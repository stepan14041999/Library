package ru.kotov.nikita.library.repos;

import org.springframework.data.repository.CrudRepository;
import ru.kotov.nikita.library.entity.Level;

public interface LevelRepo extends CrudRepository<Level,Integer> {
}
