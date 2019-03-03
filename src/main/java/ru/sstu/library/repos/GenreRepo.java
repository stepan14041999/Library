package ru.kotov.nikita.library.repos;

import org.springframework.data.repository.CrudRepository;
import ru.kotov.nikita.library.entity.Genre;


public interface GenreRepo extends CrudRepository<Genre,Integer> {
}
