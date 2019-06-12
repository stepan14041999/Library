package ru.sstu.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sstu.library.entities.*;
import ru.sstu.library.repos.*;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminService {
    @Autowired
    private GenreRepo genreRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private AuthorRepo authorRepo;
    @Autowired
    private StateRepo stateRepo;

    public Genre addNewGenre(Genre genre){
        List<Genre> genres=(List<Genre>)genreRepo.findAll();
        List<String> check=genres.stream()
                .map(x->x.getName())
                .filter(x->x.equals(genre.getName()))
                .collect(Collectors.toList());
        if(check.size()>0){
            return null;
        }
        return genreRepo.save(genre);
    }
    public Map<User, Integer> getAllUsers(){
        List<User> allUsers=(List<User>)userRepo.findAll();
        Map<User,List<Order>> orders=allUsers.stream()
                .filter(x->x.getRole().equals(roleRepo.findByName("USER"))&&!x.is_blocked())
                .flatMap(x->x.getOrders().stream())
                .filter(x->x.getState().equals(stateRepo.findByName("Active"))&&x.getDate_end().isBefore(LocalDate.now()))
                .collect(Collectors.groupingBy(Order::getUser));
        final Map<User,Integer> users=new LinkedHashMap<>();
        orders.entrySet().stream()
                .sorted((x,y)->-(x.getValue().size()-y.getValue().size()))
                .forEach(x->users.put(x.getKey(),x.getValue().size()));
        return users;
    }


    public Author addNewAuthor(Author author){
        List<Author> authors=(List<Author>)authorRepo.findAll();
        List<String> check=authors.stream()
                .map(x->x.getFio())
                .filter(x->x.equals(author.getFio()))
                .collect(Collectors.toList());
        if(check.size()>0){
            return null;
        }
        return authorRepo.save(author);
    }
    public List<User> getAllLibraries(){
        List<User> users=(List<User>)userRepo.findAll();
        Role role=roleRepo.findByName("LIBRARIAN");
        users=users.stream()
                .filter(x->x.getRole().equals(role))
                .collect(Collectors.toList());
        return users;
    }
    public void deleteUser(Integer idUser){
        userRepo.deleteById(idUser);
    }


}
