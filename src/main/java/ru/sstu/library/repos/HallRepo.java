package ru.sstu.library.repos;

import org.springframework.data.repository.CrudRepository;
import ru.sstu.library.entities.Hall;

public interface HallRepo extends CrudRepository<Hall,Integer> {
}
