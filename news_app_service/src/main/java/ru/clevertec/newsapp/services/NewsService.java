package ru.clevertec.newsapp.services;

import org.springframework.data.domain.Pageable;
import ru.clevertec.newsapp.dto.news.NewsRequestDto;
import ru.clevertec.newsapp.dto.news.NewsResponseDto;

import java.util.List;

public interface NewsService {
    NewsResponseDto addNews(NewsRequestDto newsRequestDto);

    NewsResponseDto findNewsById(Long id, Pageable pageable);

    List<NewsResponseDto> findAllNews(Pageable pageable);

    List<NewsResponseDto> findAllNewsBySpecifiedParameters(String text);

    NewsResponseDto updateNews(NewsRequestDto news);

    void deleteNews(Long id);
}
