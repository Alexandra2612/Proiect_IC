package com.crumble.helpplus.Controller;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.crumble.helpplus.Model.Question;
import com.crumble.helpplus.Model.User;
import com.crumble.helpplus.View.FriendsActivity;
import com.crumble.helpplus.View.QuestionActivity;

import org.json.JSONException;
import org.json.JSONObject;

import static com.crumble.helpplus.Model.Quiz.getSelectedQuiz;
import static com.crumble.helpplus.Model.User.getConnectedUser;
import static com.crumble.helpplus.Model.User.getFirebaseConnectedUser;
import static com.crumble.helpplus.View.LoginActivity.IP;

public class QuestionController {

    public static void populateQuestions(QuestionActivity activity, RequestQueue queue, int id){
        String url ="https://"+IP+"/?action=getall&object=questionsbyquizid&quizid="+getSelectedQuiz().getId();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url,
                null,
                response -> {
                    try{
                        if(response.length()==0) {
                            Log.d("Volley","No questions found in database");
                        }
                        else {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject question = response.getJSONObject(i);
                                Question q = new Question();
                                q.setId(question.getInt("id"));
                                q.setTitle(question.getString("title"));
                                q.setVarA(question.getString("varA"));
                                q.setVarB(question.getString("varB"));
                                q.setVarC(question.getString("varC"));
                                q.setVarD(question.getString("varD"));
                                q.setRaspunsCorect(question.getString("raspunscorect"));
                                activity.addQuestion(q);
                                Log.d("Volley", "Added question" + q.toString());
                            }
                            activity.setQuestion(0);
                        }
                    }catch (JSONException e){
                        Log.e("Volley","Response from database incomplete");
                    }
                },
                error -> Log.e("Volley",error.getMessage()));
        queue.add(jsonArrayRequest);
    }
    public static void sendScore(QuestionActivity activity, RequestQueue queue, double score) {
        String url ="https://"+IP+"/?action=send&object=quizscorebyid&id="+getConnectedUser().getId()+"&score="+score;
        Log.d("Volley","Sending grade to mysql");

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, url,
                (String response) -> {
                    if(response.equals("user grade sent successfully")) {
                        Log.d("Volley", "user grade sent successfully");
                        LoginController.setConnectedUserData(queue);
                    }
                    else {
                        Log.d("Volley", response);
                        Log.e("Volley", "User grade could not be sent to database");
                    }
                },
                error -> {Log.e("Volley",error.getMessage());});
        queue.add(stringRequest);
    }


}
