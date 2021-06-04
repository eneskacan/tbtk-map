package com.tbtk.map.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;


@Entity(name = "users")
public class User {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", unique = true)
    private String name;

    @NotNull
    @Column(name = "pass")
    private String pass;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Location> locations;

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}
