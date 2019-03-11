package ru.sstu.library.repos;

import org.springframework.data.repository.CrudRepository;
import ru.sstu.library.entities.News;

public interface NewsRepo extends CrudRepository<News, Integer> {
}
