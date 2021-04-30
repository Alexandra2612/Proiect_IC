package com.crumble.helpplus.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.crumble.helpplus.Controller.NicknameController;
import com.crumble.helpplus.R;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class NicknameActivity extends AppCompatActivity {
    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(this);
        handleSSLHandshake();
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
        NicknameController.changeNickname(this,queue,nickname.toString());
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
    public void updateUI(String text){
        TextView nicknameResponseText=(TextView)this.findViewById(R.id.nicknameResponseText);
        nicknameResponseText.setText(text);
        nicknameResponseText.setVisibility(View.VISIBLE);
    }
}
