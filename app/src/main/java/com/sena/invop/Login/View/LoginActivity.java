package com.sena.invop.Login.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sena.invop.Home.View.DashboardActivity;
import com.sena.invop.Login.Interfaces.LoginPresenter;
import com.sena.invop.Login.Interfaces.LoginView;
import com.sena.invop.Login.Presenter.LoginPresenterImpl;
import com.sena.invop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView, TextWatcher {

    @BindView(R.id.editext_name_user)
    EditText name_user;
    @BindView(R.id.editext_password)
    EditText password;
    @BindView(R.id.button_login)
    Button button_login;
    @BindView(R.id.progress_login)
    ProgressBar progressBar;
    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        button_login.setEnabled(false);
        presenter = new LoginPresenterImpl(this);
        name_user.addTextChangedListener(this);
        password.addTextChangedListener(this);

       /* button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authSignIn();
            }
        });*/
    }

    @Override
    public void enableInputs(boolean status) {
        changeStatusButton(status);
        name_user.setEnabled(status);
        password.setEnabled(status);
        if (status){
            progressBar.setVisibility(View.GONE);
        }else {
            progressBar.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void authSuccesFull() {
        Intent intent = new Intent(getApplicationContext(),DashboardActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(),"Login ok ",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void error(String error) {
        Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT).show();
    }


    @OnClick(R.id.button_login)
    public  void authSignIn(){
        presenter.authSignIn(name_user.getText().toString(),password.getText().toString());
    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }
    @Override
    public void afterTextChanged(Editable editable) {
        if (validateEditext()){
            changeStatusButton(true);

        }else {
            changeStatusButton(false);

        }
    }

    void changeStatusButton(boolean status){
        button_login.setEnabled(true);
        if (status){
            button_login.setTextColor(getResources().getColor(R.color.white));
        }else{
            button_login.setTextColor(getResources().getColor(R.color.text_color_ebnable));
        }
    }

    public boolean validateEditext(){
        if(name_user.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
            return  false;
        }
        return true;
    }
}
