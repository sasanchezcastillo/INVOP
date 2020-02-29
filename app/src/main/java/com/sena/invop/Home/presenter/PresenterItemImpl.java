package com.sena.invop.Home.presenter;

import com.sena.invop.Home.Interfaces.ItemView;
import com.sena.invop.Home.Interfaces.IteratorItem;
import com.sena.invop.Home.Interfaces.PresenterItem;
import com.sena.invop.Home.Iterator.IteratorItemImpl;
import com.sena.invop.Home.Model.Item;

public class PresenterItemImpl implements PresenterItem {

    private IteratorItem iterator;
    private ItemView view;

    public PresenterItemImpl(ItemView view) {
        this.view = view;
        iterator = new IteratorItemImpl(this);
    }

    @Override
    public void editItem(Item item) {
        iterator.editItem(item);

    }

    @Override
    public void deleteItem(Item item) {
        view.showLoad();
        iterator.deleteItem(item);

    }

    @Override
    public void error(String error) {
        view.hideLoad();
        view.error(error);

    }
    @Override
    public void getItem(String id) {
        view.showLoad();
        iterator.getItem(id);
    }

    @Override
    public void setItem(Item item) {
        view.hideLoad();
        view.setItem(item);

    }

    @Override
    public void ItemNoFound() {
        view.itemNotFound();
    }

}
