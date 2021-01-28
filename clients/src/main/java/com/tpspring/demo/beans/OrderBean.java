package com.tpspring.demo.beans;

import java.util.List;

public class OrderBean {
    private Long id;

    private List<CartItemBean> products;

    private double montant;

    public OrderBean(Long id, double montant) {
        this.id = id;
        this.montant = montant;
    }

    public OrderBean() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CartItemBean> getProducts() {
        return products;
    }

    public void addProduct(CartItemBean product) {
        this.products.add(product);
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }
}
