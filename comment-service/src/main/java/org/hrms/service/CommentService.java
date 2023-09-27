package org.hrms.service;

import org.hrms.dto.request.CommentSaveRequestDto;
import org.hrms.dto.response.CommentSaveResponseDto;
import org.hrms.exception.CommentManagerException;
import org.hrms.exception.ErrorType;
import org.hrms.manager.IUserManager;
import org.hrms.mapper.ICommentMapper;
import org.hrms.repository.ICommentRepository;
import org.hrms.repository.entity.Comment;
import org.hrms.repository.enums.EStatus;
import org.hrms.repository.enums.EUserType;
import org.hrms.utility.JwtTokenManager;
import org.hrms.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService extends ServiceManager<Comment,Long> {

    private final ICommentRepository repository;

    private final JwtTokenManager jwtTokenManager;

    private final IUserManager userManager;

    public CommentService(ICommentRepository repository, JwtTokenManager jwtTokenManager, IUserManager userManager) {
        super(repository);
        this.repository = repository;
        this.jwtTokenManager = jwtTokenManager;
        this.userManager = userManager;
    }

    public CommentSaveResponseDto saveEmployeeComment(CommentSaveRequestDto dto) {
        Boolean isValid = userManager.isCommentDetailsValid(ICommentMapper.INSTANCE.toIsCommentMatchesRequestDto(dto)).getBody();

        if (!isValid) {
            throw new CommentManagerException(ErrorType.TOKEN_USER_OR_COMPANY_NAME_NOT_VALID);
        }

        if (!dto.getUserType().equals(EUserType.MANAGER) && !dto.getUserType().equals(EUserType.EMPLOYEE)) {
            throw new CommentManagerException(ErrorType.INSUFFICIENT_PERMISSION);
        }

        Optional<Long> optionalAuthId = jwtTokenManager.getIdFromToken(dto.getToken());
        if (optionalAuthId.isEmpty()) {
            throw new CommentManagerException(ErrorType.INVALID_TOKEN);
        }


        Comment comment = ICommentMapper.INSTANCE.toComment(dto);
        comment.setAuthid(optionalAuthId.get());
        save(comment);

        return new CommentSaveResponseDto("Yorumunuz onaya gitmistir. Onaylandiktan sonra sirket sayfasi yorumlari kisminda gorebilirsiniz");
    }

    public List<Comment> getAllPendingComments() {
        List<Comment> allPendingComments = repository.getAllPendingComments();
        if (allPendingComments.isEmpty()) {
            throw new CommentManagerException(ErrorType.NO_PENDING_COMMENT);
        }
        return allPendingComments;
    }

    public Boolean approveComment(Long id) {
        Optional<Comment> optionalComment = findById(id);
        if (optionalComment.isEmpty()) {
            throw new CommentManagerException(ErrorType.COMMENT_NOT_FOUND);
        }
        optionalComment.get().setStatus(EStatus.ACTIVE);
        update(optionalComment.get());
        return true;
    }

    public Boolean deleteCommentById(Long id) {
        Optional<Comment> optionalComment = findById(id);
        if (optionalComment.isEmpty()) {
            throw new CommentManagerException(ErrorType.COMMENT_NOT_FOUND);
        }
        try {
            deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
