package ru.kotov.nikita.library.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
public class Genre {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy =GenerationType.SEQUENCE)
    @SequenceGenerator( name = "genreSeq", sequenceName = "GENRE_SEQ", allocationSize = 1)
    private Integer genre_id;
    private String name;
    @ManyToMany(mappedBy = "genres",fetch = FetchType.EAGER)
    private Collection<Book> books;

    @Override
    public String toString() {
        return "Genre{" +
                "genre_id=" + genre_id +
                ", name='" + name + '\'' +
                ", books=" + books +
                '}';
    }
}
