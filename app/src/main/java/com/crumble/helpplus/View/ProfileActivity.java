package com.crumble.helpplus.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.crumble.helpplus.Model.User;
import com.crumble.helpplus.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import static com.crumble.helpplus.Model.User.getConnectedUser;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseUser firebaseConnectedUser;
    private ImageView profileIcon;
    private TextView usernameText;
    private TextView gradeText;
    private TextView idText;
    private Drawable profileImage=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("gothere","9");

        firebaseConnectedUser = User.getFirebaseConnectedUser();
        setContentView(R.layout.activity_profile);
        profileIcon=(ImageView)this.findViewById(R.id.profileIcon);
        usernameText=(TextView)this.findViewById(R.id.usernameText);
        gradeText=(TextView)this.findViewById(R.id.gradeText);
        idText=(TextView)this.findViewById(R.id.idText);

        if(getConnectedUser().getImage()=="null")
            profileIcon.setImageResource(R.drawable.profile_base);
        else {
            Thread t = new Thread(() -> {
                profileImage = LoadImageFromWebOperations(getConnectedUser().getImage());
                profileIcon.post(() -> {
                    profileIcon.setImageDrawable(profileImage);
                });
            });
            t.start();
        }
        usernameText.setText(getConnectedUser().getNickname());
        gradeText.setText(gradeText.getText()+" "+(((Double)getConnectedUser().getAverageGrade()).toString()));
        idText.setText(idText.getText()+" "+(((Integer)getConnectedUser().getId()).toString())+"  ");

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

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}