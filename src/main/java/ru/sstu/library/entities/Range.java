package ru.kotov.nikita.library.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
public class Range {
    @Id
    @GeneratedValue(strategy =GenerationType.SEQUENCE)
    @Setter(AccessLevel.NONE)
    private Integer range_id;
    private String name;
    @OneToMany(mappedBy = "range",fetch = FetchType.EAGER)
    private Collection<Book> books;

    @Override
    public String toString() {
        return "Range{" +
                "range_id=" + range_id +
                ", name='" + name + '\'' +
                '}';
    }
}
