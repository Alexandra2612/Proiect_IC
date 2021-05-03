package com.crumble.helpplus.Model;

public class Question {
    private int id;
    private String title;
    private String varA;
    private String varB;
    private String varC;
    private String varD;
    private String raspunsCorect;

    public Question() {
    }

    public Question(String title, String varA, String varB, String varC, String varD, String raspunsCorect) {
        this.title = title;
        this.varA = varA;
        this.varB = varB;
        this.varC = varC;
        this.varD = varD;
        this.raspunsCorect = raspunsCorect;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVarA() {
        return varA;
    }

    public void setVarA(String varA) {
        this.varA = varA;
    }

    public String getVarB() {
        return varB;
    }

    public void setVarB(String varB) {
        this.varB = varB;
    }

    public String getVarC() {
        return varC;
    }

    public void setVarC(String varC) {
        this.varC = varC;
    }

    public String getVarD() {
        return varD;
    }

    public void setVarD(String varD) {
        this.varD = varD;
    }

    public String getRaspunsCorect() {
        return raspunsCorect;
    }

    public void setRaspunsCorect(String raspunsCorect) {
        this.raspunsCorect = raspunsCorect;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", varA='" + varA + '\'' +
                ", varB='" + varB + '\'' +
                ", varC='" + varC + '\'' +
                ", varD='" + varD + '\'' +
                ", raspunsCorect=" + raspunsCorect +
                '}';
    }
}
