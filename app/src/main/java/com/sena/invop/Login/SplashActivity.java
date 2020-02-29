package com.sena.invop.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.sena.invop.Home.View.DashboardActivity;
import com.sena.invop.Login.Model.User;
import com.sena.invop.Login.View.LoginActivity;
import com.sena.invop.ManagerSharePreferences.PreferenceData;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceData.get(getApplicationContext());
        User user = PreferenceData.get(null).getUser();
        Log.e("TAG", "onCreate: "+ user.getName() );
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(user == null){
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }else {
                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                }
            }
        },300);

    }
}
