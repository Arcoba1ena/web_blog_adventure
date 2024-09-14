package com.bloger.web.blog.models;

import lombok.Data;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;

@Data
@Entity
@NoArgsConstructor
public class AdventureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String city;
    private String price;
    private String culturePlace;
    private String visitPlace;
    private String rating;

    public AdventureEntity(String city, String price, String culturePlace, String visitPlace, String rating) {
        this.city = city;
        this.price = price;
        this.culturePlace = culturePlace;
        this.visitPlace = visitPlace;
        this.rating = rating;
    }
}
