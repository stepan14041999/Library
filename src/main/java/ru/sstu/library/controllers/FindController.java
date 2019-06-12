package ru.sstu.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.sstu.library.entities.Author;
import ru.sstu.library.entities.Book;
import ru.sstu.library.service.FindService;
import ru.sstu.library.service.LibraryService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class FindController {
    @Autowired
    private FindService findService;
    @Autowired
    private LibraryService libraryService;

    @GetMapping("/find")
    @ResponseBody
    public String findByInfo(@RequestParam String info){
        List<Author> lstAuthors=findService.findByAuthorFio(info);
        List<Book> lstBookIsbn=findService.findByIsbn(info);
        List<Book> lstBookName=findService.findByName(info);
        List<String> result=new ArrayList<>();
        if(lstAuthors.size()>0){
            result=lstAuthors.stream()
                    .map(x->x.getFio())
                    .collect(Collectors.toList());
            return result.toString();
        }
        else if(lstBookIsbn.size()>0){
            result=lstBookIsbn.stream()
                    .map(x->x.getName())
                    .collect(Collectors.toList());
            return result.toString();
        }
        else if(lstBookName.size()>0){
            result=lstBookName.stream()
                    .map(x->x.getName())
                    .collect(Collectors.toList());
            return result.toString();
        }
        return result.toString();
    }

    @PostMapping("/find")
    public String findBooks(@RequestParam String info, Model model){
        List<Book> resBook=new ArrayList<>();
        resBook.addAll(findService.findByIsbn(info));
        resBook.addAll(findService.findByName(info));
        resBook.addAll(findService.findByAuthor(info));
        model.addAttribute("books",resBook);
        model.addAttribute("genres",libraryService.getAllGenres());
        return "genres";
    }
}
