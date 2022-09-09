package ru.clevertec.newsapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.newsapp.dto.comment.CommentRequestDto;
import ru.clevertec.newsapp.dto.comment.CommentResponseDto;
import ru.clevertec.newsapp.services.CommentService;

import javax.validation.Valid;

/**
 * REST controller for managing comment.
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    /**
     * POST : create a new comment
     * @param commentRequestDto the commentRequestDto to create
     * @return the new comment in body
     */
    @PostMapping
    public CommentResponseDto addComment(@Valid @RequestBody CommentRequestDto commentRequestDto) {
        return commentService.addComment(commentRequestDto);
    }

    /**
     * GET /{id} : get comment by specified id
     * @param commentId the id of the comment to retrieve
     * @return the comment with defined id in body
     */
    @GetMapping("/{id}")
    public CommentResponseDto findCommentById(@PathVariable(value = "id") Long commentId) {
        return commentService.findCommentById(commentId);
    }

    /**
     * PUT : update an existing comment
     * @param commentRequestDto the commentRequestDto to update comment
     * @return updates comment in body
     */
    @PutMapping
    public CommentResponseDto updateComment(@Valid @RequestBody CommentRequestDto commentRequestDto) {
        return commentService.updateComment(commentRequestDto);
    }

    /**
     * DELETE /{id} : delete comment by specified id
     * @param commentId the id of the comment to delete
     */
    @DeleteMapping("/{id}")
    public void deleteCommentById(@PathVariable(value = "id") Long commentId) {
        commentService.deleteCommentById(commentId);
    }
}
