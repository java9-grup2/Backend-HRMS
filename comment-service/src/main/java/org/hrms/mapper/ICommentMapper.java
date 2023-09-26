package org.hrms.mapper;

import org.hrms.dto.request.CommentSaveRequestDto;
import org.hrms.dto.request.IsCommentMatchesRequestDto;
import org.hrms.repository.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICommentMapper {
    ICommentMapper INSTANCE = Mappers.getMapper(ICommentMapper.class);

    IsCommentMatchesRequestDto toIsCommentMatchesRequestDto(final CommentSaveRequestDto dto);

    Comment toComment(final CommentSaveRequestDto dto);
}
