package com.sena.invop.Login.Interfaces;

public interface LoginPresenter {

    void authSignIn(String email, String password);
    void error(String error);
    void authSuccesFull();

}
