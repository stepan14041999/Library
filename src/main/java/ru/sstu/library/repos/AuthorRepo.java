package ru.kotov.nikita.library.repos;

import org.springframework.data.repository.CrudRepository;
import ru.kotov.nikita.library.entity.Author;

import java.util.List;

public interface AuthorRepo extends CrudRepository<Author,Integer> {
    List<Author> findByFioIsContainingIgnoreCase(String fio);
}
