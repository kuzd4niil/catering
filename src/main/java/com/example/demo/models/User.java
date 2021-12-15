package com.example.demo.models;

import com.example.demo.security.ApplicationUserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Serializable {

    // CONSTRUCTORS
    public User() {

    }

    // FIELDS
    @Id
    @SequenceGenerator(
            name = "user_id_sequence",
            sequenceName = "user_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_id_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "username",
            unique = true
    )
    private String username;

    @Column(
            name = "email",
            nullable = false,
            unique = true)
    private String email;

    @Column(
            name = "password",
            nullable = false
    )
    private String password;

    @Column(
            name = "user_role"
            ,nullable = false
    )
    private ApplicationUserRole userRole;

    @OneToMany(
            mappedBy = "owner",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
//    @JsonIgnore
    private List<Catering> caterings = new ArrayList<>();

    // GETTER AND SETTER
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public ApplicationUserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(ApplicationUserRole userRole) {
        this.userRole = userRole;
    }

    public List<Catering> getCaterings() {
        return caterings;
    }

    public void setCaterings(List<Catering> caterings) {
        this.caterings = caterings;
    }
}
