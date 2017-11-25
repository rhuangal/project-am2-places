package com.rhuangal.myplaces2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rhuangal.myplaces2.presenter.register.RegisterContract;
import com.rhuangal.myplaces2.presenter.register.RegisterPresenter;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View {

    private static final String TAG = "RegisterActivity";

    private EditText eteRegEmail;
    private EditText eteRegPassword;
    private EditText etePasswordConfirm;
    private EditText eteRegNombre;

    private Button btnCreateAccount;

    private View flayLoading;

    private RegisterPresenter regPresenter;

    private String nombre;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {

        regPresenter= new RegisterPresenter();
        regPresenter.attachView(this);

        eteRegEmail     =   (EditText)findViewById(R.id.eteRegEmail);
        eteRegPassword  =   (EditText)findViewById(R.id.eteRegPassword);
        etePasswordConfirm = (EditText)findViewById(R.id.etePasswordConfirm);
        eteRegNombre = (EditText)findViewById(R.id.eteRegNombre);
        btnCreateAccount=   (Button)findViewById(R.id.btnCreateAccount);
        flayLoading=findViewById(R.id.flayLoading);

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateForm()) {
                    regPresenter.signUp();
                }
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
    public void gotoLogin() {

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(RegisterActivity.this,
                "LogIn "+message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void formReset() {
        eteRegEmail.setText("");
        eteRegEmail.setText("");
        eteRegPassword.setText("");
        etePasswordConfirm.setText("");
    }

    @Override
    public boolean validateForm() {
        username= eteRegEmail.getText().toString();
        password= eteRegPassword.getText().toString();
        nombre= eteRegNombre.getText().toString();

        if(username.isEmpty())
        {
            eteRegEmail.setError("Error campo email");
            return false;
        }
        if(nombre.isEmpty())
        {
            eteRegNombre.setError("Error campo nombre");
            return false;
        }
        if(password.isEmpty())
        {
            eteRegPassword.setError("Error campo password");
            return false;
        }
        return true;
    }

    @Override
    public String getEmail() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getNombre() {
        return nombre;
    }
}
