package ru.sstu.library.repos;

import org.springframework.data.repository.CrudRepository;

import ru.sstu.library.entities.Image;

public interface ImageRepo extends CrudRepository<Image,Integer> {
}
