package ru.kotov.nikita.library.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
public class Hall {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy =GenerationType.SEQUENCE)
    private Integer hall_id;
    private String name;
    @OneToMany(mappedBy = "hall",fetch = FetchType.EAGER)
    private Collection<Book> books;

    @Override
    public String toString() {
        return "Hall{" +
                "hall_id=" + hall_id +
                ", name='" + name + '\'' +
                '}';
    }
}
