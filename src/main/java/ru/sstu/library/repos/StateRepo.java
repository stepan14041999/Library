package ru.sstu.library.repos;

import org.springframework.data.repository.CrudRepository;
import ru.sstu.library.entities.State;

public interface StateRepo extends CrudRepository<State, Integer> {
    State findByName(String name);
}
