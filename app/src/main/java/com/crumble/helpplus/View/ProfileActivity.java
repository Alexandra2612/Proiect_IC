package com.crumble.helpplus.View;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseUser firebaseConnectedUser;
    private ImageView profileIcon;
    private TextView usernameText;
    private TextView gradeText;
    private TextView idText;

    private User connectedUser= new User();
    public void setConnectedUserData()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://86.123.241.117/?action=get&object=userdatabyemail&email="+firebaseConnectedUser.getEmail();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url,
                null,
                response -> {
                    try{
                        for(int i=0;i<response.length();i++){
                            JSONObject user = response.getJSONObject(i);
                            connectedUser.setId(user.getInt("id"));
                            connectedUser.setEmail(user.getString("email"));
                            connectedUser.setNickname(user.getString("nickname"));
                            connectedUser.setImage(user.getString("image"));
                            connectedUser.setAverageGrade(user.getDouble("averagegrade"));
                            usernameText.setText(connectedUser.getNickname());
                            gradeText.setText(gradeText.getText()+" "+(((Double)connectedUser.getAverageGrade()).toString()));
                            idText.setText(idText.getText()+" "+(((Integer)connectedUser.getId()).toString())+"  ");
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                },
                error -> Log.e("Volley",error.getMessage()));
        queue.add(jsonArrayRequest);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleSSLHandshake();
        firebaseConnectedUser = LoginActivity.connectedUser;
        setConnectedUserData();
        setContentView(R.layout.activity_profile);
        profileIcon=(ImageView)this.findViewById(R.id.profileIcon);
        profileIcon.setImageResource(R.drawable.profile_base);
        usernameText=(TextView)this.findViewById(R.id.usernameText);
        gradeText=(TextView)this.findViewById(R.id.gradeText);
        idText=(TextView)this.findViewById(R.id.idText);
        usernameText.setText("");



    }

    @Override
    public void onStart() {
        super.onStart();

        //update UI

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


    public void goToHome(View view)
    {
        Intent intent=new Intent(this,HomeActivity.class);
        startActivity(intent);
    }

}