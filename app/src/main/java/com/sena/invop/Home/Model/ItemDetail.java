package com.sena.invop.Home.Model;

public class ItemDetail {

    private String key;
    private String value;

    public ItemDetail(String key, String value) {
        this.key = key;
        this.value = value;
    }


    public ItemDetail() {
    }

    public String getKey() {
        return key;
    }

    public ItemDetail setKey(String key) {
        this.key = key;
        return this;
    }

    public String getValue() {
        return value;
    }

    public ItemDetail setValue(String value) {
        this.value = value;
        return this;
    }
}
