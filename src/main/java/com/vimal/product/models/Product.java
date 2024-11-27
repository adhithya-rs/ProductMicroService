package com.vimal.product.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product extends BaseModel {
    @Column(unique = true, nullable = false)
    private String title;
    private String description;
    private double price;
    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Category category;
    private String image;
//Test

}
