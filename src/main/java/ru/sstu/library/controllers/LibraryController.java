package ru.sstu.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sstu.library.service.LibraryService;

@Controller
public class LibraryController {
    @Autowired
    private LibraryService libraryService;

    @GetMapping("/")
    public String main(@RequestParam(name="msg", required = false,defaultValue = "no") String msg, Model model){
        if(msg.equals("failure")){
            model.addAttribute("errorLogin","Неправильный логин или пароль!");
        }
        else if(msg.equals("denied")){
            model.addAttribute("errorLogin","Вы заблокированы!");
        }
        model.addAttribute("news",libraryService.getAllNews());
        model.addAttribute("genres",libraryService.getAllGenres());
        model.addAttribute("popular",libraryService.getPopular());
        model.addAttribute("newBooks",libraryService.getLastTenBooks());
        model.addAttribute("message",false);
        return "index";

    }

    @GetMapping("/popular")
    public String popular(Model model){
        model.addAttribute("books",libraryService.getPopular());
        return "genres";
    }

    @GetMapping("/new")
    public String newBooks(Model model){
        model.addAttribute("books",libraryService.getLastTenBooks());
        return "genres";
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }

//
//
//    @GetMapping("/test")
//    public String testImage(Model model){
//        model.addAttribute("books",libraryService.getAllBooks());
//        return "test";
//    }
    @GetMapping("/InfoOrder")
    public String testImage1(Model model){

        return "InfoOrder";
    }
}