package com.crumble.helpplus.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.crumble.helpplus.R;

public class QuizActivity extends AppCompatActivity {
    private ImageView profileIcon5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        profileIcon5=(ImageView)this.findViewById(R.id.profileIcon5);
        profileIcon5.setImageResource(R.drawable.profile_base);
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
    public void goToHome(View view)
    {
        Intent intent=new Intent(this,HomeActivity.class);
        startActivity(intent);
    }
    public void goToQuestion(View view)
    {
        Intent intent=new Intent(this,QuestionActivity.class);
        startActivity(intent);
    }
    public void goToQuizes(View view)
    {
        Intent intent=new Intent(this,QuizesActivity.class);
        startActivity(intent);
    }
}
