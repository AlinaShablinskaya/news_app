package ru.clevertec.newsapp.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.newsapp.dto.news.NewsRequestDto;
import ru.clevertec.newsapp.dto.news.NewsResponseDto;
import ru.clevertec.newsapp.entities.News;
import ru.clevertec.newsapp.mappers.NewsMapper;
import ru.clevertec.newsapp.repositories.NewsRepository;
import ru.clevertec.newsapp.services.impl.NewsServiceImpl;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewsServiceTest {
    @Mock
    private NewsRepository newsRepository;
    @InjectMocks
    private NewsServiceImpl newsService;
    private static News news;
    private static NewsRequestDto newsRequestDto;
    private static NewsResponseDto newsResponseDto;

    @BeforeAll
    static void setup() {
        createTestData();
    }

    @Test
    @DisplayName("addNews should add new news")
    void addNewsTest() {
        when(newsRepository.save(news)).thenReturn(news);
        newsService.addNews(newsRequestDto);
        verify(newsRepository, times(1)).save(news);
    }

    @Test
    @DisplayName("findNewsById should return news with specified id")
    void findNewsByIdTest() {
        Pageable paging = PageRequest.of(0, 2);
        Long newsId = news.getId();
        when(newsRepository.findById(newsId)).thenReturn(Optional.of(news));
        newsService.findNewsById(newsId, paging);
        verify(newsRepository, times(1)).findById(newsId);
    }

    @Test
    @DisplayName("findNewsById should throw entity not found exception If no such news exists")
    void findNewsByIdWithExceptionTest() {
        Pageable paging = PageRequest.of(0, 2);
        Long newsId = news.getId();
        when(newsRepository.findById(newsId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> newsService.findNewsById(newsId, paging));
        verify(newsRepository).findById(newsId);
        verifyNoMoreInteractions(newsRepository);
    }

    @Test
    @DisplayName("findAllNews should return list of all news on page")
    void findAllNewsTest() {
        List<News> newsList = new ArrayList<>();
        Pageable paging = PageRequest.of(0, 2);
        Page<News> page = new PageImpl<>(newsList);
        when(newsRepository.findAll(paging)).thenReturn(page);
        newsService.findAllNews(paging);
        verify(newsRepository, times(1)).findAll(paging);
    }

    @Test
    @DisplayName("findAllNews should return list of all news where news text contains specified parameter")
    void findAllNewsBySpecifiedParametersTest() {
        List<News> newsList = new ArrayList<>();
        Pageable paging = PageRequest.of(0, 2);
        Page<News> page = new PageImpl<>(newsList);
        when(newsRepository.findAll(paging)).thenReturn(page);
        newsService.findAllNews(paging);
        verify(newsRepository, times(1)).findAll(paging);
    }

    @Test
    @DisplayName("updateNews should update news")
    void updateNewsTest() {
        Long newsId = news.getId();
        when(newsRepository.findById(newsId)).thenReturn(Optional.of(news));
        when(newsRepository.save(news)).thenReturn(news);
        newsService.updateNews(newsRequestDto);
        verify(newsRepository, times(1)).save(news);
    }

    @Test
    @DisplayName("updateNews should throw entity not found exception If no such news exists")
    void updateNewsWithExceptionTest() {
        Long newsId = news.getId();
        when(newsRepository.findById(newsId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> newsService.updateNews(newsRequestDto));
        verify(newsRepository).findById(newsId);
        verifyNoMoreInteractions(newsRepository);
    }

    @Test
    @DisplayName("deleteNews should delete news by id")
    void deleteNewsTest() {
        Long newsId = news.getId();
        when(newsRepository.findById(newsId)).thenReturn(Optional.of(news));
        doNothing().when(newsRepository).deleteById(newsId);
        newsService.deleteNews(newsId);
        verify(newsRepository, times(1)).findById(newsId);
        verify(newsRepository, times(1)).deleteById(newsId);
    }

    @Test
    @DisplayName("deleteNews should throw entity not found exception If no such news exists")
    void deleteNewsWithExceptionTest() {
        Long newsId = news.getId();
        when(newsRepository.findById(newsId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> newsService.deleteNews(newsId));
        verify(newsRepository).findById(newsId);
        verifyNoMoreInteractions(newsRepository);
    }

    private static void createTestData() {
        newsRequestDto = new NewsRequestDto();
        newsRequestDto.setId(1L);
        newsRequestDto.setTitle("Title");
        newsRequestDto.setText("Text");

        news = NewsMapper.INSTANCE.convertToNews(newsRequestDto);
        newsResponseDto = NewsMapper.INSTANCE.convertToNewsResponseDto(news);
    }
}