package ru.kotov.nikita.library.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

@Entity
@Table(name="USER_LIBRARY")
@Data
public class User implements UserDetails {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy =GenerationType.SEQUENCE)
    private Integer user_id;
    private String login;
    private String password;
    private String fio;
    private String email;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthday;
    private String phone_number;
    private String address;
    @ManyToOne(optional = false)
    @JoinColumn(name = "ROLE_ID")
    private Role role;
    private boolean is_blocked;
    @OneToMany(mappedBy = "user")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<Order> orders;
    @ManyToMany()
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "FAVORITES",joinColumns = @JoinColumn(name = "USER_ID"),inverseJoinColumns = @JoinColumn(name = "BOOK_ID"))
    private Collection<Book> booksFavorites;


    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", fio='" + fio + '\'' +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                ", phone_number='" + phone_number + '\'' +
                ", address='" + address + '\'' +
                ", is_blocked=" + is_blocked +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(getRole());
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !is_blocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
