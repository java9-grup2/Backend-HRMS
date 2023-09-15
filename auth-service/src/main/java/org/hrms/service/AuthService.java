package org.hrms.service;

import org.hrms.dto.request.*;
import org.hrms.dto.response.TokenResponseDto;
import org.hrms.dto.response.MessageResponseDto;
import org.hrms.exception.AuthManagerException;
import org.hrms.exception.ErrorType;
import org.hrms.mapper.IAuthMapper;
import org.hrms.rabbitmq.model.RegisterEmployeeModel;
import org.hrms.rabbitmq.producer.ActivationMailProducer;
import org.hrms.rabbitmq.producer.RegisterEmployeeMailProducer;
import org.hrms.rabbitmq.producer.RegisterManagerProducer;
import org.hrms.rabbitmq.producer.RegisterVisitorProducer;
import org.hrms.repository.IAuthRepository;
import org.hrms.repository.entity.Auth;
import org.hrms.repository.enums.EStatus;
import org.hrms.repository.enums.EUserType;
import org.hrms.utility.CodeGenerator;
import org.hrms.utility.JwtTokenManager;
import org.hrms.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService extends ServiceManager<Auth,Long> {

    private final IAuthRepository repository;
    private final RegisterVisitorProducer registerVisitorProducer;
    private final RegisterManagerProducer registerManagerProducer;
    private final JwtTokenManager jwtTokenManager;
    private final ActivationMailProducer activationMailProducer;
    private final RegisterEmployeeMailProducer registerEmployeeMailProducer;

    public AuthService(IAuthRepository repository, RegisterVisitorProducer registerVisitorProducer, RegisterManagerProducer registerManagerProducer, JwtTokenManager jwtTokenManager, ActivationMailProducer activationMailProducer, RegisterEmployeeMailProducer registerEmployeeMailProducer) {
        super(repository);
        this.repository = repository;
        this.registerVisitorProducer = registerVisitorProducer;
        this.registerManagerProducer = registerManagerProducer;
        this.jwtTokenManager = jwtTokenManager;
        this.activationMailProducer = activationMailProducer;
        this.registerEmployeeMailProducer = registerEmployeeMailProducer;
    }

    public TokenResponseDto registerVisitor(RegisterVisitorRequestDto dto) {

        existByPersonalEmailAndUsernameControl(dto.getPersonalEmail(), dto.getUsername());

        Auth auth = IAuthMapper.INSTANCE.toAuth(dto);
        save(auth);

        System.out.println("userregister kuyruga yollamadan once");
        registerVisitorProducer.registerVisitor(IAuthMapper.INSTANCE.toRegisterVisitorModel(auth));
        System.out.println("userregister kuyruga yollamadan sonra");
        Optional<String> optionalToken = jwtTokenManager.createToken(auth.getId());
        if (optionalToken.isEmpty()) {
            throw new AuthManagerException(ErrorType.TOKEN_NOT_CREATED);
        }
        auth.setActivationCode(optionalToken.get());
        update(auth);
        System.out.println("activasyon maili yollamdan once");
        activationMailProducer.sendActivationMail(IAuthMapper.INSTANCE.toActivationMailModel(auth));
        return new TokenResponseDto(optionalToken.get());
    }

    public TokenResponseDto registerManager(RegisterManagerRequestDto dto) {
        existByPersonalEmailAndUsernameControl(dto.getPersonalEmail(), dto.getUsername());

        Auth auth = IAuthMapper.INSTANCE.toAuth(dto);
        auth.setUserType(EUserType.MANAGER);
        save(auth);

        registerManagerProducer.registerManager(IAuthMapper.INSTANCE.toRegisterManagerModel(auth));

        Optional<String> optionalToken = jwtTokenManager.createToken(auth.getId(),auth.getUserType(),auth.getCompanyName());

        if (optionalToken.isEmpty()) {
            throw new AuthManagerException(ErrorType.TOKEN_NOT_CREATED);
        }

        return new TokenResponseDto(optionalToken.get());
    }


    /**
     * Kullanici adi ve email kontrolu yapar. databasede bu degerler daha once alinmissa hata firlatir.
     * @param email
     * @param username
     */
    public void existByPersonalEmailAndUsernameControl(String email, String username) {
        boolean existsByEmail = repository.existsByPersonalEmail(email);
        boolean existsByUsername = repository.existsByUsername(username);

        if (existsByEmail) {
            throw new AuthManagerException(ErrorType.EMAIL_TAKEN);
        }

        if (existsByUsername) {
            throw new AuthManagerException(ErrorType.USERNAME_EXIST);
        }

    }


    public TokenResponseDto login(LoginRequestDto dto) {
        Optional<Auth> optionalAuth = repository.findByPersonalEmailAndPassword(dto.getEmail(), dto.getPassword());

        if (optionalAuth.isEmpty()) {
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }

        if (!optionalAuth.get().getStatus().equals(EStatus.ACTIVE)) {
            throw new AuthManagerException(ErrorType.ACCOUNT_NOT_ACTIVE);
        }

        Optional<String> token = jwtTokenManager.createToken(optionalAuth.get().getId());
        if (token.isEmpty()) {
            throw new AuthManagerException(ErrorType.TOKEN_NOT_CREATED);
        }

        return new TokenResponseDto(token.get());
    }



    public MessageResponseDto activateStatus(String token) {
        Optional<Long> optionalId = jwtTokenManager.getIdFromToken(token);

        if (optionalId.isEmpty()) {
            throw new AuthManagerException(ErrorType.INVALID_TOKEN);
        }


        Optional<Auth> optionalAuth = findById(optionalId.get());

        if (optionalAuth.isEmpty()) {
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }

        optionalAuth.get().setStatus(EStatus.ACTIVE);
        update(optionalAuth.get());

        return new MessageResponseDto("Hesabiniz aktif edilmistir.");
    }


   // @PostConstruct
    public void defaultAdmin() {
        System.out.println("Bu metod her turlu calisir pasa");
        Auth auth= Auth.builder()
                .personalEmail("abc@gmail.com")
                .companyName("abab")
                .password("abab")
                .status(EStatus.ACTIVE)
                .userType(EUserType.ADMIN)
                .build();
                save(auth);
    }

    public Boolean registerEmployee(RegisterEmployeeRequestDto dto) {
        Optional<String> optionalRole = jwtTokenManager.getRoleFromToken(dto.getToken());
        Optional<String> companyNameFromToken = jwtTokenManager.getCompanyNameFromToken(dto.getToken());

        if (optionalRole.isEmpty()) {
            throw new AuthManagerException(ErrorType.INVALID_TOKEN);
        }

        if (companyNameFromToken.isEmpty()) {
            throw new AuthManagerException(ErrorType.INVALID_TOKEN);
        }

        if (!optionalRole.get().equals(EUserType.MANAGER.toString())) {
            throw new AuthManagerException(ErrorType.INSUFFICIENT_PERMISSION);
        }

        boolean existsByPersonalEmail = repository.existsByPersonalEmail(dto.getPersonalEmail());
        if (existsByPersonalEmail) {
            throw new AuthManagerException(ErrorType.EMAIL_TAKEN);
        }

        Auth auth = Auth.builder()
                .personalEmail(dto.getPersonalEmail())
                .userType(EUserType.EMPLOYEE)
                .status(EStatus.ACTIVE)
                .password(CodeGenerator.generateCode())
                .companyEmail(emailSetter(dto.getName(),dto.getSurname(),companyNameFromToken.get()))
                .companyName(companyNameFromToken.get())
                .build();

        save(auth);

        RegisterEmployeeModel registerEmployeeModel = IAuthMapper.INSTANCE.toRegisterEmployeeModel(auth);
        registerEmployeeModel.setName(dto.getName());
        registerEmployeeModel.setSurname(dto.getSurname());
        registerEmployeeMailProducer.sendEmployeeDetails(registerEmployeeModel);
        return true;
    }

    /**
     * Kullanicidan toplanan bilgiler ile belirli bir kontrolden gecirdikten sonra kullaniciya ozel bir email olusturur.
     * @param name
     * @param surname
     * @param companyName
     * @return
     */
    public String emailSetter(String name,String surname,String companyName) {
        String newEmail = name+"."+surname+"@"+companyName.toLowerCase()+".com";

        boolean existsByCompanyEmail = repository.existsByCompanyEmail(newEmail);
        if (existsByCompanyEmail) {
            newEmail= surname+"."+name+"@"+companyName.toLowerCase()+".com";
        }

        boolean existsByCompanyEmail2 = repository.existsByCompanyEmail(newEmail);

        if (existsByCompanyEmail2) {
            newEmail= name+"."+surname+CodeGenerator.generateCode()+"@"+companyName.toLowerCase()+".com";
        }
        return newEmail;
    }
}
