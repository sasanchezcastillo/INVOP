package com.sena.invop.Home.Interfaces;

import com.sena.invop.Home.Model.Item;
import com.sena.invop.Home.Model.Products;

import java.util.List;

public interface Dashboard {
    void setData(List<Item> data);
    void setMessage(String message);
    void showProgress(boolean status);
    void setProducts(List<Products> products);
    void productsNotFount();

}
