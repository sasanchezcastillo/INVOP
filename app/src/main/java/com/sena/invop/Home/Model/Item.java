package com.sena.invop.Home.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Item {

    private String id;
    private String create;
    private String last_update;
    private String img_item;
    private String name;
    private String description;
    private Map<String ,String > itemDetails = new HashMap<>();


    public Item(String id, String create, String last_update, String img_item, String name, String description, Map<String, String> itemDetails) {
        this.id = id;
        this.create = create;
        this.last_update = last_update;
        this.img_item = img_item;
        this.name = name;
        this.description = description;
        this.itemDetails = itemDetails;
    }

    public Item() {
    }
    public String getId() {
        return id;
    }
    public Item setId(String id) {
        this.id = id;
        return this;
    }
    public String getCreate() {
        return create;
    }
    public Item setCreate(String create) {
        this.create = create;
        return this;
    }
    public String getLast_update() {
        return last_update;
    }
    public Item setLast_update(String last_update) {
        this.last_update = last_update;
        return this;
    }
    public String getImg_item() {
        return img_item;
    }
    public Item setImg_item(String img_item) {
        this.img_item = img_item;
        return this;
    }

    public Map<String, String> getItemDetails() {
        return itemDetails;
    }

    public Item setItemDetails(Map<String, String> itemDetails) {
        this.itemDetails = itemDetails;
        return this;
    }

    public String getName() {
        return name;
    }

    public Item setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        for (Map.Entry p: itemDetails.entrySet()) {
            description = description.concat(" -<b>"+p.getKey()+"</b> : "+p.getValue()+"\n");
        }
        return description;
    }
    public Item setDescription(String description) {
        this.description = description;
        return this;
    }
}
