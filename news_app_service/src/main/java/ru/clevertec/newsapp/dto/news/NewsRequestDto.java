package ru.clevertec.newsapp.dto.news;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * DTO class NewsRequestDto.
 */
@Data
public class NewsRequestDto {
    private Long id;
    @JsonIgnore
    private final LocalDateTime date = LocalDateTime.now();
    @NotBlank
    private String title;
    @NotBlank
    private String text;
}
