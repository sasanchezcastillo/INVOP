package com.sena.invop.Home.Interfaces;

import com.sena.invop.Home.Model.Item;

public interface PresenterItem {

    void editItem(Item item);
    void deleteItem(Item item);
    void error(String error);
    void getItem(String id);
    void setItem(Item item);
    void ItemNoFound();


}
