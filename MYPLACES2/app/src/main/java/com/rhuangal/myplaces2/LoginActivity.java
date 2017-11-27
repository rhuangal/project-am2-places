package com.rhuangal.myplaces2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rhuangal.myplaces2.data.prefs.PreferencesHelper;
import com.rhuangal.myplaces2.presenter.login.LoginContract;
import com.rhuangal.myplaces2.presenter.login.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements LoginContract.View  {

    private Button btnLogin;
    private EditText eteUsername;
    private EditText etePassword;
    private TextView btnRegister;
    private String username;
    private String password;

    private View flayLoading;

    private LoginPresenter logInPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {

        logInPresenter= new LoginPresenter();
        logInPresenter.attachView(this);

        eteUsername=(EditText)findViewById(R.id.eteUsername);
        etePassword=(EditText)findViewById(R.id.etePassword);
        btnLogin=(Button)findViewById(R.id.btnLogin);
        btnRegister=(TextView)findViewById(R.id.link_to_register);
        flayLoading=findViewById(R.id.flayLoading);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateForm()) {
                    //gotoMain();
                    logInPresenter.logIn();
                }
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logInPresenter.goToUserRegister();
            }
        });
    }

    @Override
    public void showLoading() {
        flayLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        flayLoading.setVisibility(View.GONE);
    }

    @Override
    public void gotoMain() {
        Intent intent= new Intent(this,DashboardActivity.class);
        startActivity(intent);
    }

    @Override
    public void gotoUserRegister() {
        Intent intent= new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(LoginActivity.this,
                "LogIn "+message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void saveSession(String id, String pass, String email) {
        PreferencesHelper.saveSession(this, email, pass, id);
    }

    @Override
    public boolean validateForm() {
        username= eteUsername.getText().toString();
        password= etePassword.getText().toString();

        if(username.isEmpty())
        {
            eteUsername.setError("Error campo username");
            return false;
        }
        if(password.isEmpty())
        {
            etePassword.setError("Error campo password");
            return false;
        }
        return true;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

}
