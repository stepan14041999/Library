package ru.kotov.nikita.library.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "ORDER_LIBRARY")
public class Order {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy =GenerationType.SEQUENCE)
    private Integer order_id;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate date_start;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate date_end;
    @ManyToOne(optional = false)
    @JoinColumn(name="USER_ID")
    private User user;
    @ManyToOne(optional = false)
    @JoinColumn(name = "BOOK_ID")
    private Book book;
    @ManyToOne(optional = false)
    @JoinColumn(name="STATE_ID")
    private State state;
    @Override
    public String toString() {
        return "Order{" +
                "order_id=" + order_id +
                ", date_start=" + date_start +
                ", date_end=" + date_end +
                '}';
    }
}
