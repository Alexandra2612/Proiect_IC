package com.crumble.helpplus.View;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.crumble.helpplus.Controller.NicknameController;
import com.crumble.helpplus.R;

public class NicknameActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nickname);
    }

    @Override
    public void onStart() {
        super.onStart();

        //update UI

    }

    public void goToProfile(View view)
    {
        Intent intent=new Intent(this,ProfileActivity.class);
        startActivity(intent);
    }
    public void changeNickname(View view)
    {
        EditText nicknameField =(EditText) findViewById(R.id.nicknameText);
        Editable nickname= nicknameField.getText();
        NicknameController.changeNickname(nickname.toString());
    }
}
