package com.sena.invop.Home.Iterator;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.sena.invop.Home.Interfaces.IteratorDashboard;
import com.sena.invop.Home.Interfaces.PresenterDashboard;
import com.sena.invop.Home.Model.Item;
import com.sena.invop.Home.Model.Products;
import com.sena.invop.Login.Model.User;
import com.sena.invop.ManagerSharePreferences.PreferenceData;

import java.util.ArrayList;
import java.util.List;

public class IteratorDashboardImpl implements IteratorDashboard {

   private PresenterDashboard presenter;
    private static final String TAG = "IteratorDashboardImpl";

    private  User user;
    public IteratorDashboardImpl(PresenterDashboard presenter) {
        this.presenter = presenter;
        user = PreferenceData.get(null).getUser();

    }

    @Override
    public void getData() {
        FirebaseFirestore.getInstance().collection("inventary_products")
                .whereEqualTo("reference_items",user.getReference_item()).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()){
                            List<Item> items = new ArrayList<>();
                            for (DocumentSnapshot q:queryDocumentSnapshots) {
                                items.add(q.toObject(Item.class));
                            }
                            presenter.setData(items);
                        }else{
                            presenter.setMessage("0");
                        }

                    }
                });

    }

    @Override
    public void getProducts() {
        FirebaseFirestore.getInstance().collection("products")
                .whereEqualTo("key_reference_item",user.getReference_item())
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()){
                    List<Products> products = new ArrayList<>();
                    for (DocumentSnapshot d: queryDocumentSnapshots) {
                        products.add(d.toObject(Products.class));
                    }
                    presenter.setProducts(products);
                }else{
                    presenter.productsNotFount();
                }
            }
        });

    }


}
