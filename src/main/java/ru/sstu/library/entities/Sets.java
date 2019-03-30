package ru.sstu.library.entities;

import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="SetsBooks")
@Data
public class Sets {
    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer setId;
    @OneToOne(mappedBy = "image")
    @JoinColumn(name = "IMAGE_ID")
    private Image image;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "Book")
    private Collection<Book> books;
    private String description;


    @Override
    public String toString() {
        return "Sets{" +
                "setId=" + setId +
                ", books=" + books +
                ", description='" + description + '\'' +
                ", image=" + image +
                '}';
    }


}
