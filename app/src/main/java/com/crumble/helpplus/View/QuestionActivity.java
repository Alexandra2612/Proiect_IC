package com.crumble.helpplus.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.crumble.helpplus.R;

public class QuestionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
    }

    @Override
    public void onStart() {
        super.onStart();
        //update UI
    }

    public void goToQuizes(View view)
    {
        Intent intent=new Intent(this,QuizActivity.class);
        startActivity(intent);
    }
    public void goToNextQuestion1(View view)
    {}
    public void goToNextQuestion2(View view)
    {}
    public void goToNextQuestion3(View view)
    {}
    public void goToNextQuestion4(View view)
    {}
}
