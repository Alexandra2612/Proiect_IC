package com.crumble.helpplus.Controller;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.crumble.helpplus.View.FriendsActivity;

import static com.crumble.helpplus.Model.User.getConnectedUser;
import static com.crumble.helpplus.Model.User.getFirebaseConnectedUser;
import static com.crumble.helpplus.View.LoginActivity.IP;

public class FriendsController {
    public static void addFriends(FriendsActivity activity, RequestQueue queue, String code){
        if((!getConnectedUser().getFriends().contains(code)) && ((Integer.parseInt(code))!=getConnectedUser().getId())){
            String url ="https://"+IP+"/?action=update&object=userfriendlistbyemail&email="+getFirebaseConnectedUser().getEmail()+"&friendcode="+code;
            Log.d("Volley","Adding friend in mysql");
            StringRequest stringRequest = new StringRequest(
                    Request.Method.GET, url,
                    (String response) -> {
                        if(response.equals("user data changed successfully")) {
                            getConnectedUser().getFriends().add(code);
                            Log.d("Volley", "Friend added in mysql complete");
                            activity.updateUI("Friend added successfully!");
                        }
                        else {
                            Log.d("Volley", response);
                            Log.e("Volley", "Friend could not be added in database");
                            activity.updateUI("Friend could not be added :(");
                        }
                    },
                    error -> {Log.e("Volley",error.getMessage());});
            queue.add(stringRequest);
        }else {
            Log.d("Volley", "Friend already exist in mysql or is self");
            activity.updateUI("Friend could not be added :(");
        }

    }
}
