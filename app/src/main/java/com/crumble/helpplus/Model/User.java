package com.crumble.helpplus.Model;

import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import java.util.*;

public class User {
    private static User connectedUser;
    public static User getConnectedUser(){return connectedUser;}
    public static void setConnectedUser(User u){connectedUser=u;}

    private static FirebaseUser firebaseConnectedUser;
    public static FirebaseUser getFirebaseConnectedUser(){return firebaseConnectedUser;}
    public static void setFirebaseConnectedUser(FirebaseUser u){firebaseConnectedUser=u;}

    private int id;
    private String email;
    private String nickname;
    private String image;
    private ArrayList<String> friends = new ArrayList<String>();
    private double averageGrade;

    public User(int id, String email, String nickname, String image, float averageGrade,String friends) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.image = image;
        this.averageGrade = averageGrade;
        String str[] = friends.split(",");
        Collections.addAll(this.friends,str);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", image='" + image + '\'' +
                ", friends=" + friends +
                ", averageGrade=" + averageGrade +
                '}';
    }

    public User() {}

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", image='" + image + '\'' +
                ", averageGrade=" + averageGrade +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(String friends) {
        try {
            String str[] = friends.split(",");
            Collections.addAll(this.friends,str);
        }catch(NullPointerException e){
            this.friends=new ArrayList<String>();
        }

    }
}
