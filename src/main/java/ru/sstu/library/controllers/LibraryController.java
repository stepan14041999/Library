package ru.sstu.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.sstu.library.service.LibraryService;

@Controller
public class LibraryController {
    @Autowired
    private LibraryService libraryService;

    @GetMapping("/")
    public String main(Model model){
        model.addAttribute("news",libraryService.getAllNews());
        model.addAttribute("genres",libraryService.getAllGenres());
        return "index";
    }
}
