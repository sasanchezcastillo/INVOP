package com.sena.invop.Login.Iterator;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sena.invop.Login.Interfaces.LoginIterator;
import com.sena.invop.Login.Interfaces.LoginPresenter;
import com.sena.invop.Login.Model.User;
import com.sena.invop.ManagerSharePreferences.PreferenceData;


public class IteratorLoginImpl implements LoginIterator {


    private static final String TAG = "IteratorLoginImpl";
    private LoginPresenter presenter;
    public IteratorLoginImpl(LoginPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void authSigIn(String email, String password) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    assert user != null;
                    getInfoUser(user.getUid(),password);
                }else{
                    presenter.error("User o Password Incorrect");
                }
            }
        });
    }

    void getInfoUser(String id,String pass){
        Log.e(TAG, "getInfoUser: id =++ " + id );
        FirebaseFirestore.getInstance().collection("users")
                .document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    User user = documentSnapshot.toObject(User.class);
                    if (user != null){
                        Log.e(TAG, "onSuccess: " + user.getName() );
                        Log.e(TAG, "onSuccess: " + user.getReference_item() );
                        PreferenceData.get(null).setUer(user,pass);
                        presenter.authSuccesFull();
                    }
                }else{
                    presenter.error("Contacte al administrador");
                }

            }
        });

    }
}
