package ru.kotov.nikita.library.repos;

import org.springframework.data.repository.CrudRepository;
import ru.kotov.nikita.library.entity.Book;

import java.util.List;

public interface BookRepo extends CrudRepository<Book,Integer> {
    List<Book> findByNameContainingIgnoreCase(String name);

    List<Book> findByIsbnContainingIgnoreCase(String isbn);
}
