package ru.clevertec.newsapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.newsapp.dto.comment.CommentRequestDto;
import ru.clevertec.newsapp.dto.news.NewsRequestDto;
import ru.clevertec.newsapp.dto.news.NewsResponseDto;
import ru.clevertec.newsapp.entities.Comment;
import ru.clevertec.newsapp.entities.News;
import ru.clevertec.newsapp.mappers.CommentMapper;
import ru.clevertec.newsapp.mappers.NewsMapper;
import ru.clevertec.newsapp.services.CommentService;
import ru.clevertec.newsapp.services.NewsService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NewsController.class)
class NewsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private NewsService newsService;
    @Autowired
    private ObjectMapper objectMapper;
    private static NewsRequestDto firstNewRequest;
    private static NewsRequestDto secondNewRequest;
    private static NewsResponseDto firstNewResponse;
    private static NewsResponseDto secondNewResponse;
    private static List<NewsResponseDto> newsList;

    @BeforeAll
    static void setup() {
        createTestData();
    }

    @Test
    @DisplayName("addNews should add new news to DB and return this comment")
    void addNews() throws Exception {
        Mockito.when(newsService.addNews(firstNewRequest)).thenReturn(firstNewResponse);

        mockMvc.perform(post("/api/news")
                        .content(objectMapper.writeValueAsString(firstNewRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(newsService).addNews(firstNewRequest);
    }

    @Test
    @DisplayName("findNewsById should retrieve news by id from DB")
    void findNewsById() throws Exception {
        Long newsId = 1L;
        Pageable paging = PageRequest.of(0, 2);
        Mockito.when(newsService.findNewsById(newsId, paging)).thenReturn(firstNewResponse);

        mockMvc.perform(get("/api/news/1?page=0&size=2"))
                .andExpect(status().isOk());

        Mockito.verify(newsService).findNewsById(newsId, paging);
    }

    @Test
    @DisplayName("findAllNewsOnPage should show list of news on the specified page")
    void findAllNewsOnPage() throws Exception {
        Pageable paging = PageRequest.of(0, 2);
        Mockito.when(newsService.findAllNews(paging)).thenReturn(newsList);

        mockMvc.perform(get("/api/news/?page=0&size=2"))
                .andExpect(status().isOk());

        Mockito.verify(newsService).findAllNews(paging);
    }

    @Test
    @DisplayName("findAllNewsBySpecifiedParameters should show list of news if news text contains specified parameter")
    void findAllNewsBySpecifiedParameters() throws Exception {
        String parameter = "title";
        Mockito.when(newsService.findAllNewsBySpecifiedParameters(parameter)).thenReturn(newsList);

        mockMvc.perform(get("/api/news/text?text=title"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));

        Mockito.verify(newsService).findAllNewsBySpecifiedParameters(parameter);
    }

    @Test
    @DisplayName("updateNews should update news in DB and return this news")
    void updateNews() throws Exception {
        Mockito.when(newsService.updateNews(firstNewRequest)).thenReturn(firstNewResponse);

        mockMvc.perform(put("/api/news")
                        .content(objectMapper.writeValueAsString(firstNewRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(newsService).updateNews(firstNewRequest);
    }

    @Test
    @DisplayName("deleteNews should delete news by id from DB")
    void deleteNews() throws Exception {
        Long newsId = 1L;
        doNothing().when(newsService).deleteNews(newsId);

        mockMvc.perform(delete("/api/news/1"))
                .andExpect(status().isOk());

        Mockito.verify(newsService).deleteNews(newsId);
    }

    private static void createTestData() {
        firstNewRequest = new NewsRequestDto();
        firstNewRequest.setId(1L);
        firstNewRequest.setTitle("First title");
        firstNewRequest.setText("First Text");

        secondNewRequest = new NewsRequestDto();
        secondNewRequest.setId(2L);
        secondNewRequest.setTitle("Second title");
        secondNewRequest.setText("Second Text");


        News firstNews = NewsMapper.INSTANCE.convertToNews(firstNewRequest);
        News secondNews = NewsMapper.INSTANCE.convertToNews(secondNewRequest);

        firstNewResponse = NewsMapper.INSTANCE.convertToNewsResponseDto(firstNews);
        secondNewResponse = NewsMapper.INSTANCE.convertToNewsResponseDto(secondNews);

        newsList = List.of(firstNewResponse, secondNewResponse);
    }
}