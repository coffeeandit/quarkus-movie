package com.coffeeandit.quarkus.sample;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Movie extends PanacheEntity {

    @NotBlank
    @Column(unique = true)
    public String title;

    @Column(name = "year")
    public int year;

    public String url;

    public boolean completed;

    public static List<Movie> findNotCompleted() {
        return list("completed", false);
    }

    public static List<Movie> findCompleted() {
        return list("completed", true);
    }

    public static long deleteCompleted() {
        return delete("completed", true);
    }

}
