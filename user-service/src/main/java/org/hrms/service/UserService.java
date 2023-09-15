package org.hrms.service;

import org.hrms.dto.request.RegisterVisitorRequestDto;
import org.hrms.exception.UserManagerException;
import org.hrms.exception.ErrorType;
import org.hrms.mapper.IUserMapper;
import org.hrms.repository.IUserRepository;
import org.hrms.repository.entity.User;
import org.hrms.repository.enums.EUserType;
import org.hrms.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceManager<User,Long> {

    private final IUserRepository repository;

    public UserService(IUserRepository repository) {
        super(repository);
        this.repository = repository;
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
            throw new UserManagerException(ErrorType.EMAIL_TAKEN);

        }

        if (existsByUsername) {
            throw new UserManagerException(ErrorType.USERNAME_EXIST);

        }
        return true;

    }

    public User saveUser(RegisterVisitorRequestDto dto){
        if (existByPersonalEmailAndUsernameControl(dto.getPersonalEmail(),dto.getUsername())) {
            User user = IUserMapper.INSTANCE.toUser(dto);
            return save(user);
        } else {
            throw new UserManagerException(ErrorType.USERNAME_OR_MAIL_EXIST);
        }
    }

}
