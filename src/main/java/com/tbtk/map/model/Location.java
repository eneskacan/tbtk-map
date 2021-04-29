package com.tbtk.map.model;

import com.sun.istack.NotNull;
import org.locationtech.jts.geom.Geometry;

import javax.persistence.*;

@Entity(name ="geopoint")
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
}
