package ru.sstu.library.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@Table(name = "LEVEL_RG")
public class Level {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy =GenerationType.SEQUENCE)
    private Integer level_id;
    private String name;
    @OneToMany(mappedBy = "level",fetch = FetchType.EAGER)
    private Collection<Book> books;

    @Override
    public String toString() {
        return "Level{" +
                "level_id=" + level_id +
                ", name='" + name + '\'' +
                '}';
    }
}


