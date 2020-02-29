package com.sena.invop.Login.Model;

public class User {
    private String create_at;
    private String type;
    private String name;
    private String last_name;
    private String email;
    private String uuid;
    private String reference_item;
    private boolean isEditor = false;

    public User(String create_at, String type, String name, String last_name, String email, String uuid, String reference_item, boolean isEditor) {
        this.create_at = create_at;
        this.type = type;
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.uuid = uuid;
        this.reference_item = reference_item;
        this.isEditor = isEditor;
    }

    public User() {
    }

    public String getCreate_at() {
        return create_at;
    }

    public User setCreate_at(String create_at) {
        this.create_at = create_at;
        return this;
    }

    public String getType() {
        return type;
    }

    public User setType(String type) {
        this.type = type;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getLast_name() {
        return last_name;
    }

    public User setLast_name(String last_name) {
        this.last_name = last_name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public User setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getReference_item() {
        return reference_item;
    }

    public User setReference_item(String reference_item) {
        this.reference_item = reference_item;
        return this;
    }

    public boolean isEditor() {
        return isEditor;
    }

    public User setEditor(boolean editor) {
        isEditor = editor;
        return this;
    }
}
