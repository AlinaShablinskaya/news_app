package ru.clevertec.newsapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.clevertec.newsapp.dto.news.NewsRequestDto;
import ru.clevertec.newsapp.dto.news.NewsResponseDto;
import ru.clevertec.newsapp.entities.News;

/**
 * Mapper class for news
 */
@Mapper
public interface NewsMapper {
    NewsMapper INSTANCE = Mappers.getMapper(NewsMapper.class);

    /**
     * Converts entity to Dto
     * @param news entity to convert
     * @return NewsResponseDto
     */
    @Mapping(source = "comments", target = "commentsResponseDto")
    NewsResponseDto convertToNewsResponseDto(News news);

    /**
     * Converts Dto to entity
     * @param newsRequestDto Dto to convert
     * @return news entity
     */
    @Mapping(target = "comments", ignore = true)
    News convertToNews(NewsRequestDto newsRequestDto);
}
