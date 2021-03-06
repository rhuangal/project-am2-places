package com.rhuangal.myplaces2.presenter.login;

/**
 * Created by rober on 02-Nov-17.
 */

public class LoginContract {

    public interface View{
        void showLoading();
        void hideLoading();
        void gotoMain();
        void gotoUserRegister();
        void showMessage(String message);

        void saveSession(String id, String pass, String email);

        boolean validateForm();

        String getUsername();
        String getPassword();
    }

}
