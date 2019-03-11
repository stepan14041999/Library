package ru.sstu.library.repos;

import org.springframework.data.repository.CrudRepository;
import ru.sstu.library.entities.Book;

import java.util.List;

public interface BookRepo extends CrudRepository<Book,Integer> {
    List<Book> findByNameContainingIgnoreCase(String name);

    List<Book> findByIsbnContainingIgnoreCase(String isbn);
}
