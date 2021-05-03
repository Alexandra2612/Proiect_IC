package com.crumble.helpplus.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.crumble.helpplus.Model.Question;
import com.crumble.helpplus.Model.Quiz;
import com.crumble.helpplus.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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

import static com.crumble.helpplus.Model.Question.setSelectedQuestion;
import static com.crumble.helpplus.Model.Quiz.getSelectedQuiz;
import static com.crumble.helpplus.Model.Quiz.setSelectedQuiz;
import static com.crumble.helpplus.Model.User.getConnectedUser;
import static com.crumble.helpplus.View.LoginActivity.IP;
import static com.crumble.helpplus.View.QuestionActivity.setShowcase;
import static com.crumble.helpplus.View.QuestionActivity.setTraining;

public class QuestionsActivity extends AppCompatActivity {
    private ImageView profileIcon3;
    private RequestQueue queue;
    private LinearLayout questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(this);
        handleSSLHandshake();
        setContentView(R.layout.activity_questions);
        profileIcon3=(ImageView)this.findViewById(R.id.profileIcon3);
        questionList=(LinearLayout) findViewById(R.id.questionsLayout);
        getList(queue);
        setProfPic();

    }

    public void setProfPic() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(getConnectedUser().getImage());

        final long TEN_MEGABYTE = 1024 * 1024 * 10;
        islandRef.getBytes(TEN_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                profileIcon3.setImageBitmap(Bitmap.createScaledBitmap(bmp, profileIcon3.getWidth(), profileIcon3.getHeight(), false));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                profileIcon3.setImageResource(R.drawable.profile_base);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        //update UI

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

    public void getList(RequestQueue queue){
        String url ="https://"+IP+"/?action=get&object=questionlist";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url,
                null,
                response -> {
                    try{
                        if(response.length()==0) {
                            Log.e("Volley","No questions found in database");
                        }
                        else
                            for(int i=0;i<response.length();i++){
                                JSONObject question = response.getJSONObject(i);
                                Question q=new Question();
                                q.setId(question.getInt("id"));
                                q.setTitle(question.getString("title"));
                                q.setVarA(question.getString("varA"));
                                q.setVarB(question.getString("varB"));
                                q.setVarC(question.getString("varC"));
                                q.setVarD(question.getString("varD"));
                                q.setRaspunsCorect(question.getString("raspunscorect"));
                                Log.d("Question","Question: "+q.toString());
                                addQuestionToList(q);
                            }
                    }catch (JSONException e){
                        Log.e("Volley","Response from database incomplete");
                    }
                },
                error -> Log.e("Volley",error.getMessage()));
        queue.add(jsonArrayRequest);
    }
    public void addQuestionToList(Question question){
        TextView questionText=new TextView(this);
        questionText.setText(question.getTitle());
        questionText.setTextSize(25);
        questionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToQuestion(question);
            }
        });
        questionList.addView(questionText);
    }
    public void goToQuestion(Question question){
        Quiz quiz=new Quiz();
        quiz.setQuestionlist(String.valueOf(question.getId()));
        quiz.setTitle("ceva");
        quiz.setId(3);
        quiz.setQuestionno(1);
        setSelectedQuestion(question);
        setSelectedQuiz(quiz);
        setShowcase(true);
        QuestionActivity.setBackClass(QuestionsActivity.class);
        Intent intent=new Intent(this,QuestionActivity.class);
        startActivity(intent);
        Log.d("Quiz",getSelectedQuiz().toString());
    }
    public void goToProfile(View view)
    {
        Intent intent=new Intent(this,ProfileActivity.class);
        startActivity(intent);
    }
    public void goToHome(View view)
    {
        Intent intent=new Intent(this,HomeActivity.class);
        startActivity(intent);
    }
}
