package com.crumble.helpplus.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.crumble.helpplus.Controller.LoginController;
import com.crumble.helpplus.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {
    private FirebaseUser connectedUser;
    private ImageView profileIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        profileIcon=(ImageView)this.findViewById(R.id.profileIcon);
        profileIcon.setImageResource(R.drawable.profile_base);
    }

    @Override
    public void onStart() {
        super.onStart();
        connectedUser = LoginActivity.connectedUser;
        //update UI

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            signoutAction(null);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void signoutAction(View view)
    {
        FirebaseAuth.getInstance().signOut();
        goToLogin();
    }

    public void goToLogin()
    {
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}