package com.rhuangal.myplaces2.presenter.register;

/**
 * Created by rober on 04-Nov-17.
 */

public class RegisterContract {

    public interface View{
        void showLoading();
        void hideLoading();
        void gotoLogin();
        void showMessage(String message);

        void formReset();

        boolean validateForm();

        String getEmail();
        String getPassword();
        String getNombre();
    }
}
