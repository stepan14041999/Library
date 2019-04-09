package ru.sstu.library.controllers;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sstu.library.entities.Genre;
import ru.sstu.library.service.GenreService;
import ru.sstu.library.service.LibraryService;

@Controller
@RequestMapping("/genre")
public class GenreController {
    @Autowired
    private GenreService genreService;
    @Autowired
    private LibraryService libraryService;

    @GetMapping("/")
    public String getAll(Model model){
        model.addAttribute("books",genreService.getAllBooks());
        model.addAttribute("genres",libraryService.getAllGenres());
        return "genres";
    }

    @GetMapping("/{genre}")
    public String booksByGenre(@PathVariable Genre genre, Model model){
        model.addAttribute("books",genreService.getBookByGenre(genre));
        model.addAttribute("genres",libraryService.getAllGenres());
        return "genres";
    }
}
