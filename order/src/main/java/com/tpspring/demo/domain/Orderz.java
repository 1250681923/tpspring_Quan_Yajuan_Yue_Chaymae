package com.tpspring.demo.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Orderz {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> products;

    private double montant;

    public Orderz(Long id, double montant) {
        this.id = id;
        this.montant = montant;
    }

    public Orderz() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderItem> getProducts() {
        return products;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public void addProducts(OrderItem product) {
        this.products.add(product);
    }
}