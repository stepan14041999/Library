package ru.sstu.library.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name="SetsBooks")
@Data
public class Set {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer set_id;
    private String description;
    @OneToOne(optional = false)
    @JoinColumn(name = "IMAGE_ID")
    private Image image;
    @ManyToMany(fetch = FetchType.LAZY,mappedBy = "sets")
    private Collection<Book> books;



    @Override
    public String toString() {
        return "Set{" +
                "setId=" + set_id +
                ", books=" + books +
                ", description='" + description + '\'' +
                ", image=" + image +
                '}';
    }


}
