package com.sena.invop.Login.Presenter;

import com.sena.invop.Login.Interfaces.LoginIterator;
import com.sena.invop.Login.Interfaces.LoginPresenter;
import com.sena.invop.Login.Interfaces.LoginView;
import com.sena.invop.Login.Iterator.IteratorLoginImpl;

public class LoginPresenterImpl implements LoginPresenter {
    private LoginIterator iterator;
    private LoginView view;

    public LoginPresenterImpl(LoginView view) {
        this.view = view;
        this.iterator = new IteratorLoginImpl(this);
    }

    @Override
    public void authSignIn(String email, String password) {
        view.enableInputs(false);
        iterator.authSigIn(email,password);
    }

    @Override
    public void error(String error) {
        view.enableInputs(true);
        view.error(error);
    }

    @Override
    public void authSuccesFull() {
        view.enableInputs(true);
        view.authSuccesFull();

    }
}
