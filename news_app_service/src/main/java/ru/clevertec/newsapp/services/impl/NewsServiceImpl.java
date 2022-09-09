package ru.clevertec.newsapp.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.newsapp.dto.news.NewsRequestDto;
import ru.clevertec.newsapp.dto.news.NewsResponseDto;
import ru.clevertec.newsapp.entities.Comment;
import ru.clevertec.newsapp.entities.News;
import ru.clevertec.newsapp.mappers.NewsMapper;
import ru.clevertec.newsapp.repositories.NewsRepository;
import ru.clevertec.newsapp.services.NewsService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing news.
 */
@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private static final String NEWS_NOT_FOUND_EXCEPTION = "Specified news is not found";
    private final NewsRepository newsRepository;

    /**
     * Returns created news in table mapped by calling newsRepository.save(news)
     * @param newsRequestDto the entity to save
     * @return non-null value with defined id
     */
    @Override
    @Cacheable("news")
    public NewsResponseDto addNews(NewsRequestDto newsRequestDto) {
        News news = NewsMapper.INSTANCE.convertToNews(newsRequestDto);
        news = newsRepository.save(news);
        return NewsMapper.INSTANCE.convertToNewsResponseDto(news);
    }

    /**
     * Returns the news with the comments by the defined id by calling newsRepository.findById(id)
     * if specified news exists, otherwise throws EntityNotFoundException
     * @param id the id of the news to retrieve
     * @param pageable display comments on the page-number and page-size parameters specified
     * @throws EntityNotFoundException if news with specified id is not present in database
     * @return the non-null news with defined id
     */
    @Override
    @Cacheable(value = "news", key = "#id")
    public NewsResponseDto findNewsById(Long id, Pageable pageable){
        News news = newsRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(NEWS_NOT_FOUND_EXCEPTION));
        List<Comment> comments = findCommentToNewsForSpecifiedPage(news, pageable);
        news.setComments(comments);
        return  NewsMapper.INSTANCE.convertToNewsResponseDto(news);
    }

    /**
     * Returns the list of all news by calling newsRepository.findAll(pageable)
     * @param pageable display news on the page-number and page-size parameters specified
     * @return the list of all news
     */
    @Override
    public List<NewsResponseDto> findAllNews(Pageable pageable) {
        List<News> newsList = newsRepository.findAll(pageable).toList();
        return newsList.stream()
                .map(NewsMapper.INSTANCE::convertToNewsResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Returns the list of all news where news text contains specified parameter
     * by calling newsRepository.findAllByTextContainingIgnoreCase(text)
     * @param text the parameter to retrieve list of all news
     * @return the list of all news with specified parameter
     */
    @Override
    public List<NewsResponseDto> findAllNewsBySpecifiedParameters(String text) {
        List<News> newsList = newsRepository.findAllByTextContainingIgnoreCase(text);
        return newsList.stream()
                .map(NewsMapper.INSTANCE::convertToNewsResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Updates news in table mapped by calling newsRepository.save(news) if specified news exists,
     * otherwise throws EntityNotFoundException
     * @param newsRequestDto value to update news in database
     * @throws EntityNotFoundException if news with id defined in param news is not present in database
     * @return updates news
     */
    @Override
    @CachePut("news")
    public NewsResponseDto updateNews(NewsRequestDto newsRequestDto) {
        News news = NewsMapper.INSTANCE.convertToNews(newsRequestDto);
        if (newsRepository.findById(news.getId()).isEmpty()) {
            throw new EntityNotFoundException(NEWS_NOT_FOUND_EXCEPTION);
        }
        news = newsRepository.save(news);
        return NewsMapper.INSTANCE.convertToNewsResponseDto(news);
    }

    /**
     * Deletes news with param id from the table mapped by calling newsRepository.deleteById(id)
     * if specified news exists, otherwise throws EntityNotFoundException
     * @throws EntityNotFoundException if news with specified id is not present in database
     * @param id id the id of the news to delete
     */
    @Override
    @CacheEvict(value = "news")
    public void deleteNews(Long id) {
        if (newsRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException(NEWS_NOT_FOUND_EXCEPTION);
        }
        newsRepository.deleteById(id);
    }

    /**
     * Returns the list of all comment on the page-number and page-size parameters specified
     * @param news the entity for receiving comments
     * @param pageable display comments on the page-number and page-size parameters specified
     * @return the list of all comments
     */
    private List<Comment> findCommentToNewsForSpecifiedPage(News news, Pageable pageable) {
        return news.getComments().stream()
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());
    }
}
