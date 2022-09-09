package ru.clevertec.newsapp.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.newsapp.dto.comment.CommentRequestDto;
import ru.clevertec.newsapp.entities.Comment;
import ru.clevertec.newsapp.entities.News;
import ru.clevertec.newsapp.mappers.CommentMapper;
import ru.clevertec.newsapp.repositories.CommentRepository;
import ru.clevertec.newsapp.repositories.NewsRepository;
import ru.clevertec.newsapp.services.impl.CommentServiceImpl;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private NewsRepository newsRepository;
    @InjectMocks
    private CommentServiceImpl commentService;
    private static Comment comment;
    private static News news;
    private static CommentRequestDto commentRequestDto;

    @BeforeAll
    static void setup() {
        createTestData();
    }

    @Test
    @DisplayName("addComment should add new comment")
    void addCommentTest() {
        when(newsRepository.findById(comment.getId_news())).thenReturn(Optional.of(news));
        when(commentRepository.save(comment)).thenReturn(comment);
        commentService.addComment(commentRequestDto);
        verify(newsRepository, times(1)).findById(comment.getId_news());
        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    @DisplayName("addComment should throw entity not found exception If no such news exists")
    void addCommentWithExceptionTest() {
        when(newsRepository.findById(comment.getId_news())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> commentService.addComment(commentRequestDto));
        verify(newsRepository).findById(comment.getId_news());
        verifyNoMoreInteractions(newsRepository);
    }

    @Test
    @DisplayName("findCommentById should return comment with specified id")
    void findCommentByIdTest() {
        Long commentId = comment.getId();
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        commentService.findCommentById(commentId);
        verify(commentRepository, times(1)).findById(commentId);
    }

    @Test
    @DisplayName("findCommentById should throw entity not found exception If no such comment exists")
    void findCommentByIdWithExceptionTest() {
        Long commentId = comment.getId();
        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> commentService.findCommentById(commentId));
        verify(commentRepository).findById(commentId);
        verifyNoMoreInteractions(commentRepository);
    }

    @Test
    @DisplayName("updateComment should update comment")
    void updateCommentTest() {
        Long commentId = comment.getId();
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        when(commentRepository.save(comment)).thenReturn(comment);
        commentService.updateComment(commentRequestDto);
        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    @DisplayName("updateComment should throw entity not found exception If no such comment exists")
    void updateCommentWithExceptionTest() {
        Long commentId = comment.getId();
        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> commentService.updateComment(commentRequestDto));
        verify(commentRepository).findById(commentId);
        verifyNoMoreInteractions(commentRepository);
    }

    @Test
    @DisplayName("deleteCommentById should delete comment by id")
    void deleteCommentByIdTest() {
        Long commentId = comment.getId();
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        doNothing().when(commentRepository).deleteById(commentId);
        commentService.deleteCommentById(commentId);
        verify(commentRepository, times(1)).findById(commentId);
        verify(commentRepository, times(1)).deleteById(commentId);
    }

    @Test
    @DisplayName("deleteCommentById should throw entity not found exception If no such comment exists")
    void deleteCommentByIdWithExceptionTest() {
        Long commentId = comment.getId();
        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> commentService.deleteCommentById(commentId));
        verify(commentRepository).findById(commentId);
        verifyNoMoreInteractions(commentRepository);
    }

    private static void createTestData() {
        commentRequestDto = new CommentRequestDto();
        commentRequestDto.setId(1L);
        commentRequestDto.setUsername("Name");
        commentRequestDto.setText("Text");
        commentRequestDto.setId_news(1L);

        comment = CommentMapper.INSTANCE.convertToComment(commentRequestDto);

        news = new News();
        news.setId(1L);
        news.setTitle("Title");
        news.setText("Text");
    }
}