package ru.clevertec.newsapp.dto.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO class CommentResponseDto.
 */
@Data
public class CommentResponseDto {
    private Long id;
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime date;
    private String text;
    private String username;
    private Long id_news;
}
