package ru.clevertec.newsapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.newsapp.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
