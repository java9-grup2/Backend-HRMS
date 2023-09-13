package org.hrms.service;

import org.hrms.dto.request.ActivationRequestDto;
import org.hrms.dto.request.LoginRequestDto;
import org.hrms.dto.request.RegisterManagerRequestDto;
import org.hrms.dto.request.RegisterVisitorRequestDto;
import org.hrms.dto.response.TokenResponseDto;
import org.hrms.dto.response.MessageResponseDto;
import org.hrms.exception.AuthManagerException;
import org.hrms.exception.ErrorType;
import org.hrms.mapper.IAuthMapper;
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

    public AuthService(IAuthRepository repository, RegisterVisitorProducer registerVisitorProducer, RegisterManagerProducer registerManagerProducer, JwtTokenManager jwtTokenManager) {
        super(repository);
        this.repository = repository;
        this.registerVisitorProducer = registerVisitorProducer;
        this.registerManagerProducer = registerManagerProducer;
        this.jwtTokenManager = jwtTokenManager;
    }

    public TokenResponseDto registerVisitor(RegisterVisitorRequestDto dto) {
        boolean existsByEmail = repository.existsByEmail(dto.getEmail());
        boolean existsByUsername = repository.existsByUsername(dto.getUsername());

        if (existsByEmail) {
            throw new AuthManagerException(ErrorType.EMAIL_TAKEN);
        }

        if (existsByUsername) {
            throw new AuthManagerException(ErrorType.USERNAME_EXIST);
        }

        Auth auth = IAuthMapper.INSTANCE.toAuth(dto);

        registerVisitorProducer.registerVisitor(IAuthMapper.INSTANCE.toRegisterVisitorModel(auth));

        auth.setActivationCode(CodeGenerator.generateCode());
        save(auth);

        Optional<String> optionalToken = jwtTokenManager.createToken(auth.getId());
        if (optionalToken.isEmpty()) {
            throw new AuthManagerException(ErrorType.TOKEN_NOT_CREATED);
        }

        return new TokenResponseDto(optionalToken.get());
    }

    public TokenResponseDto registerManager(RegisterManagerRequestDto dto) {
        boolean existsByEmail = repository.existsByEmail(dto.getEmail());
        boolean existsByUsername = repository.existsByUsername(dto.getUsername());

        if (existsByEmail) {
            throw new AuthManagerException(ErrorType.EMAIL_TAKEN);
        }

        if (existsByUsername) {
            throw new AuthManagerException(ErrorType.USERNAME_EXIST);
        }

        Auth auth = IAuthMapper.INSTANCE.toAuth(dto);
        auth.setUserType(EUserType.MANAGER);
        save(auth);

        registerManagerProducer.registerManager(IAuthMapper.INSTANCE.toRegisterManagerModel(auth));

        Optional<String> optionalToken = jwtTokenManager.createToken(auth.getId());


        if (optionalToken.isEmpty()) {
            throw new AuthManagerException(ErrorType.TOKEN_NOT_CREATED);
        }

        return new TokenResponseDto(optionalToken.get());
    }

    public TokenResponseDto login(LoginRequestDto dto) {
        Optional<Auth> optionalAuth = repository.findByEmailAndPassword(dto.getEmail(), dto.getPassword());

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



    public MessageResponseDto activateStatus(ActivationRequestDto dto) {
        Optional<Long> optionalId = jwtTokenManager.getIdFromToken(dto.getToken());

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
}
