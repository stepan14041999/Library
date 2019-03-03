package ru.kotov.nikita.library.repos;

import org.springframework.data.repository.CrudRepository;
import ru.kotov.nikita.library.entity.State;

public interface StateRepo extends CrudRepository<State, Integer> {
    State findByName(String name);
}
