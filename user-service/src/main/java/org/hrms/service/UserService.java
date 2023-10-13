package org.hrms.service;

import org.hrms.dto.request.*;
import org.hrms.dto.response.ApproveCommentOfEmployeeResponseDto;
import org.hrms.exception.UserManagerException;
import org.hrms.exception.ErrorType;
import org.hrms.manager.ICommentManager;
import org.hrms.mapper.IUserMapper;
import org.hrms.rabbitmq.model.*;
import org.hrms.rabbitmq.producer.*;
import org.hrms.repository.IUserRepository;
import org.hrms.repository.entity.User;
import org.hrms.repository.enums.EStatus;
import org.hrms.repository.enums.EUserType;
import org.hrms.utility.JwtTokenManager;
import org.hrms.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService extends ServiceManager<User,Long> {

    private final IUserRepository repository;
    private final JwtTokenManager jwtTokenManager;

    private final UpdateUserProducer updateUserProducer;
    private final DeleteUserByAuthIdProducer deleteUserByAuthIdProducer;

    private final ApproveManagerMailProducer approveManagerMailProducer;

    private final ActivateManagerStatusProducer activateManagerStatusProducer;

    private final DeleteAuthContainsCompanyNameProducer deleteAuthContainsCompanyNameProducer;

    private final ICommentManager commentManager;

    private final DeleteCompanyByRegisterDenyProducer deleteCompanyByRegisterDenyProducer;

    private final ManagerDenyMailProducer managerDenyMailProducer;

    private final ActivateCompanyStatusProducer activateCompanyStatusProducer;

    private final ActivateCompanyPackageProducer activateCompanyPackageProducer;

    private final DenyCompanyPackageProducer denyCompanyPackageProducer;

    public UserService(IUserRepository repository, JwtTokenManager jwtTokenManager, UpdateUserProducer updateUserProducer, DeleteUserByAuthIdProducer deleteUserByAuthIdProducer, ApproveManagerMailProducer approveManagerMailProducer, ActivateManagerStatusProducer activateManagerStatusProducer, DeleteAuthContainsCompanyNameProducer deleteAuthContainsCompanyNameProducer, ICommentManager commentManager, DeleteCompanyByRegisterDenyProducer deleteCompanyByRegisterDenyProducer, ManagerDenyMailProducer managerDenyMailProducer, ActivateCompanyStatusProducer activateCompanyStatusProducer, ActivateCompanyPackageProducer activateCompanyPackageProducer, DenyCompanyPackageProducer denyCompanyPackageProducer) {
        super(repository);
        this.repository = repository;
        this.jwtTokenManager = jwtTokenManager;
        this.updateUserProducer = updateUserProducer;
        this.deleteUserByAuthIdProducer = deleteUserByAuthIdProducer;
        this.approveManagerMailProducer = approveManagerMailProducer;
        this.activateManagerStatusProducer = activateManagerStatusProducer;
        this.deleteAuthContainsCompanyNameProducer = deleteAuthContainsCompanyNameProducer;
        this.commentManager = commentManager;
        this.deleteCompanyByRegisterDenyProducer = deleteCompanyByRegisterDenyProducer;
        this.managerDenyMailProducer = managerDenyMailProducer;
        this.activateCompanyStatusProducer = activateCompanyStatusProducer;
        this.activateCompanyPackageProducer = activateCompanyPackageProducer;
        this.denyCompanyPackageProducer = denyCompanyPackageProducer;
    }
    /*
        admin onayı icin user service kısmına bi metod yazıldı,
        usertype manager olan bir kullanıcı icin ordan onay almamıs bir kullanıcının id'si girilip onaylandı
        onaylayan kisininde usertype admin olmasina dikkat edildi.
     */
    public Boolean approveManagerUser(ApproveManagerRequestDto dto) {
        Optional<Long> optionalAdminAuthId = jwtTokenManager.getIdFromToken(dto.getToken());
        if (optionalAdminAuthId.isEmpty() ) {
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        }
        System.out.println("admin id:"+optionalAdminAuthId.get());
        Optional<User> optionalAdmin = repository.findByAuthid(optionalAdminAuthId.get());
        if (optionalAdmin.isEmpty()) {
            throw new UserManagerException(ErrorType.INSUFFICIENT_PERMISSION);
        }

        Optional<User> optionalManager = repository.findByAuthid(dto.getManagerAuthId());
        if (optionalManager.isEmpty()) {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }

        System.out.println(optionalManager.get().getUserType());
        if(!optionalManager.get().getUserType().equals(EUserType.MANAGER)){
            throw new UserManagerException(ErrorType.USER_TYPE_MISMATCH);
        }

        if (optionalManager.get().getStatus().equals(EStatus.ACTIVE.toString())) {
            throw new UserManagerException(ErrorType.USER_ALREADY_APPROVED);
        }

        optionalManager.get().setStatus(EStatus.ACTIVE);
        update(optionalManager.get());

        activateCompanyStatusProducer.activateCompanyStatus(IUserMapper.INSTANCE.toActivateCompanyStatusModel(optionalManager.get()));
        approveManagerMailProducer.sendApproveManagerMail(IUserMapper.INSTANCE.toApproveManagerMailModel(optionalManager.get()));
        activateManagerStatusProducer.activateManagerStatus(optionalManager.get().getAuthid());
        activateCompanyPackageProducer.activateCompanyPackage(IUserMapper.INSTANCE.toActivateCompanyPackageModel(dto));
        return true;
    }


    /**
     * Kullanici adi ve email kontrolu yapar. databasede bu degerler daha once alinmissa hata firlatir.
     * @param email
     * @param username
     */

    public boolean existByPersonalEmailAndUsernameControl(String email, String username) {
        boolean existsByEmail = repository.existsByPersonalEmail(email);
        boolean existsByUsername = repository.existsByUsername(username);

        if (existsByEmail) {
            throw new UserManagerException(ErrorType.PERSONAL_EMAIL_IS_TAKEN);

        }

        if (existsByUsername) {
            throw new UserManagerException(ErrorType.USERNAME_EXIST);

        }
        return true;

    }

    public User saveVisitorUser(RegisterVisitorModel model){
        if (existByPersonalEmailAndUsernameControl(model.getPersonalEmail(),model.getUsername())) {
            User user = IUserMapper.INSTANCE.toUser(model);
            return save(user);
        } else {
            throw new UserManagerException(ErrorType.USERNAME_OR_MAIL_EXIST);
        }
    }

    public User saveManagerUser(RegisterManagerModel model){
        if (existByPersonalEmailAndUsernameControl(model.getPersonalEmail(),model.getUsername())) {
            User user = IUserMapper.INSTANCE.toUser(model);
            user.setUserType(EUserType.MANAGER);
            user.setCompanyName(user.getCompanyName().toLowerCase());
            return save(user);
        } else {
            throw new UserManagerException(ErrorType.USERNAME_OR_MAIL_EXIST);
        }
    }
    public Boolean saveEmployee(SaveEmployeeModel model) {

        if (existByPersonalEmailAndUsernameControl(model.getPersonalEmail(), model.getUsername())) {
            boolean existsByCompanyEmail = repository.existsByCompanyEmail(model.getCompanyEmail());
            if (existsByCompanyEmail) {
                throw new UserManagerException(ErrorType.PERSONAL_EMAIL_IS_TAKEN);
            }
            User user = IUserMapper.INSTANCE.toUser(model);
            user.setCompanyName(user.getCompanyName().toLowerCase());
            save(user);
        }

        return true;


    }

    public Boolean activateStatus(Long authid) {
        Optional<User> userOptional = repository.findByAuthid(authid);
        if (userOptional.isEmpty()) {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        userOptional.get().setStatus(EStatus.ACTIVE);
        update(userOptional.get());
        return true;
    }


    public Boolean updateUser(UpdateRequestDto dto) {
        Optional<String> optionalRole = jwtTokenManager.getRoleFromToken(dto.getToken());
        Optional<Long> optionalId = jwtTokenManager.getIdFromToken(dto.getToken());
        System.out.println(optionalRole.get());
        if(optionalRole.isEmpty()){
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        }
        System.out.println(optionalId.get());
        if (optionalId.isEmpty()) {
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        }
        Optional<User> optionalUser = repository.findByAuthid(optionalId.get());
        if (optionalUser.isEmpty()) {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }

        boolean existsByPersonalEmail = repository.existsByPersonalEmail(dto.getPersonalEmail());

        if (existsByPersonalEmail && !optionalUser.get().getPersonalEmail().equals(dto.getPersonalEmail())) {
            throw new UserManagerException(ErrorType.PERSONAL_EMAIL_IS_TAKEN);
        }

        boolean existsByUsername = repository.existsByUsername(dto.getUsername());
        if (existsByUsername && !optionalUser.get().getUsername().equals(dto.getUsername())) {
            throw new UserManagerException(ErrorType.USERNAME_EXIST);
        }

        User user = setUpdateSettings(optionalUser.get(), dto);
        updateUserProducer.updateUser(IUserMapper.INSTANCE.toUpdateUserModel(user));
        return true;
    }


    /**
     * Guncellenecek kullanicinin tipine gore gerekli alanlari gunceller ve databasede update eder.
     * @param user
     * @param dto
     * @return
     */
    public User setUpdateSettings(User user, UpdateRequestDto dto) {

        switch (user.getUserType()) {
            case ADMIN,VISITOR -> {
                user.setUsername(dto.getUsername());
                user.setName(dto.getName());
                user.setSurname(dto.getSurname());
                user.setPassword(dto.getPassword());
                user.setPersonalEmail(dto.getPersonalEmail());
                update(user);
                return user;
            }
            case MANAGER, EMPLOYEE -> {
                user.setUsername(dto.getUsername());
                user.setName(dto.getName());
                user.setSurname(dto.getSurname());
                user.setPassword(dto.getPassword());
                user.setPersonalEmail(dto.getPersonalEmail());
                user.setPhoneNumber(dto.getPhoneNumber());
                user.setSalary(dto.getSalary());
                update(user);
                return user;
            }

            default -> {
                throw new UserManagerException(ErrorType.BAD_REQUEST);
            }
        }
    }

    public Boolean deleteUserByAuthId(Long authid) {
        Optional<User> optionalUser = repository.findByAuthid(authid);
        if (optionalUser.isEmpty()) {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }

        Long authid1 = optionalUser.get().getAuthid();
        deleteById(optionalUser.get().getId());
        deleteUserByAuthIdProducer.deleteUserByAuthid(authid1);
        return true;
    }

    public Boolean createAdminUser(CreateAdminUserModel model) {

            User user = IUserMapper.INSTANCE.toUser(model);
            System.out.println(user);
            save(user);

        return true;
    }

    public Boolean deleteByCompanyName(DeleteUsersContainsCompanyNameModel model) {
        List<User> allUsers = findAll();
        String companyName = model.getCompanyName();
        List<User> usersToDelete = allUsers.stream()
                .filter(user -> user.getCompanyName().equals(companyName))
                .collect(Collectors.toList());

        try {
            for (User user : usersToDelete) {
                deleteById(user.getId());
            }
        } catch (Exception e) {
            throw new UserManagerException(ErrorType.COULD_NOT_DELETE_ALL_USERS);
        }


        deleteAuthContainsCompanyNameProducer.deleteAuthContainsCompanyName(IUserMapper.INSTANCE.toDeleteAuthContainsCompanyNameModel(model));
        return true;
    }

    public Boolean updateCompanyDetails(UpdateUsersCompanyNameDetailsModel model) {
        List<User> allUsers = findAll();
        String oldCompanyName = model.getOldCompanyName();
        List<User> usersToUpdate = allUsers.stream()
                .filter(user -> user.getCompanyName().equals(oldCompanyName))
                .collect(Collectors.toList());
        try {
            for (User user : usersToUpdate) {
                companyNameSetter(user, model.getNewCompanyName());
            }
        } catch (Exception e) {
            throw new UserManagerException(ErrorType.COULD_NOT_UPDATE_ALL_USERS);
        }
        return true;
    }

    public void companyNameSetter(User user,String newCompanyName) {

        String userOldCompanyMail = user.getCompanyEmail();
        int indexOfAt = userOldCompanyMail.indexOf("@");
        String newCompanyMail = userOldCompanyMail.substring(0, indexOfAt) + "@" + newCompanyName + ".com";
        user.setCompanyName(newCompanyName);
        user.setCompanyEmail(newCompanyMail);
        update(user);
    }

    public List<User> listWorkersAsManager(ListWorkersRequestDto dto) {
        List<User> allWorkers = repository.findAllWorkers();
        if (allWorkers.isEmpty()) {
            throw new UserManagerException(ErrorType.NO_DATA_FOUND);
        }

        List<User> list = allWorkers.stream().filter(user -> user.getCompanyName().equals(dto.getCompanyName())).toList();

        if (list.isEmpty()) {
            throw new UserManagerException(ErrorType.NO_DATA_FOUND);
        }

        return list;
    }

    public List<User> listWorkersAsManagerWithoutManager(ListWorkersRequestDto dto) {
        List<User> allWorkers = repository.findAllWorkersWithoutManager();
        if (allWorkers.isEmpty()) {
            throw new UserManagerException(ErrorType.NO_DATA_FOUND);
        }

        List<User> list = allWorkers.stream().filter(user -> user.getCompanyName().equals(dto.getCompanyName())).toList();

        if (list.isEmpty()) {
            throw new UserManagerException(ErrorType.NO_DATA_FOUND);
        }
        return list;
    }

    public Boolean isCommentDetailsValid(IsCommentMatchesRequestDto dto) {
        Optional<Long> optionalAuthId = jwtTokenManager.getIdFromToken(dto.getToken());
        if (optionalAuthId.isEmpty()) {
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        }
        Optional<User> optionalUser = repository.findByAuthid(optionalAuthId.get());
        if (optionalUser.isEmpty()) {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        if (!optionalUser.get().getCompanyName().equals(dto.getCompanyName())) {
            return false;
        }

        return true;
    }

    public ApproveCommentOfEmployeeResponseDto approveCommentOfEmployee(ApproveCommentOfEmployeeRequestDto dto) {
        EUserType userType = dto.getUserType();
        if (!userType.equals(EUserType.ADMIN)) {
            throw new UserManagerException(ErrorType.INSUFFICIENT_PERMISSION);
        }
        Boolean isValid = commentManager.approveComment(dto.getCommentId()).getBody();
        if (!isValid) {
            throw new UserManagerException(ErrorType.COMMENT_NOT_FOUND);
        }
        return new ApproveCommentOfEmployeeResponseDto("Yorum basariyla onaylandi");
    }

    public List<User> listPendingManagerApproval() {
        List<User> allPendingManagerApproval = repository.findAllPendingManagerApproval();
        if (allPendingManagerApproval.isEmpty()) {
            throw new UserManagerException(ErrorType.NOT_PENDING_APPROVAL);
        }

        return allPendingManagerApproval;
    }

    public Boolean denyCommentOfEmployee(DenyCommentOfEmployeeRequestDto dto) {
        if (!dto.getUserType().equals(EUserType.ADMIN)) {
            throw new UserManagerException(ErrorType.INSUFFICIENT_PERMISSION);
        }
        Boolean isValid = commentManager.approveComment(dto.getCommentId()).getBody();
        if (!isValid) {
            throw new UserManagerException(ErrorType.COMMENT_NOT_FOUND);
        }

        Boolean isDeleted = commentManager.deleteCommentById(dto.getCommentId()).getBody();
        if (!isDeleted) {
            throw new UserManagerException(ErrorType.BAD_REQUEST);
        }

        return true;

    }

    public Boolean denyRegisterManager(DenyManagerRequestDto dto) {
        if (!dto.getUserType().equals(EUserType.ADMIN)) {
            throw new UserManagerException(ErrorType.INSUFFICIENT_PERMISSION);
        }
        Optional<User> optionalManager = repository.findByAuthid(dto.getAuthId());
        if (optionalManager.isEmpty()) {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        if (!optionalManager.get().getUserType().equals(EUserType.MANAGER)) {
            throw new UserManagerException(ErrorType.USER_TYPE_MISMATCH);
        }
        try {
            deleteUserByAuthId(dto.getAuthId());
            deleteCompanyByRegisterDenyProducer.deleteCompanyByName(new DeleteCompanyByRegisterDenyModel(optionalManager.get().getCompanyName()));
            managerDenyMailProducer.sendDenyMailToManager(new ManagerDenyMailModel(optionalManager.get().getPersonalEmail()));
            denyCompanyPackageProducer.denyCompanyPackage(IUserMapper.INSTANCE.toDenyCompanyPackageModel(dto));
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public User showPersonelInfo(String token) {
        Optional<Long> optionalAuthId = jwtTokenManager.getIdFromToken(token);
        if (optionalAuthId.isEmpty()) {
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        }
        Optional<User> optionalUser = repository.findByAuthid(optionalAuthId.get());
        if (optionalUser.isEmpty()) {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        return optionalUser.get();

    }

    public EUserType getUserType(Long authid){
        return repository.findByAuthid(authid).get().getUserType();
    }

    public String getUsername(Long authid){
        User user=repository.findByAuthid(authid).get();
        return user.getUsername();
    }

    public Boolean isRequestValid(AuthIdAndCompanyNameCheckerRequestDto dto) {
        Optional<Long> optionalAuthId = jwtTokenManager.getIdFromToken(dto.getToken());
        if (optionalAuthId.isEmpty()) {
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        }
        System.out.println(optionalAuthId.get());
        System.out.println("Id kontrolunden gecti");

        Optional<User> optionalUser = repository.findByAuthid(optionalAuthId.get());
        if (optionalUser.isEmpty()) {
            return false;
//            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        System.out.println("kullanici bulundu");

        if (!(optionalUser.get().getUserType().equals(EUserType.MANAGER) || optionalUser.get().getUserType().equals(EUserType.EMPLOYEE))) {
            return false;
//            throw new UserManagerException(ErrorType.USER_TYPE_MISMATCH);
        }
        System.out.println("manager veya employee testinden gecti");
        if (!optionalUser.get().getCompanyName().equals(dto.getCompanyName())) {
            return false;
//            throw new UserManagerException(ErrorType.COMPANY_NAME_MISMATCH);
        }
        System.out.println("sirket ismi kontrolunden gecti");

        return true;

    }

    public Boolean isAdvanceRequestValid(AdvancePaymentUserControlDto dto) {
        Optional<Long> optionalAuthId = jwtTokenManager.getIdFromToken(dto.getToken());

        Optional<User> optionalUser = repository.findByAuthid(optionalAuthId.get());
        if (optionalUser.isEmpty()) {
            return false;
        }
        if (!optionalUser.get().getCompanyName().equals(dto.getCompanyName())) {
            return false;
        }

        if (optionalUser.get().getSalary() == null) {
            return false;
        }

        if (dto.getAmount() > optionalUser.get().getSalary()) {
            return false;
        }

        return true;
    }
}
