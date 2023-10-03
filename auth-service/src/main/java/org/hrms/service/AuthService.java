package org.hrms.service;

import org.hrms.dto.request.*;
import org.hrms.dto.response.ForgotPasswordResponseDto;
import org.hrms.dto.response.LoginResponseDto;
import org.hrms.dto.response.TokenResponseDto;
import org.hrms.dto.response.MessageResponseDto;
import org.hrms.exception.AuthManagerException;
import org.hrms.exception.ErrorType;
import org.hrms.manager.ICompanyManager;
import org.hrms.mapper.IAuthMapper;
import org.hrms.rabbitmq.model.*;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    private final CreateAdminUserProducer createAdminUserProducer;

    private final ForgotPasswordMailProducer forgotPasswordMailProducer;

    public AuthService(IAuthRepository repository, RegisterVisitorProducer registerVisitorProducer, RegisterManagerProducer registerManagerProducer, JwtTokenManager jwtTokenManager, ActivationMailProducer activationMailProducer, RegisterEmployeeMailProducer registerEmployeeMailProducer, SaveEmployeeProducer saveEmployeeProducer, ActivateStatusProducer activateStatusProducer, CreateCompanyProducer createCompanyProducer, ICompanyManager companyManager, CreateAdminUserProducer createAdminUserProducer, ForgotPasswordMailProducer forgotPasswordMailProducer) {
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
        this.createAdminUserProducer = createAdminUserProducer;
        this.forgotPasswordMailProducer = forgotPasswordMailProducer;
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


        Boolean isCompanyValid = companyManager.isCompanyRequestValid(IAuthMapper.INSTANCE.toIsCompanyRequestValidDto(dto)).getBody();
        if (!isCompanyValid) {
            throw new AuthManagerException(ErrorType.COMPANY_NAME_OR_TAX_NO_IS_NOT_VALID);
        }

        Auth auth = IAuthMapper.INSTANCE.toAuth(dto);
        String companyEmail = "manager" + dto.getName() + "@" + dto.getCompanyName() + ".com";
        auth.setCompanyEmail(companyEmail.toLowerCase());
        auth.setUserType(EUserType.MANAGER);
        auth.setCompanyName(auth.getCompanyName().toLowerCase());
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


    public LoginResponseDto login(LoginRequestDto dto) {
        int indexOfAt = dto.getEmail().indexOf("@");
        int lastIndexOfDot = dto.getEmail().lastIndexOf(".");
        String companyName = dto.getEmail().substring(indexOfAt + 1, lastIndexOfDot);

        Boolean isCompanyExist = companyManager.isCompanyExists(companyName).getBody();

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

        Optional<String> token;

        if (optionalAuth.get().getCompanyName() == null) {
            token = jwtTokenManager.createToken(optionalAuth.get().getId(),optionalAuth.get().getUserType());
        }else{
            token = jwtTokenManager.createToken(optionalAuth.get().getId(),optionalAuth.get().getUserType(),optionalAuth.get().getCompanyName());
        }

        if (token.isEmpty()) {
            throw new AuthManagerException(ErrorType.TOKEN_NOT_CREATED);
        }
        return new LoginResponseDto(token.get(),optionalAuth.get().getUserType().toString(),optionalAuth.get().getCompanyName());
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


    @PostConstruct
    public void defaultAdmin() {
        boolean existsByUserType = repository.existsByUserType(EUserType.ADMIN);
        if (existsByUserType) {
            return;
        }
        Auth auth= Auth.builder()
                .personalEmail("admin@gmail.com")
                .companyName("serverOwner")
                .password("admin")
                .status(EStatus.ACTIVE)
                .userType(EUserType.ADMIN)
                .build();
        save(auth);

        createAdminUserProducer.createAdminUser(IAuthMapper.INSTANCE.toCreateAdminUserModel(auth));
    }

    public Boolean  registerEmployee(RegisterEmployeeRequestDto dto) {
        Optional<String> optionalRole = jwtTokenManager.getRoleFromToken(dto.getToken());
        Optional<String> companyNameFromToken = jwtTokenManager.getCompanyNameFromToken(dto.getToken());

        System.out.println(optionalRole.get());
        System.out.println(companyNameFromToken.get());

        if (optionalRole.isEmpty()) {
            System.out.println("Burasi rol");
            throw new AuthManagerException(ErrorType.INVALID_TOKEN);
        }

        if (companyNameFromToken.isEmpty()) {
            System.out.println("Burasi sirketIsim");
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
                .companyEmail(emailSetter(dto.getName(), dto.getSurname(), companyNameFromToken.get()))
                .companyName(companyNameFromToken.get().toLowerCase())
                .salary(dto.getSalary())
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
            case ADMIN, VISITOR -> {
                auth.setUsername(model.getUsername());
                auth.setName(model.getName());
                auth.setSurname(model.getSurname());
                auth.setPassword(model.getPassword());
                auth.setPersonalEmail(model.getPersonalEmail());
                update(auth);
            }
            case MANAGER, EMPLOYEE -> {
                auth.setUsername(model.getUsername());
                auth.setName(model.getName());
                auth.setSurname(model.getSurname());
                auth.setPassword(model.getPassword());
                auth.setPersonalEmail(model.getPersonalEmail());
                auth.setSalary(model.getSalary());
                auth.setPhoneNumber(model.getPhoneNumber());
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

    public Boolean activateManagerStatus(Long authid) {
        Optional<Auth> optionalManager = findById(authid);
        if (optionalManager.isEmpty()) {
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }

        if (!optionalManager.get().getUserType().equals(EUserType.MANAGER)) {
            throw new AuthManagerException(ErrorType.USER_TYPE_MISMATCH);
        }

        optionalManager.get().setStatus(EStatus.ACTIVE);
        update(optionalManager.get());
        return true;
    }

    public Boolean deleteByCompanyName(DeleteAuthContainsCompanyNameModel model) {
        List<Auth> allUsers = findAll();
        String companyName = model.getCompanyName();
        List<Auth> usersToDelete = allUsers.stream()
                .filter(auth -> auth.getCompanyName().equals(companyName))
                .collect(Collectors.toList());

        try {
            for (Auth auth : usersToDelete) {
                deleteById(auth.getId());
            }
        } catch (Exception e) {
            throw new AuthManagerException(ErrorType.COULD_NOT_DELETE_ALL_USERS);
        }

        return true;
    }

    public Boolean updateCompanyDetails(UpdateAuthCompanyNameDetailsModel model) {

        List<Auth> allUsers = findAll();
        String oldCompanyName = model.getOldCompanyName();
        List<Auth> usersToUpdate = allUsers.stream()
                .filter(user -> user.getCompanyName().equals(oldCompanyName))
                .collect(Collectors.toList());

        try {
            for (Auth auth : usersToUpdate) {
                companyNameSetter(auth, model.getNewCompanyName());
            }
        } catch (Exception e) {
            throw new AuthManagerException(ErrorType.COULD_NOT_UPDATE_ALL_USERS);
        }
        return true;
    }

    public void companyNameSetter(Auth auth,String newCompanyName) {

        String userOldCompanyMail = auth.getCompanyEmail();
        int indexOfAt = userOldCompanyMail.indexOf("@");
        String newCompanyMail = userOldCompanyMail.substring(0, indexOfAt) + "@" + newCompanyName + ".com";
        auth.setCompanyName(newCompanyName);
        auth.setCompanyEmail(newCompanyMail);
        update(auth);
    }

    public ForgotPasswordResponseDto forgotPassword(ForgotPasswordRequestDto dto) {
        String successMessage = "Sifreniz basariyla sahsi mailinize gonderilmistir";
        boolean existsByPersonalEmail = repository.existsByPersonalEmail(dto.getEmail());
        if (existsByPersonalEmail) {
            Optional<Auth> optionalAuth = repository.findByPersonalEmail(dto.getEmail());
            if (optionalAuth.isEmpty()) {
                throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
            }
            forgotPasswordMailProducer.forgotPasswordMailSender(ForgotPasswordMailModel.builder()
                    .email(optionalAuth.get().getPersonalEmail())
                    .password(optionalAuth.get().getPassword())
                    .build());
            return new ForgotPasswordResponseDto(successMessage);
        }

        boolean existsByCompanyEmail = repository.existsByCompanyEmail(dto.getEmail());
        if (existsByCompanyEmail) {
            Optional<Auth> optionalAuth = repository.findByCompanyEmail(dto.getEmail());
            if (optionalAuth.isEmpty()) {
                throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
            }
            forgotPasswordMailProducer.forgotPasswordMailSender(ForgotPasswordMailModel.builder()
                    .email(optionalAuth.get().getPersonalEmail())
                    .password(optionalAuth.get().getPassword())
                    .build());
            return new ForgotPasswordResponseDto(successMessage);
        }

        throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
    }
}
