package com.springboot.news.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@NamedQuery(
        name = "Article.findByTitleOrContent",
        query = "SELECT a FROM Article a WHERE a.title LIKE CONCAT('%', :query, '%') OR a.content LIKE CONCAT('%', :query, '%')"
)
@Table(
        name = "articles", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})}
)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "imageURL", nullable = false)
    private String imageURL;

    @UpdateTimestamp
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}
