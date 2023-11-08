package com.ebenzlab.catlogservice.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

//import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@Table(name = "Products")
public class Product  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    private String description;

    private double price;
    @Transient
    private boolean inStock = true;
}
