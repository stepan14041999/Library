package ru.sstu.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sstu.library.entities.Book;
import ru.sstu.library.entities.Genre;
import ru.sstu.library.repos.BookRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreService {
    @Autowired
    private BookRepo bookRepo;


    public List<Book> getBookByGenre(Genre genre){
        List<Book> books=(List<Book>) bookRepo.findAll();
        List<Book> booksByGenre=books.stream()
                .filter(x->x.getGenres().contains(genre))
                .collect(Collectors.toList());
        return booksByGenre;
    }

    public List<Book> getAllBooks(){
        List<Book> books=(List<Book>) bookRepo.findAll();
        return books;
    }
}
