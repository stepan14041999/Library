package ru.sstu.library.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.User;

public interface UserRepo extends CrudRepository<User,Integer> {
    User findByLogin(String login);

    Iterable<User> findByFioIsContainingIgnoreCase(String fio);
}
