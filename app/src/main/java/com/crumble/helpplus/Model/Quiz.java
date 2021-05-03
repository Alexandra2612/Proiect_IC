package com.crumble.helpplus.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Quiz {
    private static Quiz selectedQuiz=null;
    private int id;
    private String title;
    private int questionno;
    private ArrayList<String> questionlist=new ArrayList<String>();

    public static Quiz getSelectedQuiz() {
        return selectedQuiz;
    }

    public static void setSelectedQuiz(Quiz selectedQuiz) {
        Quiz.selectedQuiz = selectedQuiz;
    }

    public Quiz(){}

    public Quiz(String title, int questionno,String questionlist) {
        this.title = title;
        this.questionno = questionno;
        String str[] = questionlist.split(",");
        Collections.addAll(this.questionlist,str);
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

    public int getQuestionno() {
        return questionno;
    }

    public void setQuestionno(int questionno) {
        this.questionno = questionno;
    }

    public List<String> getQuestionlist() {
        return questionlist;
    }

    public void setQuestionlist(String questionlist) {
        try {
            String str[] = questionlist.split(",");
            Collections.addAll(this.questionlist,str);
        }catch(NullPointerException e){
            this.questionlist=new ArrayList<String>();
        }
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", questionno=" + questionno +
                ", questionlist=" + questionlist +
                '}';
    }
}
