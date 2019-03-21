package ru.sstu.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sstu.library.entities.User;
import ru.sstu.library.service.LibraryService;
import ru.sstu.library.service.RegistrationService;

@Controller
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping("/login")
    public String getLoginPage(Model model,@RequestParam(required = false) String error){
        model.addAttribute("error",error);
        return "login";
    }

    @GetMapping("/registration")
    public String getRegistrationPage(Model model){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model){
        User userFromDb=registrationService.getUserByLogin(user.getLogin());
        if(userFromDb!=null){
            model.addAttribute("message","true");
            model.addAttribute("user",user);
            return "registration";
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(registrationService.getRoleByName("USER"));
        registrationService.saveUser(user);
        return "success";
    }
}
