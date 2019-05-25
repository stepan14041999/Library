package ru.sstu.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sstu.library.entities.User;
import ru.sstu.library.service.LibrarianService;
import ru.sstu.library.service.LkService;

@Controller
@PreAuthorize("isAuthenticated()")
public class LkController {
    @Autowired
    private LkService lkService;
    @Autowired
    private LibrarianService librarianService;

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
        return "lkstaff";
    }

    @PostMapping("/cancelOrder")
    public String cancelOrder(@AuthenticationPrincipal User user,@RequestParam(name = "idOrder") Integer idOrder){
        librarianService.cancelOrder(idOrder);
        if(user.getRole().getName().equals("LIBRARIAN"))
            return "redirect:/listOrders";
        return "redirect:/lk";
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

}
