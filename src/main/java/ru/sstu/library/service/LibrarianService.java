package ru.sstu.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sstu.library.entities.News;
import ru.sstu.library.entities.Order;
import ru.sstu.library.repos.NewsRepo;
import ru.sstu.library.repos.OrderRepo;
import ru.sstu.library.repos.StateRepo;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibrarianService {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private NewsRepo newsRepo;
    @Autowired
    private StateRepo stateRepo;

    public Order findOrderById(Integer idOrder){
        return orderRepo.findById(idOrder).get();
    }

    public Order changeActive(Order order){
        order.setState(stateRepo.findByName("Complete"));
        return orderRepo.save(order);
    }

    public List<Order> getActiveOrders(){
        List<Order> orderList= (List<Order>) orderRepo.findAll();
        orderList=orderList.stream()
                .filter(x->x.getState().equals(stateRepo.findByName("Active")))
                .sorted((x,y)->-x.getOrder_id()-y.getOrder_id())
                .collect(Collectors.toList());
        return orderList;
    }
    public List<Order> getCompleteOrders(){
        List<Order> orderList= (List<Order>) orderRepo.findAll();
        orderList=orderList.stream()
                .filter(x->x.getState().equals(stateRepo.findByName("Complete")))
                .collect(Collectors.toList());
        return orderList;
    }

    public News addNews(News news){
        news.setDate_publish(LocalDate.now());
        return newsRepo.save(news);
    }

    public List<Order> getBookedOrders(){
        List<Order> orderList=(List<Order>)orderRepo.findAll();
        orderList=orderList.stream()
                .filter(x->x.getState().equals(stateRepo.findByName("Booked")))
                .sorted((x,y)->x.getDate_start().compareTo(y.getDate_start()))
                .collect(Collectors.toList());
        return orderList;
    }

    public void cancelOrder(Integer idOrder){
        orderRepo.deleteById(idOrder);
    }

    public Order activateOrder(Integer idOrder){
        Order order=orderRepo.findById(idOrder).get();
        order.setState(stateRepo.findByName("Active"));
        return orderRepo.save(order);
    }
}
