package ru.sstu.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sstu.library.entities.Role;
import ru.sstu.library.entities.User;
import ru.sstu.library.repos.RoleRepo;
import ru.sstu.library.repos.UserRepo;

@Service
public class RegistrationService {
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private UserRepo userRepo;

    public Role getRoleByName(String name){
        return roleRepo.findByName(name);
    }

    public User getUserByLogin(String login){
        return userRepo.findByLogin(login);
    }

    public User saveUser(User user){
        return userRepo.save(user);
    }
}
