package ru.kotov.nikita.library.repos;

import org.springframework.data.repository.CrudRepository;
import ru.kotov.nikita.library.entity.Image;

public interface ImageRepo extends CrudRepository<Image,Integer> {
}
