package com.crumble.helpplus.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.crumble.helpplus.Controller.LoginController;
import com.crumble.helpplus.R;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
    public void registerAction(View view)
    {}
    public void goToLogin(View view)
    {}
}
