package ru.sstu.library.repos;

import org.springframework.data.repository.CrudRepository;
import ru.sstu.library.entities.Level;

public interface LevelRepo extends CrudRepository<Level,Integer> {
}
