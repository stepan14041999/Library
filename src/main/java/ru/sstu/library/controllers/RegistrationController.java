package ru.sstu.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.sstu.library.entities.User;
import ru.sstu.library.service.LibraryService;

@Controller
public class RegistrationController {
    @Autowired
    private LibraryService libraryService;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @PostMapping("/registration")
    public String addUser(User user, Model model){
        User userFromDb=libraryService.getUserByLogin(user.getLogin());
        if(userFromDb!=null){
            model.addAttribute("message","true");
            model.addAttribute("user",user);
            return "registration";
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(libraryService.getRoleByName("USER"));
        libraryService.saveUser(user);
        return "success";
    }
}
