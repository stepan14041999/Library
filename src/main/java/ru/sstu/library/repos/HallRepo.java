package ru.kotov.nikita.library.repos;

import org.springframework.data.repository.CrudRepository;
import ru.kotov.nikita.library.entity.Hall;

public interface HallRepo extends CrudRepository<Hall,Integer> {
}
