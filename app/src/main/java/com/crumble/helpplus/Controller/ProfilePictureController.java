package com.crumble.helpplus.Controller;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.crumble.helpplus.View.NicknameActivity;
import com.crumble.helpplus.View.ProfilePictureActivity;

import static com.crumble.helpplus.Model.User.getConnectedUser;
import static com.crumble.helpplus.Model.User.getFirebaseConnectedUser;
import static com.crumble.helpplus.View.LoginActivity.IP;

public class ProfilePictureController {
    public static void changePicture(ProfilePictureActivity activity, RequestQueue queue, String image){
            String url ="https://"+IP+"/?action=update&object=userimagebyemail&email="+getFirebaseConnectedUser().getEmail()+"&image="+image;
            Log.d("Volley","Changing nickname in mysql");

            StringRequest stringRequest = new StringRequest(
                    Request.Method.GET, url,
                    (String response) -> {
                        if(response.equals("user data changed successfully")) {
                            getConnectedUser().setImage(image);
                            Log.d("Volley", "Nickname changed in mysql complete");
                            activity.updateUI("Image changed succesfully!");
                        }
                        else {
                            Log.d("Volley", response);
                            Log.e("Volley", "Nickname could not be changed in database");
                            activity.updateUI("Image could not be changed :(");
                        }
                    },
                    error -> {Log.e("Volley",error.getMessage());});
            queue.add(stringRequest);
    }
}

