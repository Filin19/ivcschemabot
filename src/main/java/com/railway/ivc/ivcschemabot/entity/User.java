package com.railway.ivc.ivcschemabot.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * Class entity to save user in database.
 * <p>
 * @author Viktor Zaitsev.
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_number")
    private String userNumber;

    public User(String userName, String userNumber) {
        this.userName = userName;
        this.userNumber = userNumber;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && userName.equals(user.userName) && userNumber.equals(user.userNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, userNumber);
    }
}
