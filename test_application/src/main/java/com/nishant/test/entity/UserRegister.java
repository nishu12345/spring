package com.nishant.test.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user_register")
public class UserRegister implements Serializable {

    private static final long serialVersionUID = 2315791319501153513L;

    private Long id;

    private String username;

    private String email;

    private String password;

    private Boolean isAccountActive = false;

    private Date addedOn = new Date();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "username", nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "email", nullable = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "is_account_active", nullable = false)
    public Boolean getAccountActive() {
        return isAccountActive;
    }

    public void setAccountActive(Boolean accountActive) {
        isAccountActive = accountActive;
    }

    @Column(name = "added_on", nullable = false)
    public Date getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(Date addedOn) {
        this.addedOn = addedOn;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserRegister [");
        sb.append("id=").append(id);
        sb.append(", username= ").append(username);
        sb.append(", email= ").append(email);
        sb.append(", password= ").append(password);
        sb.append(", isAccountActive=").append(isAccountActive);
        sb.append(", addedOn=").append(addedOn);
        sb.append("]");
        return sb.toString();
    }
}
