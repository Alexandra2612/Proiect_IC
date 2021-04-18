package com.crumble.helpplus.Model;

public class User {

    private int id;
    private String email;
    private String nickname;
    private String image;
    private double averageGrade;

    public User(int id, String email, String nickname, String image, float averageGrade) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.image = image;
        this.averageGrade = averageGrade;
    }

    public User() {}

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
}
