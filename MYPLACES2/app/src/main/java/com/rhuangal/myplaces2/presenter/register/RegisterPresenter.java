package com.rhuangal.myplaces2.presenter.register;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.rhuangal.myplaces2.data.network.ApiClient;
import com.rhuangal.myplaces2.data.network.entity.User;
import com.rhuangal.myplaces2.data.network.entity.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rober on 04-Nov-17.
 */
public class RegisterPresenter {

    private static final String TAG = "RegisterPresenter";

    private RegisterContract.View view;


    public void goToLogin(){
        this.view.gotoLogin();
    }

    public void signUp() {

        view.showLoading();

        String email= view.getEmail();
        String password= view.getPassword();
        String nombre= view.getNombre();

        Call<UserResponse> call= ApiClient.getMyApiClient().register(new User(nombre, email, password));
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                view.hideLoading();
                if(response!=null){
                    UserResponse userResponse =null;
                    if(response.isSuccessful()){
                        userResponse=response.body();
                        if(userResponse.getObjectId() != null){
                            view.showMessage("Se registro correctamente");
                            view.formReset();

                        }
                    }else{
                        //Error error = response.errorBody();
                        Log.v("CONSOLE", "error "+userResponse);
                    }
                }else{
                    view.showMessage("Ocurrió un error");
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                view.showMessage("error "+t.getMessage());
                view.hideLoading();
            }
        });



    }

    public void attachView(RegisterContract.View view){
        this.view=view;
    }
    public void dettachView(){
        this.view=null;
    }

}
