package com.crumble.helpplus.Controller;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.crumble.helpplus.View.LoginActivity;
import com.crumble.helpplus.View.NicknameActivity;

import static com.crumble.helpplus.Model.User.getConnectedUser;
import static com.crumble.helpplus.Model.User.getFirebaseConnectedUser;
import static com.crumble.helpplus.View.LoginActivity.IP;

public class NicknameController {
    public static void changeNickname(NicknameActivity activity, RequestQueue queue, String nickname){
        String url ="https://"+IP+"/?action=update&object=usernicknamebyemail&email="+getFirebaseConnectedUser().getEmail()+"&nickname="+nickname;
        Log.d("Volley","Changing nickname in mysql");

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, url,
                (String response) -> {
                    if(response.equals("user data changed successfully")) {
                        getConnectedUser().setNickname(nickname);
                        Log.d("Volley", "Nickname changed in mysql complete");
                        activity.updateUI("Nickname changed succesfully!");
                    }
                    else {
                        Log.d("Volley", response);
                        Log.e("Volley", "Nickname could not be changed in database");
                        activity.updateUI("Nickname could not be changed :(");
                    }
                },
                error -> {Log.e("Volley",error.getMessage());});
        queue.add(stringRequest);
    }

}
