package ru.sstu.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sstu.library.entities.Genre;
import ru.sstu.library.entities.News;
import ru.sstu.library.entities.Order;
import ru.sstu.library.repos.GenreRepo;
import ru.sstu.library.repos.NewsRepo;
import ru.sstu.library.repos.OrderRepo;

import java.util.Comparator;
import java.util.List;

@Service
public class LibraryService {
    @Autowired
    private NewsRepo newsRepo;
    @Autowired
    private GenreRepo genreRepo;
    @Autowired
    private OrderRepo orderRepo;

    public List<News> getAllNews(){
        List<News> news=(List<News>) newsRepo.findAll();
        news.sort((x,y)->-x.getDate_publish().compareTo(y.getDate_publish()));
        return news;
    }

    public List<Genre> getAllGenres(){
        List<Genre> genres=(List<Genre>) genreRepo.findAll();
        genres.sort(Comparator.comparing(Genre::getName));
        return genres;
    }
}
