package ru.clevertec.newsapp.mappers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.clevertec.newsapp.dto.comment.CommentResponseDto;
import ru.clevertec.newsapp.dto.news.NewsRequestDto;
import ru.clevertec.newsapp.dto.news.NewsResponseDto;
import ru.clevertec.newsapp.entities.Comment;
import ru.clevertec.newsapp.entities.News;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {NewsMapperImpl.class})
class NewsMapperTest {

    @Test
    @DisplayName("Test should  convert news entity to News Response Dto")
    void convertToNewsResponseDto() {
        NewsRequestDto newsRequestDto = new NewsRequestDto();
        newsRequestDto.setId(1L);
        newsRequestDto.setTitle("First title");
        newsRequestDto.setText("First Text");

        News news = NewsMapper.INSTANCE.convertToNews(newsRequestDto);
        assertEquals(news.getId(), newsRequestDto.getId());
        assertEquals(news.getTitle(), newsRequestDto.getTitle());
        assertEquals(news.getText(), newsRequestDto.getText());
    }

    @Test
    @DisplayName("Test should  convert News Response Dto to news entity")
    void convertToNews() {
        News news = new News();
        news.setId(1L);
        news.setTitle("First title");
        news.setText("First Text");

        NewsResponseDto newsResponseDto = NewsMapper.INSTANCE.convertToNewsResponseDto(news);
        assertEquals(news.getId(), newsResponseDto.getId());
        assertEquals(news.getTitle(), newsResponseDto.getTitle());
        assertEquals(news.getText(), newsResponseDto.getText());
    }
}