package com.kumuluz.ee.samples.microservices.simple.Models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "carts")
@NamedQuery(name = "Cart.findAll", query = "SELECT c FROM Cart c")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    private String cartJSON;

    public String getCartJSON() {
        return cartJSON;
    }

    public void setCartJSON(String cartJSON) {
        this.cartJSON = cartJSON;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
