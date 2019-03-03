package ru.kotov.nikita.library.repos;

import org.springframework.data.repository.CrudRepository;
import ru.kotov.nikita.library.entity.News;

public interface NewsRepo extends CrudRepository<News, Integer> {
}
