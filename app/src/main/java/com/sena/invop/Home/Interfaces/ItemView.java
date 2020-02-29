package com.sena.invop.Home.Interfaces;

import com.sena.invop.Home.Model.Item;

public interface ItemView {
    void showLoad();
    void hideLoad();
    void error(String error);
    void setItem(Item item);
    void itemNotFound();


}
