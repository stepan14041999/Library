package ru.sstu.library.repos;

import org.springframework.data.repository.CrudRepository;
import ru.sstu.library.entities.Order;

public interface OrderRepo extends CrudRepository<Order,Integer> {
}
