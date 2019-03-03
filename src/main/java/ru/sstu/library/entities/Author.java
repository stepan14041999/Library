package ru.kotov.nikita.library.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Data
@Entity
public class Author {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy =GenerationType.SEQUENCE)
    private Integer author_id;
    private String fio;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date birthday;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date die;
    @ManyToMany(mappedBy = "authors")
    private Collection<Book> books;
    @ManyToMany(mappedBy = "foreignAuthors")
    private Collection<Book> foreignBooks;

    @Override
    public String toString() {
        return "Author{" +
                "author_id=" + author_id +
                ", fio='" + fio + '\'' +
                ", birthday=" + birthday +
                ", die=" + die +
                ", books=" + books +
                '}';
    }
}
