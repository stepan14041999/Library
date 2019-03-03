package ru.kotov.nikita.library.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
public class Image {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy =GenerationType.SEQUENCE)
    private Integer image_id;
    private String link;
    @OneToMany(mappedBy = "image")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<Book> books;
    @OneToMany(mappedBy = "imageNews")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<News> news;

    @Override
    public String toString() {
        return "Image{" +
                "image_id=" + image_id +
                ", link='" + link + '\'' +
                '}';
    }
}
