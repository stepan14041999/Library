package ru.sstu.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sstu.library.entities.Book;
import ru.sstu.library.entities.Genre;
import ru.sstu.library.entities.Order;
import ru.sstu.library.entities.User;
import ru.sstu.library.repos.GenreRepo;
import ru.sstu.library.repos.OrderRepo;
import ru.sstu.library.repos.StateRepo;
import ru.sstu.library.repos.UserRepo;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LkService {
    @Autowired
    private GenreRepo genreRepo;
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private StateRepo stateRepo;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public Iterable<Genre> getAllGenre(){
        return genreRepo.findAll();
    }

    public Iterable<Order> getAllOrder(){
        List<Order> orders=(List<Order>) orderRepo.findAll();
        orders.sort((x,y)->x.getOrder_id().compareTo(y.getOrder_id()));
        return orders;
    }
    public List<Order> getOrderActive(User user){
        List<Order> orders=(List<Order>) orderRepo.findAll();
        orders=orders.stream()
                .filter(x->x.getUser().getUser_id().equals(user.getUser_id()))
                .filter(x->x.getState().equals(stateRepo.findByName("Active")))
                .collect(Collectors.toList());
        return orders;
    }
    public List<Order> getOrderBooked(User user){
        List<Order> orders=(List<Order>) orderRepo.findAll();
        orders=orders.stream()
                .filter(x->x.getUser().getUser_id().equals(user.getUser_id()))
                .filter(x->x.getState().equals(stateRepo.findByName("Booked")))
                .sorted((x,y)->-x.getDate_start().compareTo(LocalDate.now()))
                .collect(Collectors.toList());
        return orders;
    }
    public List<Order> getOrderComplete(User user){
        List<Order> orders=(List<Order>) orderRepo.findAll();
        orders=orders.stream()
                .filter(x->x.getUser().getUser_id().equals(user.getUser_id()))
                .filter(x->x.getState().equals(stateRepo.findByName("Complete")))
                .collect(Collectors.toList());
        return orders;
    }
    public List<Book> getAllFavorites(User user){
        List<Book> books;
        List<User> users=(List<User>) userRepo.findAll();
        books=users.stream()
                .filter(x->x.getUser_id().equals(user.getUser_id()))
                .flatMap(x->x.getBooksFavorites().stream())
                .collect(Collectors.toList());
        return books;
    }
    public User changePass(Integer idUser,String password){
        User user=userRepo.findById(idUser).get();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        return userRepo.save(user);
    }
}
