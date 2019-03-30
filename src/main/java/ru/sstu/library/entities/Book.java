package ru.sstu.library.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

@Entity
@Data
public class Book {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy =GenerationType.SEQUENCE)
    private Integer book_id;
    private String name;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate year_publish;
    private String description;
    @Column(name = "count_book_library")
    private Integer countInLibrary;
    private String isbn;
    @Column(name="countpages")
    private Integer countPages;
    @ManyToOne(optional = false)
    @JoinColumn(name="IMAGE_ID")
    private Image image;
    @ManyToOne(optional = false)
    @JoinColumn(name="HALL_ID")
    private Hall hall;
    @ManyToOne(optional = false)
    @JoinColumn(name="RANGE_ID")
    private Range range;
    @ManyToOne(optional = false)
    @JoinColumn(name="LEVEL_ID")
    private Level level;
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany()
    @JoinTable(name = "BOOKSETS",joinColumns = @JoinColumn(name="SET_ID"),inverseJoinColumns =@JoinColumn(name="SET_ID"))
    private Collection<Sets> sets;
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany()
    @JoinTable(name = "BOOKTOAUTHOR",joinColumns = @JoinColumn(name="BOOK_ID"),inverseJoinColumns =@JoinColumn(name="AUTHOR_ID"))
    private Collection<Author> authors;
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany()
    @JoinTable(name = "BOOKTOGENRE",joinColumns = @JoinColumn(name = "BOOK_ID"),inverseJoinColumns = @JoinColumn(name = "GENRE_ID"))
    private Collection<Genre> genres;
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany()
    @JoinTable(name="BOOKTOFOREIGNAUTHOR",joinColumns = @JoinColumn(name = "BOOK_ID"),inverseJoinColumns = @JoinColumn(name = "AUTHOR_ID"))
    private Collection<Author> foreignAuthors;
    @ManyToMany(mappedBy = "booksFavorites")
    private Collection<User> usersFavorites;
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private LocalDate date_publish;

    @Override
    public String toString() {
        return "Book{" +
                "book_id=" + book_id +
                ", name='" + name + '\'' +
                ", year_publish=" + year_publish +
                ", description='" + description + '\'' +
                ", count=" + countInLibrary +
                '}';
    }
}
