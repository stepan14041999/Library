package ru.sstu.library.repos;

import org.springframework.data.repository.CrudRepository;
import ru.sstu.library.entities.Author;

import java.util.List;

public interface AuthorRepo extends CrudRepository<Author,Integer> {
    List<Author> findByFioIsContainingIgnoreCase(String fio);
}
