package ru.kotov.nikita.library.repos;

import org.springframework.data.repository.CrudRepository;
import ru.kotov.nikita.library.entity.Range;

public interface RangeRepo extends CrudRepository<Range,Integer> {
}
