package ru.clevertec.newsapp.mappers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.clevertec.newsapp.dto.comment.CommentRequestDto;
import ru.clevertec.newsapp.dto.comment.CommentResponseDto;
import ru.clevertec.newsapp.entities.Comment;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {CommentMapperImpl.class})
class CommentMapperTest {

    @Test
    @DisplayName("Test should  convert Comment Response Dto to comment entity")
    void convertToComment() {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setUsername("Name");
        comment.setText("Text");
        comment.setId_news(1L);

        CommentResponseDto commentResponseDto = CommentMapper.INSTANCE.convertToCommentResponseDto(comment);
        assertEquals(comment.getId(), commentResponseDto.getId());
        assertEquals(comment.getUsername(), commentResponseDto.getUsername());
        assertEquals(comment.getText(), commentResponseDto.getText());
        assertEquals(comment.getId_news(), commentResponseDto.getId_news());

    }

    @Test
    @DisplayName("Test should  convert comment entity to Comment Response Dto")
    void convertToCommentResponseDto() {
        CommentRequestDto commentRequestDto = new CommentRequestDto();
        commentRequestDto.setId(1L);
        commentRequestDto.setUsername("Name");
        commentRequestDto.setText("Text");
        commentRequestDto.setId_news(1L);

        Comment comment = CommentMapper.INSTANCE.convertToComment(commentRequestDto);
        assertEquals(commentRequestDto.getId(), comment.getId());
        assertEquals(commentRequestDto.getUsername(), comment.getUsername());
        assertEquals(commentRequestDto.getText(), comment.getText());
        assertEquals(commentRequestDto.getId_news(), comment.getId_news());
    }
}