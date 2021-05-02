package com.tbtk.map.model;

import com.sun.istack.NotNull;
import org.locationtech.jts.geom.Geometry;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity(name = "geopoint")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "geopoint_id")
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "geom")
    private Geometry geometry;

    @ManyToOne
    @JoinTable(
            name = "user_geopoint",
            joinColumns = @JoinColumn(name = "geopoint_id"),
            inverseJoinColumns = @JoinColumn(name = "users_id"))
    private User owner;


    public Location() {
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

    public Geometry getGeometry() {
        return this.geometry;
    }

    public void setGeometry(Geometry geom) {
        this.geometry = geom;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
