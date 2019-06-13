package ru.sstu.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import ru.sstu.library.entities.User;
import ru.sstu.library.service.LibraryService;
import ru.sstu.library.service.RegistrationService;

@Controller
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private LibraryService libraryService;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @PostMapping("/registration")
    public String addUser(User user, Model model) {
        User userFromDb = registrationService.getUserByLogin(user.getLogin());
        if (userFromDb != null) {
            model.addAttribute("errorRegistration", "Логин уже занят!");
            model.addAttribute("news", libraryService.getAllNews());
            model.addAttribute("genres", libraryService.getAllGenres());
            model.addAttribute("popular", libraryService.getPopular());
            model.addAttribute("newBooks", libraryService.getLastTenBooks());
            model.addAttribute("user", user);
            return "index";
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(registrationService.getRoleByName("USER"));
        registrationService.saveUser(user);
        return "redirect:/";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/registrationLibrarian")
    public String addLibraries(User user,Model model){
        if(libraryService.getUserByLogin(user.getLogin())!=null){
            model.addAttribute("message",true);
            return "redirect:/lkstaff";
        }
        user.setRole(libraryService.getRoleByName("LIBRARIAN"));
        user.setPassword(encoder.encode(user.getPassword()));
        libraryService.saveUser(user);
        model.addAttribute("success","Вы зарегистрировали библиотекоря!!!");
        return "redirect:/lkstaff";
    }

}