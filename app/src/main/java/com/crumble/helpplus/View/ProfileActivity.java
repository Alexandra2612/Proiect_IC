package com.crumble.helpplus.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.crumble.helpplus.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseUser connectedUser;
    private ImageView profileIcon;
    private TextView usernameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        connectedUser = LoginActivity.connectedUser;
        profileIcon=(ImageView)this.findViewById(R.id.profileIcon);
        profileIcon.setImageResource(R.drawable.profile_base);
        usernameText=(TextView)this.findViewById(R.id.usernameText);
        usernameText.setText(connectedUser.getEmail());
    }

    @Override
    public void onStart() {
        super.onStart();

        //update UI

    }


    public void goToHome(View view)
    {
        Intent intent=new Intent(this,HomeActivity.class);
        startActivity(intent);
    }
    public void goToChangePicture(View view)
    {
        Intent intent=new Intent(this,ProfilePictureActivity.class);
        startActivity(intent);
    }
    public void goToSetNickname(View view)
    {
        Intent intent=new Intent(this,NicknameActivity.class);
        startActivity(intent);
    }
}