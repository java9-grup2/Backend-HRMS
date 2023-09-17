package org.hrms.service;

import org.hrms.dto.request.UpdateRequestDto;
import org.hrms.exception.UserManagerException;
import org.hrms.exception.ErrorType;
import org.hrms.mapper.IUserMapper;
import org.hrms.rabbitmq.model.RegisterManagerModel;
import org.hrms.rabbitmq.model.RegisterVisitorModel;
import org.hrms.rabbitmq.model.SaveEmployeeModel;
import org.hrms.rabbitmq.producer.DeleteUserByAuthIdProducer;
import org.hrms.rabbitmq.producer.UpdateUserProducer;
import org.hrms.repository.IUserRepository;
import org.hrms.repository.entity.User;
import org.hrms.repository.enums.EStatus;
import org.hrms.utility.JwtTokenManager;
import org.hrms.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService extends ServiceManager<User,Long> {

    private final IUserRepository repository;
    private final JwtTokenManager jwtTokenManager;

    private final UpdateUserProducer updateUserProducer;
    private final DeleteUserByAuthIdProducer deleteUserByAuthIdProducer;

    public UserService(IUserRepository repository, JwtTokenManager jwtTokenManager, UpdateUserProducer updateUserProducer, DeleteUserByAuthIdProducer deleteUserByAuthIdProducer) {
        super(repository);
        this.repository = repository;
        this.jwtTokenManager = jwtTokenManager;
        this.updateUserProducer = updateUserProducer;
        this.deleteUserByAuthIdProducer = deleteUserByAuthIdProducer;
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
        if(optionalRole.isEmpty()){
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        }

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
            case MANAGER,ADMIN -> {
                user.setUsername(dto.getUsername());
                user.setPassword(dto.getPassword());
                user.setPersonalEmail(dto.getPersonalEmail());
                user.setTaxNo(dto.getTaxNo());
                user.setCompanyName(dto.getCompanyName());
                update(user);
                return user;
            }
            case VISITOR,EMPLOYEE -> {
                user.setUsername(dto.getUsername());
                user.setPassword(dto.getPassword());
                user.setPersonalEmail(dto.getPersonalEmail());
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
}
