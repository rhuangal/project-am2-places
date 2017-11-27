package com.rhuangal.myplaces2.presenter.login;


import android.util.Log;
import android.widget.Toast;

import com.rhuangal.myplaces2.LoginActivity;
import com.rhuangal.myplaces2.data.network.ApiClient;
import com.rhuangal.myplaces2.data.network.entity.Login;
import com.rhuangal.myplaces2.data.network.entity.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rober on 02-Nov-17.
 */
public class LoginPresenter {

    private static final String TAG = "LoginPresenter";

    private LoginContract.View view;


    public void logIn(){
        if(view.validateForm()){
            authentication();
        }
    }

    public void goToUserRegister(){
        this.view.gotoUserRegister();
    }

    private void authentication() {
        String username = view.getUsername();
        String password = view.getPassword();
        view.showLoading();
        final Login login= new Login();
        login.setLogin(username);
        login.setPassword(password);
        Log.v("CONSOLE", "ENTRO EN AUTENTICAR");

        Call<LoginResponse> call= ApiClient.getMyApiClient().login(login);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                view.hideLoading();
                if(response!=null){
                    LoginResponse logInResponse=null;

                    if(response.isSuccessful()){
                        logInResponse=response.body();
                        if(logInResponse!=null){
                            view.saveSession(logInResponse.getObjectId(), login.getPassword(), login.getLogin());
                            view.showMessage("login "+logInResponse.getName());
                            view.gotoMain();
                        }
                    }else{
                        Log.v("CONSOLE", "error "+logInResponse);
                    }
                }else{
                    view.showMessage("Ocurrió un error");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                view.showMessage("error "+t.getMessage());
                view.hideLoading();
            }
        });


    }

    public void attachView(LoginContract.View view){
        this.view=view;
    }
    public void dettachView(){
        this.view=null;
    }
}
