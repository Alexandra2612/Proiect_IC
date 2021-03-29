package com.crumble.helpplus.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.crumble.helpplus.Controller.LoginController;
import com.crumble.helpplus.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    public EditText loginUsernameField;
    public EditText loginPasswordField;
    public TextView loginResponseText;
    public FirebaseAuth mAuth;
    public static FirebaseUser connectedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //database test
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://help-plus-ba67d-default-rtdb.firebaseio.com");
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue("Hello, World!");

        checkLoggedIn();

        setContentView(R.layout.activity_main);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

    }

    public void checkLoggedIn()
    {
        mAuth = FirebaseAuth.getInstance();
        connectedUser = mAuth.getCurrentUser();
        if(connectedUser != null){
            goToHome();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void loginAction(View view) throws InterruptedException {
        if(mAuth.getCurrentUser()==null)
            LoginController.loginAction(this,view);
        checkLoggedIn();
    }
    public void goToRegister(View view)
    {
        Intent intent=new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
    public void goToHome()
    {
        Intent intent=new Intent(this,HomeActivity.class);
        startActivity(intent);
    }
}