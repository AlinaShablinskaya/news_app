package ru.clevertec.newsapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.clevertec.newsapp.dto.comment.CommentRequestDto;
import ru.clevertec.newsapp.dto.comment.CommentResponseDto;
import ru.clevertec.newsapp.entities.Comment;

/**
 * Mapper class for comment
 */
@Mapper
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    /**
     * Converts entity to Dto
     * @param comment entity to convert
     * @return CommentResponseDto
     */
    CommentResponseDto convertToCommentResponseDto(Comment comment);

    /**
     * Converts Dto to entity
     * @param commentRequestDto Dto to convert
     * @return comment entity
     */
    Comment convertToComment(CommentRequestDto commentRequestDto);
}
