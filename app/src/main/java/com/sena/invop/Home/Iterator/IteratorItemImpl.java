package com.sena.invop.Home.Iterator;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.sena.invop.Home.Interfaces.IteratorItem;
import com.sena.invop.Home.Interfaces.PresenterItem;
import com.sena.invop.Home.Model.Item;
public class IteratorItemImpl implements IteratorItem {
    private PresenterItem presenter;
    public IteratorItemImpl(PresenterItem presenter) {
        this.presenter = presenter;
    }

    @Override
    public void editItem(Item item) {
        FirebaseFirestore.getInstance().collection("products")
                .document(item.getId())
                .set(item).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                presenter.error("Actualizado...");
            }
        });
    }

    @Override
    public void deleteItem(Item item) {
        FirebaseFirestore.getInstance().collection("products").document(item.getId())
                .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                presenter.error("Eliminado correctamente");
            }
        });

    }
    @Override
    public void getItem(String id) {
        FirebaseFirestore.getInstance().collection("products").whereEqualTo("id",id)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()){

                }else{
                    presenter.ItemNoFound();
                }
            }
        })


                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    Item item = task.getResult().getDocuments().get(0).toObject(Item.class);
                    presenter.setItem(item);
                }else{
                    presenter.error("no existe");
                }
            }
        });
    }
}
