package com.oguzhan.rig.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class CustomerDto {

    private Long id;
    @Email
    private String email;
    @NotNull
    @Size(min=8, message="password should have at least 2 characters")
    private String password;
    @NotNull
    @Size(min=2, message="name should have at least 2 characters")
    private String name;
    @NotNull
    @Size(min=2, message="surname should have at least 2 characters")
    private String surname;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
