package com.crumble.helpplus.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
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

import static com.crumble.helpplus.Model.User.getConnectedUser;
import static com.crumble.helpplus.Model.User.getFirebaseConnectedUser;
import static com.crumble.helpplus.Model.User.setConnectedUser;
import static com.crumble.helpplus.Model.User.setFirebaseConnectedUser;

public class LoginActivity extends AppCompatActivity {
    public EditText loginUsernameField;
    public EditText loginPasswordField;
    public TextView loginResponseText;
    public FirebaseAuth mAuth;
    private RequestQueue queue;
    private boolean threadRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(this);
        handleSSLHandshake(); // cod prost facut care permite orice certificate pentru Volley Request. !! NOT SECURE !!
        checkLoggedIn();
        setContentView(R.layout.activity_main);

        Thread t = new Thread(()->{
            while(getConnectedUser()==null&&threadRunning) {
            try { Thread.sleep(1000);
                } catch (InterruptedException e) { e.printStackTrace(); }
            }
            if(threadRunning)
                goToHome();
        });
        t.setDaemon(true);
        threadRunning=true;
        t.start(); // aparent start() inseamna thread paralel si run() inseamna pe threadul asta. Good to know.
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void checkLoggedIn()
    {
        mAuth = FirebaseAuth.getInstance();
        setFirebaseConnectedUser(mAuth.getCurrentUser());
        if(getFirebaseConnectedUser() != null){
            setConnectedUserData();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void loginAction(View view) throws InterruptedException {
        if(mAuth.getCurrentUser()==null)
            LoginController.loginAction(this,view);
        checkLoggedIn();
    }
    public void goToRegister(View view)
    {
        threadRunning=false;
        Intent intent=new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
    public void goToHome()
    {
        Intent intent=new Intent(this,HomeActivity.class);
        startActivity(intent);

    }

    public void setConnectedUserData()
    {
        LoginController.setConnectedUserData(queue);
    }

    @SuppressLint("TrulyRandom")
    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }




}