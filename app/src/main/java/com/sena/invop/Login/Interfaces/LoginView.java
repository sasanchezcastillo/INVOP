package com.sena.invop.Login.Interfaces;

public interface LoginView {

    void enableInputs(boolean status);
    void authSuccesFull();
    void error(String error);

}
