package ru.kotov.nikita.library.repos;

import org.springframework.data.repository.CrudRepository;
import ru.kotov.nikita.library.entity.Order;

public interface OrderRepo extends CrudRepository<Order,Integer> {
}
