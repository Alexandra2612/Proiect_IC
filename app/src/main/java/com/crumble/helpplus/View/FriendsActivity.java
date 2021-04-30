package com.crumble.helpplus.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.crumble.helpplus.Controller.FriendsController;
import com.crumble.helpplus.Controller.NicknameController;
import com.crumble.helpplus.Model.User;
import com.crumble.helpplus.R;

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
import static com.crumble.helpplus.View.LoginActivity.IP;

public class FriendsActivity extends AppCompatActivity {
    private RequestQueue queue;
    private LinearLayout friendsList;
    public static User friendUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(this);
        handleSSLHandshake();
        setContentView(R.layout.activity_friends);
        friendsList=(LinearLayout)findViewById(R.id.friendsLayout);
        getList(queue);
    }
    @Override
    public void onStart() {
        super.onStart();
        //update UI
    }
    public void addFriends(View view){
        EditText friendsField =(EditText) findViewById(R.id.friendsText);
        Editable friendsCode= friendsField.getText();
        FriendsController.addFriends(this, queue, friendsCode.toString());
    }
    public void goToProfile(View view){
        Intent intent=new Intent(this,ProfileActivity.class);
        startActivity(intent);
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
        TextView friendsResponseText=(TextView)this.findViewById(R.id.friendsResponseText);
        friendsResponseText.setText(text);
        friendsResponseText.setVisibility(View.VISIBLE);
    }
    public void getList(RequestQueue queue){
        String url ="https://"+IP+"/?action=getall&object=userfriendsbyemail&email="+getConnectedUser().getEmail();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url,
                null,
                response -> {
                    try{
                        if(response.length()==0) {
                            Log.d("Volley","No friends found in database");
                        }
                        else
                            for(int i=0;i<response.length();i++){
                                JSONObject user = response.getJSONObject(i);
                                User u=new User();
                                u.setId(user.getInt("id"));
                                u.setEmail(user.getString("email"));
                                u.setNickname(user.getString("nickname"));
                                u.setImage(user.getString("image"));
                                u.setAverageGrade(user.getDouble("averagegrade"));
                                u.setFriends(user.getString("friends"));
                                Log.e("friends",u.getFriends().toString()+" friends");
                                addUserToList(u);
                                Log.d("Volley","User "+u.getEmail()+" connected");
                            }
                    }catch (JSONException e){
                        Log.e("Volley","Response from database incomplete");
                    }
                },
                error -> Log.e("Volley",error.getMessage()));
        queue.add(jsonArrayRequest);
    }
    public void addUserToList(User user){
        TextView userText=new TextView(this);
        userText.setText(user.getNickname());
        userText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFriendProfile(user);
            }
        });
        friendsList.addView(userText);
    }
    public void goToFriendProfile(User user){
        friendUser=user;
        //stuff with intent here
        Log.d("user",friendUser.toString());
    }
}
