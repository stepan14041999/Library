package ru.sstu.library.repos;

import org.springframework.data.repository.CrudRepository;
import ru.sstu.library.entities.Role;

public interface RoleRepo extends CrudRepository<Role,Integer> {
    Role findByName(String name);
}
