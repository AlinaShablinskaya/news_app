package ru.clevertec.newsapp.dto.news;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.clevertec.newsapp.dto.comment.CommentResponseDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO class NewsResponseDto.
 */
@Data
public class NewsResponseDto {
    private Long id;
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime date;
    private String title;
    private String text;
    private List<CommentResponseDto> commentsResponseDto;
}
