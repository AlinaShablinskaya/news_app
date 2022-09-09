package ru.clevertec.newsapp.services;

import ru.clevertec.newsapp.dto.comment.CommentRequestDto;
import ru.clevertec.newsapp.dto.comment.CommentResponseDto;

public interface CommentService {
    CommentResponseDto addComment(CommentRequestDto commentRequestDto);

    CommentResponseDto findCommentById(Long id);

    CommentResponseDto updateComment(CommentRequestDto comment);

    void deleteCommentById(Long id);
}
