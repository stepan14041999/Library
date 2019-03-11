package ru.sstu.library.repos;

import org.springframework.data.repository.CrudRepository;
import ru.sstu.library.entities.Genre;


public interface GenreRepo extends CrudRepository<Genre,Integer> {
}
