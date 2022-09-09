package ru.clevertec.newsapp.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class News.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;
    private String title;
    private String text;
    @OneToMany(mappedBy = "id_news",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Comment> comments = new ArrayList<>();
}
