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
import ru.clevertec.newsapp.dto.news.NewsRequestDto;

import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Sql(value = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
class NewsControllerIT {
    private static final String FIFTH_NEWS_TITLE = "Taron Egerton Starring In New Thriller Carry On";
    private static final String FIFTH_NEWS_TEXT = "Egerton is playing Ethan Kopek, a young TSA agent who " +
            "gets blackmailed by a mysterious traveler to...";
    private static final String FIFTH_NEWS_DATE = "05-07-2022 06:20:14";
    private static final String SIXTH_NEWS_TITLE = "Kevin Hart And Mark Wahlberg Get Into Trouble For The Me Time";
    private static final String SIXTH_NEWS_TEXT = "It is been a few years since we had a comedy from John Hamberg, " +
            "at least one he wrote and directed.";
    private static final String SIXTH_NEWS_DATE = "06-07-2022 07:36:42";
    private static final String EIGHTH_NEWS_TITLE = "With the San Diego Comic-Con ack to live";
    private static final String EIGHTH_NEWS_DATE = "08-07-2022 04:08:37";
    private static final String EIGHTH_NEWS_TEXT = "Marvel Announces The Next Avengers Movies And Much More...";
    private static final String FOURTEENTH_NEWS_TITLE = "Eternals 2 Is Happening";
    private static final String FOURTEENTH_NEWS_DATE = "14-07-2022 05:23:32";
    private static final String FOURTEENTH_NEWS_TEXT = "There still hasn’t been any official word from Marvel about an Eternals 2,...";
    private static final String FIRST_USER_COMMENT = "The first comment to the fifth news";
    private static final String SECOND_USER_COMMENT = "The second comment to the fifth news";
    private final static LocalDate DATE = LocalDate.of(2020, 12, 31);
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
    @DisplayName("addNews should add new news to DB and return this comment")
    void addNewsTest() throws Exception {
        NewsRequestDto newsRequestDto = new NewsRequestDto();
        newsRequestDto.setTitle("Title");
        newsRequestDto.setText("Text");

        mockMvc.perform(post("/api/news")
                .content(objectMapper.writeValueAsString(newsRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(21)))
                .andExpect(jsonPath("$.title", is("Title")))
                .andExpect(jsonPath("$.text", is("Text")));
    }

    @Test
    @DisplayName("findNewsById should retrieve news by id from DB")
    void findNewsByIdTest() throws Exception {
        mockMvc.perform(get("/api/news/5?page=0&size=2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(5)))
                .andExpect(jsonPath("$.title", is(FIFTH_NEWS_TITLE)))
                .andExpect(jsonPath("$.date", is(FIFTH_NEWS_DATE)))
                .andExpect(jsonPath("$.text", is(FIFTH_NEWS_TEXT)))
                .andExpect(jsonPath("$.commentsResponseDto.[0].id", is(41)))
                .andExpect(jsonPath("$.commentsResponseDto.[0].date", is("05-07-2022 06:22:14")))
                .andExpect(jsonPath("$.commentsResponseDto.[0].username", is("Jack")))
                .andExpect(jsonPath("$.commentsResponseDto.[0].text", is(FIRST_USER_COMMENT)))
                .andExpect(jsonPath("$.commentsResponseDto.[1].id", is(42)))
                .andExpect(jsonPath("$.commentsResponseDto.[1].date", is("05-07-2022 06:24:22")))
                .andExpect(jsonPath("$.commentsResponseDto.[1].username", is("Harry")))
                .andExpect(jsonPath("$.commentsResponseDto.[1].text", is(SECOND_USER_COMMENT)));
    }

    @Test
    @DisplayName("findAllNewsOnPage should show list of news on the specified page")
    void findAllNewsOnPageTest() throws Exception {
        mockMvc.perform(get("/api/news/?page=2&size=2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(5)))
                .andExpect(jsonPath("$[0].title", is(FIFTH_NEWS_TITLE)))
                .andExpect(jsonPath("$[0].date", is(FIFTH_NEWS_DATE)))
                .andExpect(jsonPath("$[0].text", is(FIFTH_NEWS_TEXT)))
                .andExpect(jsonPath("$[1].id", is(6)))
                .andExpect(jsonPath("$[1].title", is(SIXTH_NEWS_TITLE)))
                .andExpect(jsonPath("$[1].date", is(SIXTH_NEWS_DATE)))
                .andExpect(jsonPath("$[1].text", is(SIXTH_NEWS_TEXT)));
    }

    @Test
    @DisplayName("findAllNewsBySpecifiedParameters should show list of news if news text contains specified parameter")
    void findAllNewsBySpecifiedParameters() throws Exception {
        mockMvc.perform(get("/api/news/text?text=marvel"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(8)))
                .andExpect(jsonPath("$[0].title", is(EIGHTH_NEWS_TITLE)))
                .andExpect(jsonPath("$[0].date", is(EIGHTH_NEWS_DATE)))
                .andExpect(jsonPath("$[0].text", is(EIGHTH_NEWS_TEXT)))
                .andExpect(jsonPath("$[1].id", is(14)))
                .andExpect(jsonPath("$[1].title", is(FOURTEENTH_NEWS_TITLE)))
                .andExpect(jsonPath("$[1].date", is(FOURTEENTH_NEWS_DATE)))
                .andExpect(jsonPath("$[1].text", is(FOURTEENTH_NEWS_TEXT)));
    }

    @Test
    @DisplayName("updateNews should update news in DB and return this news")
    void updateNewsTest() throws Exception {
        NewsRequestDto newsRequestDto = new NewsRequestDto();
        newsRequestDto.setId(5L);
        newsRequestDto.setTitle("New Title");
        newsRequestDto.setText(FIFTH_NEWS_TEXT);

        mockMvc.perform(put("/api/news")
                .content(objectMapper.writeValueAsString(newsRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(5)))
                .andExpect(jsonPath("$.title", is("New Title")))
                .andExpect(jsonPath("$.text", is(FIFTH_NEWS_TEXT)));
    }

    @Test
    @DisplayName("deleteNews should delete news by id from DB")
    void deleteNewsTest() throws Exception {
        mockMvc.perform(delete("/api/news/1"))
                .andExpect(status().isOk());
    }
}