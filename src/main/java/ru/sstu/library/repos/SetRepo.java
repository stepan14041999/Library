package ru.sstu.library.repos;

import org.springframework.data.repository.CrudRepository;
import ru.sstu.library.entities.Set;

public interface SetRepo extends CrudRepository<Set, Integer> {
}
