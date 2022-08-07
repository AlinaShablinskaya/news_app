package ru.clevertec.newsapp.integration.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.clevertec.newsapp.dto.comment.CommentRequestDto;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Sql(value = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
class CommentControllerIT {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @DisplayName("addComment should add new comment and return this comment")
    void addCommentTest() throws Exception {
        CommentRequestDto commentRequestDto = new CommentRequestDto();
        commentRequestDto.setUsername("Name");
        commentRequestDto.setText("Text");
        commentRequestDto.setId_news(1L);

        mockMvc.perform(post("/api/comment")
                .content(objectMapper.writeValueAsString(commentRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(201)))
                .andExpect(jsonPath("$.username", is("Name")))
                .andExpect(jsonPath("$.text", is("Text")))
                .andExpect(jsonPath("$.id_news", is(1)));
    }

    @Test
    @DisplayName("findCommentById should retrieve comment by id from DB")
    void findCommentByIdTest() throws Exception {
        mockMvc.perform(get("/api/comment/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.date", is("01-07-2022 10:24:28")))
                .andExpect(jsonPath("$.text", is("The first comment to the first news")))
                .andExpect(jsonPath("$.username", is("Jack")));
    }

    @Test
    @DisplayName("updateComment should update comment and return this comment")
    void updateCommentTest() throws Exception {
        CommentRequestDto commentRequestDto = new CommentRequestDto();
        commentRequestDto.setId(1L);
        commentRequestDto.setUsername("Jack");
        commentRequestDto.setText("The modified comment");
        commentRequestDto.setId_news(1L);

        mockMvc.perform(put("/api/comment")
                .content(objectMapper.writeValueAsString(commentRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.username", is("Jack")))
                .andExpect(jsonPath("$.text", is("The modified comment")))
                .andExpect(jsonPath("$.id_news", is(1)));
    }

    @Test
    @DisplayName("deleteCommentById should delete comment by id from DB")
    void deleteCommentByIdTest() throws Exception {
        mockMvc.perform(delete("/api/comment/1"))
                .andExpect(status().isOk());
    }
}