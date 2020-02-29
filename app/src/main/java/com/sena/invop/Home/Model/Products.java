package com.sena.invop.Home.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Products implements Serializable {
    private String id;
    private List<String> itemDetails = new ArrayList<>();
    private String key_reference_item;
    private String nombre_producto;

    public Products(String id, List<String> itemDetails, String key_reference_item, String nombre_producto) {
        this.id = id;
        this.itemDetails = itemDetails;
        this.key_reference_item = key_reference_item;
        this.nombre_producto = nombre_producto;
    }

    public Products() {
    }

    public String getId() {
        return id;
    }

    public Products setId(String id) {
        this.id = id;
        return this;
    }

    public List<String> getItemDetails() {
        return itemDetails;
    }

    public Products setItemDetails(List<String> itemDetails) {
        this.itemDetails = itemDetails;
        return this;
    }

    public String getKey_reference_item() {
        return key_reference_item;
    }

    public Products setKey_reference_item(String key_reference_item) {
        this.key_reference_item = key_reference_item;
        return this;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public Products setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
        return this;
    }
}
