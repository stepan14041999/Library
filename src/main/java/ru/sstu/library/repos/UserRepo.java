package ru.sstu.library.repos;

import org.springframework.data.repository.CrudRepository;
import ru.sstu.library.entities.User;


public interface UserRepo extends CrudRepository<User,Integer> {
    User findByLogin(String login);

    Iterable<User> findByFioIsContainingIgnoreCase(String fio);
}
