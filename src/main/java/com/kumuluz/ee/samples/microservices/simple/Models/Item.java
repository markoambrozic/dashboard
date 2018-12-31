package com.kumuluz.ee.samples.microservices.simple.Models;

import org.json.JSONObject;

public class Item {

    private int productId;

    private int qty;

    private String name;

    private double price;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public JSONObject getItemJSON() {
        JSONObject itemJSON = new JSONObject();

        itemJSON.put("productId", this.productId);
        itemJSON.put("name", this.name);
        itemJSON.put("price", this.price);
        itemJSON.put("qty", this.qty);

        return itemJSON;
    }
}
