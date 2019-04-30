package ru.sstu.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sstu.library.entities.Book;
import ru.sstu.library.entities.Order;
import ru.sstu.library.entities.User;
import ru.sstu.library.service.LibraryService;
import ru.sstu.library.service.OrderService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private LibraryService libraryService;

    @GetMapping("/{book}")
    public String getPageBook(@PathVariable Book book, Model model) {
        model.addAttribute("genres",libraryService.getAllGenres());
        model.addAttribute("book", book);
        return "books";
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping()
    public String createOrd(@RequestParam Integer book_id,
                            @AuthenticationPrincipal User user,
                            @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate date_start,
                            @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate date_end,
                            Model model,
                            @RequestParam(required = false,name = "user") String userFio){
        Book book=orderService.getBookById(book_id);
        Order order=new Order();
        order.setDate_start(date_start);
        order.setDate_end(date_end);
        model.addAttribute("book",book);
        if(orderService.checkDates(order)){
            model.addAttribute("error","Конечная дата не может быть раньше начальной!");
            changeModel(book,model);
            return "books";
        }
        if(orderService.checkContainBookInOrderUser(user,book_id)) {
            model.addAttribute("error", "У вас уже есть эта книга!");
            changeModel(book,model);
            return "books";
        }
        try {
            order=orderService.createOrder(book_id,user,order,userFio);
        }
        catch (IllegalAccessException e){
            model.addAttribute("error", "Данный пользователь заблокирован!");
            changeModel(book,model);
            return "books";
        }
        catch (IllegalArgumentException e){
            model.addAttribute("error", "У пользователя уже есть эта книга!");
            changeModel(book,model);
            return "books";
        }

        if(order==null){
            model.addAttribute("error", "Книгу уже заказали на эту дату!");
            changeModel(book,model);
            return "books";
        }
        model.addAttribute("order",order);
        model.addAttribute("startDate",order.getDate_start().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        model.addAttribute("endDate",order.getDate_end().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        return "infoOrder";
    }
    @GetMapping("/mindata")
    @ResponseBody
    public String[] getMinDate(@RequestParam Integer book_id){
        List<LocalDate> dates=orderService.getMinDateOrder(book_id);
        String[] disabledDates=new String[dates.size()];
        for(int i=0;i<dates.size();i++){
            disabledDates[i]=dates.get(i).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        }
        return disabledDates;
    }
    @GetMapping("/maxdata")
    @ResponseBody
    public String getMaxDate(@RequestParam Integer book_id, @RequestParam String minDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.parse(minDate, formatter);
        LocalDate result=orderService.getMaxDateOrder(book_id,date);
        if(result!=null){
            return result.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        }
        return null;
    }
    @GetMapping("/finduser")
    @ResponseBody
    public String findUser(@RequestParam String fio){
        List<User> users=orderService.findUser(fio);
        List<String> names=users.stream()
                .map(x->{
                    String[] strs=x.getFio().split(" ");
                    String result=strs[0]+" "+strs[1].substring(0,1)+"."+" "+strs[2].substring(0,1)+"."+" "+x.getBirthday();
                    return result;
                })
                .collect(Collectors.toList());
        return names.toString();
    }
    private Model changeModel(Book book, Model model){
        model.addAttribute("textStartDate","Начальная дата:");
        return model;
    }
}
