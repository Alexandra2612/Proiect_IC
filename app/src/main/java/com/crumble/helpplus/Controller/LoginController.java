package com.crumble.helpplus.Controller;

import android.os.Build;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.crumble.helpplus.Model.User;
import com.crumble.helpplus.R;
import com.crumble.helpplus.View.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.crumble.helpplus.Model.User.getFirebaseConnectedUser;
import static com.crumble.helpplus.Model.User.setConnectedUser;
import static com.crumble.helpplus.View.LoginActivity.IP;

public class LoginController {

    public static void updateUI(final LoginActivity loginActivity, FirebaseUser user)
    {
        loginActivity.loginResponseText.setVisibility(View.VISIBLE);
        if(user!=null)
            loginActivity.loginResponseText.setText("logged in successfully as " + user.getEmail());
        else
            loginActivity.loginResponseText.setText("login fail");
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void loginAction(final LoginActivity loginActivity, View view){

        loginActivity.loginUsernameField =(EditText) loginActivity.findViewById(R.id.loginUsernameField);
        loginActivity.loginPasswordField =(EditText) loginActivity.findViewById(R.id.loginPasswordField);
        loginActivity.loginResponseText =(TextView) loginActivity.findViewById(R.id.loginResponseText);
        Editable email= loginActivity.loginUsernameField.getText();
        Editable password= loginActivity.loginPasswordField.getText();

        if(!email.toString().isEmpty()&&!password.toString().isEmpty())
            loginActivity.mAuth.signInWithEmailAndPassword(email.toString(), LoginController.hashString(password.toString()))
                    .addOnCompleteListener(loginActivity, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("login", "signInWithEmail:success");
                                FirebaseUser user = loginActivity.mAuth.getCurrentUser();
                                LoginController.updateUI(loginActivity, user);
                                User.setFirebaseConnectedUser(user);
                                loginActivity.checkLoggedIn();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("login", "signInWithEmail:failure", task.getException());
                                Toast.makeText(loginActivity, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                LoginController.updateUI(loginActivity, null);
                            }
                        }
                    });
        else LoginController.updateUI(loginActivity, null);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String hashString(String init){
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(
                    init.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }

    }
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static void setConnectedUserData(RequestQueue queue)
    {
        String url ="https://"+IP+"/?action=get&object=userdatabyemail&email="+getFirebaseConnectedUser().getEmail();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url,
                null,
                response -> {
                    try{
                        if(response.length()==0) {
                            Log.d("Volley","User not found in database");
                            addNewUser(queue);
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
                                setConnectedUser(u);
                                Log.d("Volley","User "+u.getEmail()+" connected");
                            }
                    }catch (JSONException e){
                        Log.e("Volley","Response from database incomplete");
                    }
                },
                error -> Log.e("Volley",error.getMessage()));
        queue.add(jsonArrayRequest);
    }

    public static void addNewUser(RequestQueue queue)
    {
        String url ="https://"+IP+"/?action=create&object=user&email="+getFirebaseConnectedUser().getEmail();
        Log.d("Volley","Creating user in mysql");

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, url,
                (String response) -> {
                    if(response.equals("user added")) {
                        Log.d("Volley", "Registration to mysql complete");
                        setConnectedUserData(queue);
                    }
                    else {
                        Log.d("Volley", response);
                        Log.e("Volley", "User could not be registered to database");
                    }
                },
                error -> {Log.e("Volley",error.getMessage());});
        queue.add(stringRequest);
    }

}
