package ru.kotov.nikita.library.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer news_id;
    private String title;
    private String description;
    private LocalDate date_publish;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IMAGE_ID")
    private Image imageNews;

    @Override
    public String toString() {
        return "News{" +
                "news_id=" + news_id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date_publish=" + date_publish +
                '}';
    }
}
