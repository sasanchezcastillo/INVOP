package com.sena.invop.Home.presenter;

import com.sena.invop.Home.Interfaces.Dashboard;
import com.sena.invop.Home.Interfaces.IteratorDashboard;
import com.sena.invop.Home.Interfaces.PresenterDashboard;
import com.sena.invop.Home.Iterator.IteratorDashboardImpl;
import com.sena.invop.Home.Model.Item;
import com.sena.invop.Home.Model.Products;

import java.util.List;

public class PresenterDashboardImpl implements PresenterDashboard {

    private Dashboard view;
    private IteratorDashboard iterator;

    public PresenterDashboardImpl(Dashboard view) {
        this.view = view;
        iterator = new IteratorDashboardImpl(this);
    }

    @Override
    public void getData() {
        view.showProgress(true);
        iterator.getData();

    }

    @Override
    public void getProducts() {
        iterator.getProducts();

    }

    @Override
    public void setData(List<Item> data) {
        view.showProgress(false);
        view.setData(data);
    }

    @Override
    public void setMessage(String message) {
        view.showProgress(false);
        view.setMessage(message);


    }

    @Override
    public void setProducts(List<Products> products) {

        view.setProducts(products);
    }

    @Override
    public void productsNotFount() {
        view.productsNotFount();
    }
}
