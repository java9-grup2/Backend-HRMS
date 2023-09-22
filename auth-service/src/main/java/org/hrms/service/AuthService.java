package org.hrms.service;

import org.hrms.dto.request.*;
import org.hrms.dto.response.TokenResponseDto;
import org.hrms.dto.response.MessageResponseDto;
import org.hrms.exception.AuthManagerException;
import org.hrms.exception.ErrorType;
import org.hrms.manager.ICompanyManager;
import org.hrms.mapper.IAuthMapper;
import org.hrms.rabbitmq.model.RegisterEmployeeMailModel;
import org.hrms.rabbitmq.model.UpdateUserModel;
import org.hrms.rabbitmq.producer.*;
import org.hrms.repository.IAuthRepository;
import org.hrms.repository.entity.Auth;
import org.hrms.repository.enums.EStatus;
import org.hrms.repository.enums.EUserType;
import org.hrms.utility.CodeGenerator;
import org.hrms.utility.JwtTokenManager;
import org.hrms.utility.ServiceManager;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class AuthService extends ServiceManager<Auth,Long> {

    private final IAuthRepository repository;
    private final RegisterVisitorProducer registerVisitorProducer;
    private final RegisterManagerProducer registerManagerProducer;
    private final JwtTokenManager jwtTokenManager;
    private final ActivationMailProducer activationMailProducer;
    private final RegisterEmployeeMailProducer registerEmployeeMailProducer;
    private final SaveEmployeeProducer saveEmployeeProducer;

    private final ActivateStatusProducer activateStatusProducer;

    private final CreateCompanyProducer createCompanyProducer;
    private final ICompanyManager companyManager;

    public AuthService(IAuthRepository repository, RegisterVisitorProducer registerVisitorProducer, RegisterManagerProducer registerManagerProducer, JwtTokenManager jwtTokenManager, ActivationMailProducer activationMailProducer, RegisterEmployeeMailProducer registerEmployeeMailProducer, SaveEmployeeProducer saveEmployeeProducer, ActivateStatusProducer activateStatusProducer, CreateCompanyProducer createCompanyProducer, ICompanyManager companyManager) {
        super(repository);
        this.repository = repository;
        this.registerVisitorProducer = registerVisitorProducer;
        this.registerManagerProducer = registerManagerProducer;
        this.jwtTokenManager = jwtTokenManager;
        this.activationMailProducer = activationMailProducer;
        this.registerEmployeeMailProducer = registerEmployeeMailProducer;
        this.saveEmployeeProducer = saveEmployeeProducer;
        this.activateStatusProducer = activateStatusProducer;
        this.createCompanyProducer = createCompanyProducer;
        this.companyManager = companyManager;
    }

    public TokenResponseDto registerVisitor(RegisterVisitorRequestDto dto) {

        existByPersonalEmailAndUsernameControl(dto.getPersonalEmail(), dto.getUsername());

        Auth auth = IAuthMapper.INSTANCE.toAuth(dto);
        save(auth);

        registerVisitorProducer.registerVisitor(IAuthMapper.INSTANCE.toRegisterVisitorModel(auth));
        Optional<String> optionalToken = jwtTokenManager.createToken(auth.getId());
        if (optionalToken.isEmpty()) {
            throw new AuthManagerException(ErrorType.TOKEN_NOT_CREATED);
        }
        auth.setActivationCode(optionalToken.get());
        update(auth);
        activationMailProducer.sendActivationMail(IAuthMapper.INSTANCE.toActivationMailModel(auth));
        return new TokenResponseDto(optionalToken.get());
    }


    public TokenResponseDto registerManager(RegisterManagerRequestDto dto) {
        existByPersonalEmailAndUsernameControl(dto.getPersonalEmail(), dto.getUsername());

        Auth auth = IAuthMapper.INSTANCE.toAuth(dto);
        auth.setUserType(EUserType.MANAGER);
        save(auth);

        registerManagerProducer.registerManager(IAuthMapper.INSTANCE.toRegisterManagerModel(auth));

        createCompanyProducer.createCompany(IAuthMapper.INSTANCE.toCreateCompanyModel(auth));

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
            throw new AuthManagerException(ErrorType.PERSONAL_EMAIL_IS_TAKEN);
        }

        if (existsByUsername) {
            throw new AuthManagerException(ErrorType.USERNAME_EXIST);
        }

    }


    public TokenResponseDto login(LoginRequestDto dto) {
        int indexOfAt = dto.getEmail().indexOf("@");
        int lastIndexOfDot = dto.getEmail().lastIndexOf(".");
        String companyName = dto.getEmail().substring(indexOfAt + 1, lastIndexOfDot);

        System.out.println(companyName);
        Boolean isCompanyExist = companyManager.isCompanyExists(companyName).getBody();

        System.out.println("Is companyName= "+isCompanyExist);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Optional<Auth> optionalAuth;

        if (isCompanyExist) {
            optionalAuth = repository.findByCompanyEmailAndPassword(dto.getEmail(), dto.getPassword());
        } else {
            optionalAuth = repository.findByPersonalEmailAndPassword(dto.getEmail(), dto.getPassword());
        }

        if (optionalAuth.isEmpty()) {
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }

        if (!optionalAuth.get().getStatus().equals(EStatus.ACTIVE)) {
            throw new AuthManagerException(ErrorType.ACCOUNT_NOT_ACTIVE);
        }

        Optional<String> token = jwtTokenManager.createToken(optionalAuth.get().getId(),optionalAuth.get().getUserType());
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

        activateStatusProducer.activateStatus(IAuthMapper.INSTANCE.toActivateStatusModel(optionalAuth.get()));

        return new MessageResponseDto("Hesabiniz aktif edilmistir.");
    }


//    @PostConstruct
    public void defaultAdmin() {
        System.out.println("Bu metod her turlu calisir ");
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
            throw new AuthManagerException(ErrorType.PERSONAL_EMAIL_IS_TAKEN);
        }

        Auth auth = Auth.builder()
                .personalEmail(dto.getPersonalEmail())
                .name(dto.getName())
                .surname(dto.getSurname())
                .userType(EUserType.EMPLOYEE)
                .status(EStatus.ACTIVE)
                .password(CodeGenerator.generateCode())
                .companyEmail(emailSetter(dto.getName(),dto.getSurname(),companyNameFromToken.get()))
                .companyName(companyNameFromToken.get())
                .build();

        auth.setUsername(usernameSetter(auth.getCompanyEmail()));

        save(auth);

        saveEmployeeProducer.saveEmployee(IAuthMapper.INSTANCE.toSaveEmployeeModel(auth));

        RegisterEmployeeMailModel registerEmployeeModel = IAuthMapper.INSTANCE.toRegisterEmployeeModel(auth);
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

    /**
     * Calisan icin sirket emailine gore username atar. Eger ayni email mevcutsa farkli bir email atama islemi yapar.
     * @param companyEmail
     * @return
     */
    public String usernameSetter(String companyEmail) {
        int i = companyEmail.indexOf("@");
        String username = companyEmail.substring(0, i);

        while (repository.existsByUsername(username)) {
            username = companyEmail.substring(0, i) + CodeGenerator.generateCode();
        }
        return username;
    }

    public Boolean updateAuth(UpdateUserModel model) {
        boolean existsByUsername = repository.existsByUsername(model.getUsername());
        boolean existsByPersonalEmail = repository.existsByPersonalEmail(model.getPersonalEmail());

        Optional<Auth> optionalAuth = repository.findById(model.getId());
        if (optionalAuth.isEmpty()) {
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        if (existsByUsername && !optionalAuth.get().getUsername().equals(model.getUsername())) {
            throw new AuthManagerException(ErrorType.USERNAME_EXIST);
        }

        if (existsByPersonalEmail && !optionalAuth.get().getPersonalEmail().equals(model.getPersonalEmail())) {
            throw new AuthManagerException(ErrorType.PERSONAL_EMAIL_IS_TAKEN);
        }

        setAuthUpdateSettings(optionalAuth.get(),model);
        return true;

    }

    /**
     * Rabbitmq ile user-serviceden gelen modelin tipine gore guncelleme yapar.
     * @param auth
     * @param model
     */
    public void setAuthUpdateSettings(Auth auth, UpdateUserModel model) {

        switch (auth.getUserType()) {
            case MANAGER,ADMIN -> {
                auth.setUsername(model.getUsername());
                auth.setName(model.getName());
                auth.setSurname(model.getSurname());
                auth.setPassword(model.getPassword());
                auth.setPersonalEmail(model.getPersonalEmail());
                auth.setTaxNo(model.getTaxNo());
                auth.setCompanyName(model.getCompanyName());
                update(auth);
            }
            case VISITOR,EMPLOYEE -> {
                auth.setUsername(model.getUsername());
                auth.setName(model.getName());
                auth.setSurname(model.getSurname());
                auth.setPassword(model.getPassword());
                auth.setPersonalEmail(model.getPersonalEmail());
                update(auth);
            }
            default -> {
                throw new AuthManagerException(ErrorType.BAD_REQUEST);
            }
        }
    }

    public Boolean deleteAuthById(Long id) {
        Optional<Auth> optionalAuth = findById(id);
        if (optionalAuth.isEmpty()) {
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        deleteById(id);
        return true;
    }
}
