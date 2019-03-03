package ru.kotov.nikita.library.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kotov.nikita.library.entity.Book;
import ru.kotov.nikita.library.entity.User;
import ru.kotov.nikita.library.repos.BookRepo;
import ru.kotov.nikita.library.repos.OrderRepo;
import ru.kotov.nikita.library.repos.UserRepo;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private BookRepo bookRepo;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepo.findByLogin(s);
    }

    public User addFavorites(User user, Book book){
        user=userRepo.findById(user.getUser_id()).get();
        List<Book> favorites= (List<Book>) user.getBooksFavorites();
        if(favorites.contains(book)){
            return null;
        }
        favorites.add(book);
        user.setBooksFavorites(favorites);
        return userRepo.save(user);
    }

    public User deleteFavorite(User user,Integer idBook){
        user=userRepo.findById(user.getUser_id()).get();
        List<Book> books=(List<Book>)user.getBooksFavorites();
        books.remove(bookRepo.findById(idBook).get());
        user.setBooksFavorites(books);
        return userRepo.save(user);
    }
}
