package com.crumble.helpplus.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.crumble.helpplus.Controller.LoginController;
import com.crumble.helpplus.Model.User;
import com.crumble.helpplus.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import static com.crumble.helpplus.Model.User.setConnectedUser;

public class HomeActivity extends AppCompatActivity {

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
        setConnectedUser(null);
        goToLogin();
    }

    public void goToLogin()
    {
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    public void goToProfile(View view)
    {
        Intent intent=new Intent(this,ProfileActivity.class);
        startActivity(intent);
    }
    public void goToQuestions(View view)
    {
        Intent intent=new Intent(this,QuestionsActivity.class);
        startActivity(intent);
    }
    public void goToRoutines(View view)
    {
        Intent intent=new Intent(this,RoutinesActivity.class);
        startActivity(intent);
    }
    public void goToQuizes(View view)
    {
        Intent intent=new Intent(this,QuizActivity.class);
        startActivity(intent);
    }
}