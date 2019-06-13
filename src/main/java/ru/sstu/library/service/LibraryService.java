package ru.sstu.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sstu.library.entities.*;
import ru.sstu.library.repos.*;

import ru.sstu.library.entities.Book;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LibraryService {
    @Autowired
    private NewsRepo newsRepo;
    @Autowired
    private GenreRepo genreRepo;
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private SetRepo setRepo;
    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;

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

    public User getUserByLogin(String login){
        return userRepo.findByLogin(login);
    }

    public User saveUser(User user){
        return userRepo.save(user);
    }

    public Role getRoleByName(String name){
        return roleRepo.findByName(name);
    }

    public List<Book> getPopular(){
        List<Order> orderList=(List<Order>) orderRepo.findAll();
        List<Book> books;
        Map<Integer, List<Order>> mapOrders =orderList.stream()
                .collect(Collectors.groupingBy(x->x.getBook().getBook_id()));
        Collection<List<Order>> orders=mapOrders.values();
        books=orders.stream()
                .sorted((x,y)->-(x.size()-y.size()))
                .limit(10)
                .flatMap(x->x.stream())
                .map(x->x.getBook())
                .distinct()
                .collect(Collectors.toList());
        if(books.size()==0){
            books=(List<Book>) bookRepo.findAll();
            books=books.stream()
                    .limit(6)
                    .collect(Collectors.toList());
        }
        return books;
    }
    public List<Book> getLastTenBooks()
    {
        List<Book> books = (List<Book>) bookRepo.findAll();
        books=books.stream()
                .sorted((x,y)->-x.getDate_publish().compareTo(y.getDate_publish()))
                .limit(6)
                .collect(Collectors.toList());
        return books;
    }
    public List<ru.sstu.library.entities.Set>  AllSets()
    {
        List<ru.sstu.library.entities.Set> sets=(List<ru.sstu.library.entities.Set>) setRepo.findAll();
        sets.sort(Comparator.comparing(ru.sstu.library.entities.Set::getName));
        return sets;
    }
}