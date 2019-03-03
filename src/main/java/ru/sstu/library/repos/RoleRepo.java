package ru.kotov.nikita.library.repos;

import org.springframework.data.repository.CrudRepository;
import ru.kotov.nikita.library.entity.Role;

public interface RoleRepo extends CrudRepository<Role,Integer> {
    Role findByName(String name);
}
