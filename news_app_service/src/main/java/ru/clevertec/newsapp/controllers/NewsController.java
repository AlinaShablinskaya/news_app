package ru.clevertec.newsapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.newsapp.dto.news.NewsRequestDto;
import ru.clevertec.newsapp.dto.news.NewsResponseDto;
import ru.clevertec.newsapp.services.NewsService;

import javax.validation.Valid;
import java.util.List;

/**
 * REST controller for managing news.
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/news")
public class NewsController {
    private final NewsService newsService;

    /**
     * POST : create a new news
     * @param newsRequestDto the newsRequestDto to create
     * @return the new news in body
     */
    @PostMapping
    public NewsResponseDto addNews(@Valid @RequestBody NewsRequestDto newsRequestDto) {
        return newsService.addNews(newsRequestDto);
    }

    /**
     * GET /{id} : get news by specified id
     * @param newsId the id of the news to retrieve
     * @param pageable display comments on the page-number and page-size parameters specified
     * @return the news with defined id in body
     */
    @GetMapping("/{id}")
    public NewsResponseDto findNewsById(@PathVariable(value = "id") Long newsId, Pageable pageable) {
        return newsService.findNewsById(newsId, pageable);
    }

    /**
     * GET : get all news on page
     * @param pageable display news on the page-number and page-size parameters specified
     * @return the list of news in body
     */
    @GetMapping
    public List<NewsResponseDto> findAllNewsOnPage(Pageable pageable) {
        return newsService.findAllNews(pageable);
    }

    /**
     * GET /text : get all news by specified parameter
     * @param text the parameter to retrieve list of all news
     * @return the list of all news with specified parameter
     */
    @GetMapping("/text")
    public List<NewsResponseDto> findAllNewsBySpecifiedParameters(@RequestParam(value = "text") String text) {
        return newsService.findAllNewsBySpecifiedParameters(text);
    }

    /**
     * PUT : update an existing news
     * @param newsRequestDto the newsRequestDto to update news
     * @return updates news in body
     */
    @PutMapping
    public NewsResponseDto updateNews(@Valid @RequestBody NewsRequestDto newsRequestDto) {
        return newsService.updateNews(newsRequestDto);
    }

    /**
     * DELETE /{id} : delete news by specified id
     * @param newsId the id of the news to delete
     */
    @DeleteMapping("/{id}")
    public void deleteNews(@PathVariable(value = "id") Long newsId) {
        newsService.deleteNews(newsId);
    }
}
