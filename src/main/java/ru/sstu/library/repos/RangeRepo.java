package ru.sstu.library.repos;

import org.springframework.data.repository.CrudRepository;
import ru.sstu.library.entities.Range;

public interface RangeRepo extends CrudRepository<Range,Integer> {
}
