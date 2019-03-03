package ru.kotov.nikita.library.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
public class State {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer state_id;
    private String name;
    @OneToMany(mappedBy = "state")
    private Collection<Order> orders;

    @Override
    public String toString() {
        return "State{" +
                "state_id=" + state_id +
                ", state_name='" + name + '\'' +
                '}';
    }
}
