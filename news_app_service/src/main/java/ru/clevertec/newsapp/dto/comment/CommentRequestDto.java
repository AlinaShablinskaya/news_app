package ru.clevertec.newsapp.dto.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * DTO class CommentRequestDto.
 */
@Data
public class CommentRequestDto {
    private Long id;
    private final LocalDateTime date = LocalDateTime.now();
    @NotBlank
    private String text;
    @NotBlank
    private String username;
    @NotNull
    private Long id_news;
}
