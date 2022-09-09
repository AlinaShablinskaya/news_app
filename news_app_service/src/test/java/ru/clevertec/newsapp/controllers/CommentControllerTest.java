package ru.clevertec.newsapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.newsapp.dto.comment.CommentRequestDto;
import ru.clevertec.newsapp.dto.comment.CommentResponseDto;
import ru.clevertec.newsapp.entities.Comment;
import ru.clevertec.newsapp.mappers.CommentMapper;
import ru.clevertec.newsapp.services.CommentService;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CommentService commentService;
    @Autowired
    private ObjectMapper objectMapper;
    private static CommentRequestDto firstCommentRequest;
    private static CommentResponseDto firstCommentResponse;

    @BeforeAll
    static void setup() {
        createTestData();
    }

    @Test
    @DisplayName("addComment should add new comment and return this comment")
    void addComment() throws Exception {
        Mockito.when(commentService.addComment(firstCommentRequest)).thenReturn(firstCommentResponse);

        mockMvc.perform(post("/api/comment")
                        .content(objectMapper.writeValueAsString(firstCommentRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(commentService).addComment(firstCommentRequest);
    }

    @Test
    @DisplayName("findCommentById should retrieve comment by id from DB")
    void findCommentById() throws Exception {
        Long commentId = 1L;
        Mockito.when(commentService.findCommentById(commentId)).thenReturn(firstCommentResponse);

        mockMvc.perform(get("/api/comment/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(commentService).findCommentById(commentId);
    }

    @Test
    @DisplayName("updateComment should update comment and return this comment")
    void updateComment() throws Exception {
        Mockito.when(commentService.updateComment(firstCommentRequest)).thenReturn(firstCommentResponse);

        mockMvc.perform(put("/api/comment")
                        .content(objectMapper.writeValueAsString(firstCommentRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(commentService).updateComment(firstCommentRequest);
    }

    @Test
    @DisplayName("deleteCommentById should delete comment by id from DB")
    void deleteCommentById() throws Exception {
        Long commentId = 1L;
        doNothing().when(commentService).deleteCommentById(commentId);

        mockMvc.perform(delete("/api/comment/1"))
                .andExpect(status().isOk());

        Mockito.verify(commentService).deleteCommentById(commentId);
    }

    private static void createTestData() {
        firstCommentRequest = new CommentRequestDto();
        firstCommentRequest.setId(1L);
        firstCommentRequest.setUsername("Name");
        firstCommentRequest.setText("Text");
        firstCommentRequest.setId_news(1L);

        Comment firstComment = CommentMapper.INSTANCE.convertToComment(firstCommentRequest);

        firstCommentResponse = CommentMapper.INSTANCE.convertToCommentResponseDto(firstComment);
    }
}