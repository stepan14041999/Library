package ru.sstu.library.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sstu.library.entities.Book;

@Controller
@RequestMapping("/order")
public class OrderController {
    @GetMapping("/{book}")
    public String getPageBook(@PathVariable Book book, Model model){
        model.addAttribute("book",book);
        return "book";
    }
}
