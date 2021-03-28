package com.crumble.helpplus.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //database test
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://help-plus-ba67d-default-rtdb.firebaseio.com");
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
        //auth test
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            //go to home activity
        }
    }
    public void loginAction(View view)
    {
        LoginController.loginAction(this,view);
    }
    public void goToRegister(View view)
    {
        Intent intent=new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
}