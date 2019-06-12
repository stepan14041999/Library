package ru.sstu.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sstu.library.entities.Author;
import ru.sstu.library.entities.Book;
import ru.sstu.library.repos.AuthorRepo;
import ru.sstu.library.repos.BookRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindService {
    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private AuthorRepo authorRepo;

    public List<Author> findByAuthorFio(String fio){
        return authorRepo.findByFioIsContainingIgnoreCase(fio);
    }

    public List<Book> findByName(String name){
        return bookRepo.findByNameContainingIgnoreCase(name);
    }

    public List<Book> findByIsbn(String isbn){
        return bookRepo.findByIsbnContainingIgnoreCase(isbn);
    }

    public List<Book> findByAuthor(String fio){
        List<Author> authors=authorRepo.findByFioIsContainingIgnoreCase(fio);
        List<Book> books=new ArrayList<>();
        if(authors.size()>0) {
            books = (List<Book>) bookRepo.findAll();
            books = books.stream()
                    .filter(x -> x.getAuthors().containsAll(authors)||x.getForeignAuthors().containsAll(authors))
                    .collect(Collectors.toList());
            return books;
        }
        return books;
    }
}
