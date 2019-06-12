package ru.sstu.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sstu.library.entities.Book;
import ru.sstu.library.entities.Order;
import ru.sstu.library.entities.User;
import ru.sstu.library.repos.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private StateRepo stateRepo;

    private final Integer MAX_COUNT_DAY_ORDER=30;

    public Book getBookById(Integer id){
        return bookRepo.findById(id).orElse(null);
    }

    public boolean checkDates(Order order){
        if(order.getDate_end().isBefore(order.getDate_start()))
            return true;
        return false;
    }
    public Order createOrder(Integer book_id, User user, Order order, String fio) throws IllegalAccessException{
        Book book=bookRepo.findById(book_id).get();
        Integer result=getCountOrder(order.getDate_start(),book_id);
        if(result>=book.getCountInLibrary())
            return null;
//        bookRepo.save(book);

        if((user.getRole().getRole_id().equals(roleRepo.findByName("LIBRARIAN").getRole_id()))&&(!"".equals(fio))){
            List<User> users=(List<User>) userRepo.findAll();
            String[] info=fio.split(" ");
            String sername=info[0];
            user=users.stream()
                    .filter(x->x.getBirthday().toString().equals(info[3]))
                    .filter(x->x.getFio().indexOf(sername)!=-1)
                    .findFirst()
                    .get();
            if(user.is_blocked())
                throw new IllegalAccessException();
            if(checkContainBookInOrderUser(user,book_id))
                throw new IllegalArgumentException();
        }
        order.setBook(book);
        order.setUser(user);
        order.setState(stateRepo.findByName("Booked"));
        return orderRepo.save(order);
    }

    public Boolean checkContainBookInOrderUser(User user, Integer book_id){
        List<Order> orderList=(List<Order>)orderRepo.findAll();
        orderList=orderList.stream()
                .filter(x->x.getUser().getUser_id().equals(user.getUser_id()))
                .filter(x->x.getBook().getBook_id().equals(book_id))
                .filter(x->x.getState().equals(stateRepo.findByName("Booked")))
                .collect(Collectors.toList());
        return orderList.size()>0;

    }
    //находим минимально возможную дату заказа
    public List<LocalDate> getMinDateOrder(Integer book_id) {
        Book book = bookRepo.findById(book_id).get();
        LocalDate minDate = LocalDate.now();
        DayOfWeek dv=minDate.getDayOfWeek();
        if(dv==DayOfWeek.SATURDAY) minDate=minDate.plusDays(2);
        if(dv==DayOfWeek.SUNDAY) minDate=minDate.plusDays(1);
        List<LocalDate> disabledDates=new ArrayList<>();
        for(int i=0;i<MAX_COUNT_DAY_ORDER;i++){
            if(minDate.getDayOfWeek().equals(DayOfWeek.FRIDAY)){
                LocalDate date=minDate.plusDays(3);
                if(getCountOrder(date, book_id)<book.getCountInLibrary()){
                    minDate=minDate.plusDays(1);
                    continue;
                }
                disabledDates.add(minDate);
                minDate=minDate.plusDays(1);
                continue;
            }
            if((getCountOrder(minDate, book_id)<book.getCountInLibrary())&&(minDate.getDayOfWeek()!=DayOfWeek.SATURDAY)&&(minDate.getDayOfWeek()!=DayOfWeek.SUNDAY)){
                minDate=minDate.plusDays(1);
                continue;
            }
            disabledDates.add(minDate);
            minDate=minDate.plusDays(1);
        }
        return disabledDates;
    }
    //получение количества бронирований, где преполагаемая дата находится в промежутке дат заказа, а также
    //вначале или в конце, для того, чтобы понять, есть ли свободные книги на этот день или нет
    private Integer getCountOrder(LocalDate minDate, Integer book_id){
        List<Order> orders=(List<Order>) orderRepo.findAll();
        final List<Order> orderList=new ArrayList<>();
        orders.stream()
                .filter(x -> x.getBook().getBook_id() == book_id)
                .filter(x->x.getState().equals(stateRepo.findByName("Booked")))
                .peek(x->{
                    if(x.getDate_start().compareTo(minDate)==0||x.getDate_end().compareTo(minDate)==0||x.getDate_start().compareTo(minDate.plusDays(1))==0){
                        orderList.add(x);
                    }
                })
                .filter(x -> x.getDate_start().isBefore(minDate))
                .filter(x->x.getDate_end().isAfter(minDate))
                .peek(x->orderList.add(x))
                .collect(Collectors.toList());
        Integer countOverdue=(int) orders.stream()
                .filter(x -> x.getBook().getBook_id() == book_id)
                .filter(x->x.getState().equals(stateRepo.findByName("Booked")))
                .filter(x->x.getDate_end().isBefore(LocalDate.now()))
                .count();
        return orderList.size()+countOverdue;
    }

    public LocalDate getMaxDateOrder(Integer book_id,LocalDate minDate){
        Book book=bookRepo.findById(book_id).get();
        for(int i=0;i<MAX_COUNT_DAY_ORDER;i++){
//            Integer a=getCountOrder(minDate,book.getBook_id());
//            Integer b=book.getCountInLibrary();
//            boolean c=book.getCountInLibrary().equals(getCountOrder(minDate,book.getBook_id()));
            if(book.getCountInLibrary()<=getCountOrder(minDate,book.getBook_id())){
                return minDate;
            }
            minDate=minDate.plusDays(1);
        }
        return minDate;

    }

    public List<User> findUser(String fio){
        List<User> users=(List<User>)userRepo.findByFioIsContainingIgnoreCase(fio);
        users=users.stream()
                .filter(x->x.getRole().equals(roleRepo.findByName("USER")))
                .collect(Collectors.toList());
        return users;
    }
}
