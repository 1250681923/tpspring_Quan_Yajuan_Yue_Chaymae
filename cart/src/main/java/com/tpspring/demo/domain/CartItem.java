package com.tpspring.demo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CartItem {

    @Id
    @GeneratedValue
    private Long id;

    private Long productId;

    private Integer quantity;

    private double price;

    public CartItem() {
    }

    public CartItem(Long id, Long productId, Integer quantity, double price) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Cart Item :"+id+":"+productId+":"+quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
