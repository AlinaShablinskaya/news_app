package ru.clevertec.newsapp.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.clevertec.newsapp.dto.comment.CommentRequestDto;
import ru.clevertec.newsapp.dto.comment.CommentResponseDto;
import ru.clevertec.newsapp.entities.Comment;
import ru.clevertec.newsapp.mappers.CommentMapper;
import ru.clevertec.newsapp.repositories.CommentRepository;
import ru.clevertec.newsapp.repositories.NewsRepository;
import ru.clevertec.newsapp.services.CommentService;

import javax.persistence.EntityNotFoundException;

/**
 * Service class for managing comment.
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private static final String COMMENT_NOT_FOUND_EXCEPTION = "Specified comment is not found";
    private static final String NEWS_NOT_FOUND_EXCEPTION = "Specified news is not found";
    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;

    /**
     * Returns created comment in the table mapped by calling commentRepository.save(comment)
     * @param commentRequestDto the entity to save
     * @return non-null value with defined id
     */
    @Override
    @Cacheable("comment")
    public CommentResponseDto addComment(CommentRequestDto commentRequestDto) {
        Comment comment = CommentMapper.INSTANCE.convertToComment(commentRequestDto);
        if (newsRepository.findById(comment.getId_news()).isEmpty()) {
            throw new EntityNotFoundException(NEWS_NOT_FOUND_EXCEPTION);
        }
        comment = commentRepository.save(comment);
        return CommentMapper.INSTANCE.convertToCommentResponseDto(comment);
    }

    /**
     * Returns the comment by the defined id by calling commentRepository.findById(id) if specified comment exists,
     * otherwise throws EntityNotFoundException
     * @param id the id of the comment to retrieve
     * @throws EntityNotFoundException if comment with specified id is not present in database
     * @return the non-null comment with defined id
     */
    @Override
    @Cacheable("comment")
    public CommentResponseDto findCommentById(Long id) {
        Comment comment =  commentRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(COMMENT_NOT_FOUND_EXCEPTION));
        return CommentMapper.INSTANCE.convertToCommentResponseDto(comment);
    }

    /**
     * Updates comment in the table mapped by calling commentRepository.save(comment) if specified comment exists,
     * otherwise throws EntityNotFoundException
     * @param commentRequestDto value to update comment in database
     * @throws EntityNotFoundException if comment with id defined in param comment is not present in database
     * @return updates comment
     */
    @Override
    @CachePut("comment")
    public CommentResponseDto updateComment(CommentRequestDto commentRequestDto) {
        Comment comment = CommentMapper.INSTANCE.convertToComment(commentRequestDto);
        if (commentRepository.findById(comment.getId()).isEmpty()) {
            throw new EntityNotFoundException(COMMENT_NOT_FOUND_EXCEPTION);
        }
        comment = commentRepository.save(comment);
        return CommentMapper.INSTANCE.convertToCommentResponseDto(comment);
    }

    /**
     * Deletes comment with param id from the table mapped by calling commentRepository.deleteById(id)
     * if specified comment exists, otherwise throws EntityNotFoundException
     * @throws EntityNotFoundException if comment with specified id is not present in database
     * @param id the id of the product to delete
     */
    @Override
    @CacheEvict("comment")
    public void deleteCommentById(Long id) {
        if (commentRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException(COMMENT_NOT_FOUND_EXCEPTION);
        }
        commentRepository.deleteById(id);
    }
}
