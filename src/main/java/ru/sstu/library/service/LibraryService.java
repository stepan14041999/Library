package ru.sstu.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.sstu.library.entities.Order;
import ru.sstu.library.repos.GenreRepo;
import ru.sstu.library.repos.NewsRepo;
import ru.sstu.library.repos.OrderRepo;

public class LibraryService {
    @Autowired
    private NewsRepo newsRepo;
    @Autowired
    private GenreRepo genreRepo;
    @Autowired
    private OrderRepo orderRepo;

    public List<>
}
