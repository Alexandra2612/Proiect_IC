package com.crumble.helpplus.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.crumble.helpplus.R;

public class QuizesActivity extends AppCompatActivity {
    private ImageView profileIcon6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizes);

        profileIcon6=(ImageView)this.findViewById(R.id.profileIcon6);
        profileIcon6.setImageResource(R.drawable.profile_base);
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
    public void goToQuizPage(View view)
    {
        Intent intent=new Intent(this,QuizActivity.class);
        startActivity(intent);
    }
}
