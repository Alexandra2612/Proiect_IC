package com.crumble.helpplus.View;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.crumble.helpplus.Controller.LoginController;
import com.crumble.helpplus.Controller.RegisterController;
import com.crumble.helpplus.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    public FirebaseAuth mAuth;
    public EditText registerUsernameField;
    public EditText registerPasswordField;
    public TextView registerResponseText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            //go to home
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void registerAction(View view)
    {
        if(mAuth.getCurrentUser()==null)
            RegisterController.registerAction(view,this);
        if(mAuth.getCurrentUser()!=null)
            goToLogin(null);
    }

    public void checkLoggedIn()
    {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser connectedUser = mAuth.getCurrentUser();
        if(connectedUser != null){
            goToLogin(null);
        }
    }

    public void goToLogin(View view)
    {
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}
