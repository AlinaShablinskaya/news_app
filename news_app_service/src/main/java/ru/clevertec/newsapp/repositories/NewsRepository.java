package ru.clevertec.newsapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.newsapp.entities.News;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findAllByTextContainingIgnoreCase(String text);
}
