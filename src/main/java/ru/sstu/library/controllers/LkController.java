package ru.sstu.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sstu.library.entities.*;
import ru.sstu.library.service.AdminService;
import ru.sstu.library.service.LibrarianService;
import ru.sstu.library.service.LkService;
import sun.plugin.liveconnect.SecurityContextHelper;

import java.util.ArrayList;
import java.util.List;

@Controller
@PreAuthorize("isAuthenticated()")
public class LkController {
    @Autowired
    private LkService lkService;
    @Autowired
    private LibrarianService librarianService;
    @Autowired
    private AdminService adminService;

    @GetMapping("/lk")
    @PreAuthorize("hasAuthority('USER')")
    public String lk(Model model, @AuthenticationPrincipal User user){
        model.addAttribute("genres",lkService.getAllGenre());
        model.addAttribute("ordersComplete",lkService.getOrderComplete(user));
        model.addAttribute("ordersActive",lkService.getOrderActive(user));
        model.addAttribute("ordersBooked",lkService.getOrderBooked(user));
        model.addAttribute("favorites",lkService.getAllFavorites(user));
        return "lk";
    }

    @GetMapping("/lkstaff")
    @PreAuthorize("hasAnyAuthority('ADMIN','LIBRARIAN')")
    public String lkstaff(Model model){
        model.addAttribute("ordersActive",librarianService.getActiveOrders());
        model.addAttribute("ordersComplete",librarianService.getCompleteOrders());
        model.addAttribute("allLibraries",adminService.getAllLibraries());
        model.addAttribute("orders",librarianService.getBookedOrders());
        model.addAttribute("usersExpired",adminService.getAllUsers());
        model.addAttribute("genres",lkService.getAllGenre());
        return "lkstaff";
    }

    @PostMapping("/cancelOrder")
    public String cancelOrder(@AuthenticationPrincipal User user,@RequestParam(name = "idOrder") Integer idOrder){
        librarianService.cancelOrder(idOrder);

        return "redirect:/lkstaff";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/changePass")
    public String changePass(@AuthenticationPrincipal User user,Model model){
        model.addAttribute("userId",user.getUser_id());
        return "change";
    }
    @PostMapping("/changePass")
    public String changePass(@AuthenticationPrincipal User user,@RequestParam String password,@RequestParam String confirm,Model model){
        if(!password.equals(confirm)){
            model.addAttribute("error","Пароли не совпали!");
            return "change";
        }
        lkService.changePass(user.getUser_id(),password);
        model.addAttribute("success","Вы успешно сменили пароль!");
        return "success";
    }

    @PostMapping("/deleteFavorite")
    public String deleteFavorite(@AuthenticationPrincipal User user, @RequestParam Integer idBook){
        lkService.deleteFavorite(user,idBook);
        return "redirect:/lk";
    }

    @PostMapping("/endOrder")
    @PreAuthorize("hasAnyAuthority('ADMIN','LIBRARIAN')")
    public String endOrder(@RequestParam Integer idOrder, Model model){
        Order order=librarianService.findOrderById(idOrder);
        librarianService.changeActive(order);
        model.addAttribute("ordersActive",librarianService.getActiveOrders());
        model.addAttribute("ordersComplete",librarianService.getCompleteOrders());
        return "redirect:/lkstaff";
    }

    @PostMapping("/activateOrder")
    @PreAuthorize("hasAnyAuthority('ADMIN','LIBRARIAN')")
    public String activateOrder(@RequestParam(name = "idOrder")Integer idOrder){
        librarianService.activateOrder(idOrder);
        return "redirect:/lkstaff";
    }
    @PostMapping("/addGenre")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String addGenre(Genre genre, Model model){
        if(adminService.addNewGenre(genre)==null){
            model.addAttribute("error",true);
            model.addAttribute("ordersActive",librarianService.getActiveOrders());
            model.addAttribute("ordersComplete",librarianService.getCompleteOrders());
            model.addAttribute("allLibraries",adminService.getAllLibraries());
            model.addAttribute("genres",lkService.getAllGenre());
            return "lkstaff";
        }
        model.addAttribute("success","Вы успешно добавили жанр!!!");
        return "redirect:/lkstaff";
    }
    @PostMapping("/addAuthor")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String addAuthor(Author author, Model model){
        if(adminService.addNewAuthor(author)==null){
            model.addAttribute("error",true);
            model.addAttribute("ordersActive",librarianService.getActiveOrders());
            model.addAttribute("ordersComplete",librarianService.getCompleteOrders());
            model.addAttribute("allLibraries",adminService.getAllLibraries());
            model.addAttribute("genres",lkService.getAllGenre());
            return "lkstaff";
        }
        model.addAttribute("success","Вы успешно добавили автора!!!");
        return "redirect:/lkstaff";
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String deleteUser(@RequestParam Integer idUser,Model model){
        adminService.deleteUser(idUser);
        return "redirect:/libraries";
    }

    @GetMapping("/favorites/{book}")
    @PreAuthorize("hasAuthority('USER')")
    public String addFavorites(@PathVariable Book book, @AuthenticationPrincipal User user,Model model){
        if(librarianService.addFavorites(user,book)==null){
            return "redirect:/";
        }
        else {
            Authentication auth= SecurityContextHolder.getContext().getAuthentication();
            User user1=(User) auth.getPrincipal();

            List<GrantedAuthority> updatedAuthorities=new ArrayList<>();
            updatedAuthorities.add(new SimpleGrantedAuthority("USER"));
            Authentication newAuth=new UsernamePasswordAuthenticationToken(auth.getPrincipal(),auth.getCredentials(),updatedAuthorities);
            SecurityContextHolder.getContext().setAuthentication(newAuth);
        }
        return "redirect:/";
    }


}
