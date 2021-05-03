package com.crumble.helpplus.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.crumble.helpplus.Model.Quiz;
import com.crumble.helpplus.Model.User;
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

import static com.crumble.helpplus.Model.Quiz.getSelectedQuiz;
import static com.crumble.helpplus.Model.Quiz.setSelectedQuiz;
import static com.crumble.helpplus.Model.User.getConnectedUser;
import static com.crumble.helpplus.Model.User.getFirebaseConnectedUser;
import static com.crumble.helpplus.Model.User.setConnectedUser;
import static com.crumble.helpplus.View.LoginActivity.IP;

public class QuizActivity extends AppCompatActivity {
    private ImageView profileIcon5;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(this);
        handleSSLHandshake();
        getDailyQuiz(queue);
        setContentView(R.layout.activity_quiz);
        profileIcon5=(ImageView)findViewById(R.id.profileIcon5);
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
                profileIcon5.setImageBitmap(Bitmap.createScaledBitmap(bmp, profileIcon5.getWidth(), profileIcon5.getHeight(), false));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                profileIcon5.setImageResource(R.drawable.profile_base);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        //update UI

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
    public void goToQuestion(View view)
    {
        if(getSelectedQuiz()==null)
            return;
        QuestionActivity.setTraining(false);
        QuestionActivity.setBackClass(QuizActivity.class);
        Intent intent=new Intent(this,QuestionActivity.class);
        startActivity(intent);
    }
    public void goToQuizes(View view)
    {
        Intent intent=new Intent(this,QuizesActivity.class);
        startActivity(intent);
    }
    public void getDailyQuiz(RequestQueue queue){
        String url ="https://"+IP+"/?action=get&object=dailyquiz";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url,
                null,
                response -> {
                    try{
                        if(response.length()==0) {
                            Log.d("Volley","Quiz not found in database");
                        }
                        else {
                            for(int i=0;i<response.length();i++){
                                JSONObject quiz = response.getJSONObject(i);
                                Quiz q=new Quiz();
                                q.setId(quiz.getInt("id"));
                                q.setTitle(quiz.getString("title"));
                                q.setQuestionno(quiz.getInt("questionNo"));
                                q.setQuestionlist(quiz.getString("questionList"));
                                setSelectedQuiz(q);
                                Log.d("Volley","Daily quiz "+q.getTitle()+" downloaded");
                                Log.d("Quiz",q.toString());
                            }
                        }
                    }catch (JSONException e){
                        Log.e("Volley","Response from database incomplete");
                    }
                },
                error -> Log.e("Volley",error.getMessage()));
        queue.add(jsonArrayRequest);
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

}
