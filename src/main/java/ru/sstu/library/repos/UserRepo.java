package ru.kotov.nikita.library.repos;

import org.springframework.data.repository.CrudRepository;
import ru.kotov.nikita.library.entity.User;

public interface UserRepo extends CrudRepository<User,Integer> {
    User findByLogin(String login);

    Iterable<User> findByFioIsContainingIgnoreCase(String fio);
}
