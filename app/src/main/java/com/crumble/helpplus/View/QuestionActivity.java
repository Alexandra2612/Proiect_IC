package com.crumble.helpplus.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.crumble.helpplus.Controller.QuestionController;
import com.crumble.helpplus.Model.Question;
import com.crumble.helpplus.R;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import static com.crumble.helpplus.Model.Quiz.getSelectedQuiz;

public class QuestionActivity extends AppCompatActivity {
    private static boolean training;
    private static boolean showcase;
    private RequestQueue queue;
    private ArrayList<Question> questions = new ArrayList<Question>();
    private Button buttonA;
    private Button buttonB;
    private Button buttonC;
    private Button buttonD;
    private TextView questionTitleText;
    private TextView questionScoreText;
    private int score=0;
    private int questionNo=0;
    private String rightAnswer;
    private static Class backClass;
    public static void setShowcase(boolean showcase) {
        QuestionActivity.showcase = showcase;
    }

    public static void setBackClass(Class backClass) {
        QuestionActivity.backClass = backClass;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(this);
        handleSSLHandshake();
        if(!showcase)
            QuestionController.populateQuestions(this,queue,getSelectedQuiz().getId());
        else
            addQuestion(Question.getSelectedQuestion());
        setContentView(R.layout.activity_question);
        buttonA = (Button)findViewById(R.id.answer1Button);
        buttonB = (Button)findViewById(R.id.answer2Button);
        buttonC = (Button)findViewById(R.id.answer3Button);
        buttonD = (Button)findViewById(R.id.answer4Button);
        questionTitleText= (TextView)findViewById(R.id.questionTitleText);
        questionScoreText= (TextView)findViewById(R.id.questionScoreText);
        if(!showcase)
          setAnswersClickable();
        if(showcase){
            setQuestion(0);
            if(rightAnswer.equals("a"))
                buttonA.setBackgroundColor(getResources().getColor(R.color.right_answer));
            if(rightAnswer.equals("b"))
                buttonB.setBackgroundColor(getResources().getColor(R.color.right_answer));
            if(rightAnswer.equals("c"))
                buttonC.setBackgroundColor(getResources().getColor(R.color.right_answer));
            if(rightAnswer.equals("d"))
                buttonD.setBackgroundColor(getResources().getColor(R.color.right_answer));
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        //update UI
    }

    public void sendFinalScore()
    {
        goToQuizes(null); // will be done in sendscore handler instead when implemented
    }

    public void setQuestion(int i)
    {
        if(i==getSelectedQuiz().getQuestionno())
        {
            setAnswersUnclickable();
            buttonA.setText("");
            buttonB.setText("");
            buttonC.setText("");
            buttonD.setText("");
            questionTitleText.setText("FINAL SCORE:"+((double)score)/questionNo*10);
            if(((double)score)/getSelectedQuiz().getQuestionno()>=0.66)
                questionTitleText.append("\nCONGRATULATIONS!!!");
            else
                questionTitleText.append("\nBETTER LUCK NEXT TIME :)");
            if(!training)
                QuestionController.sendScore(this,queue,((double)score)/questionNo*10);
            (new Handler()).postDelayed(this::sendFinalScore, 3000);
            return;
        }
        Question q=questions.get(i);
        questionTitleText.setText(q.getTitle());
        buttonA.setText(q.getVarA());
        buttonB.setText(q.getVarB());
        buttonC.setText(q.getVarC());
        buttonD.setText(q.getVarD());
        rightAnswer=q.getRaspunsCorect();
    }

    public void goToQuizes(View view)
    {
        Intent intent=new Intent(this,backClass);
        startActivity(intent);
    }

    private void setAnswersClickable() {
        buttonA.setClickable(true);
        buttonB.setClickable(true);
        buttonC.setClickable(true);
        buttonD.setClickable(true);
        buttonA.setBackgroundColor(getResources().getColor(R.color.white));
        buttonB.setBackgroundColor(getResources().getColor(R.color.white));
        buttonC.setBackgroundColor(getResources().getColor(R.color.white));
        buttonD.setBackgroundColor(getResources().getColor(R.color.white));
        questionScoreText.setText("score:"+score+"/"+getSelectedQuiz().getQuestionno());
        questionNo++;
    }

    private void setAnswersUnclickable() {
        buttonA.setClickable(false);
        buttonB.setClickable(false);
        buttonC.setClickable(false);
        buttonD.setClickable(false);
    }

    public void goToNextQuestionPart2()
    {
        setQuestion(questionNo);
        setAnswersClickable();
    }

    public void goToNextQuestion1(View view) throws InterruptedException {
        setAnswersUnclickable();
        if(rightAnswer.equals("a")) {
            score++;
            buttonA.setBackgroundColor(getResources().getColor(R.color.right_answer));
        }
        else
            buttonA.setBackgroundColor(getResources().getColor(R.color.wrong_answer));
        if(rightAnswer.equals("b"))
            buttonB.setBackgroundColor(getResources().getColor(R.color.right_answer));
        if(rightAnswer.equals("c"))
            buttonC.setBackgroundColor(getResources().getColor(R.color.right_answer));
        if(rightAnswer.equals("d"))
            buttonD.setBackgroundColor(getResources().getColor(R.color.right_answer));
        (new Handler()).postDelayed(this::goToNextQuestionPart2, 3000);
    }

    public void goToNextQuestion2(View view) throws InterruptedException {
        setAnswersUnclickable();
        if(rightAnswer.equals("b")) {
            score++;
            buttonB.setBackgroundColor(getResources().getColor(R.color.right_answer));
        }
        else
            buttonB.setBackgroundColor(getResources().getColor(R.color.wrong_answer));
        if(rightAnswer.equals("a"))
            buttonA.setBackgroundColor(getResources().getColor(R.color.right_answer));
        if(rightAnswer.equals("c"))
            buttonC.setBackgroundColor(getResources().getColor(R.color.right_answer));
        if(rightAnswer.equals("d"))
            buttonD.setBackgroundColor(getResources().getColor(R.color.right_answer));
        (new Handler()).postDelayed(this::goToNextQuestionPart2, 3000);
    }
    public void goToNextQuestion3(View view) throws InterruptedException {
        setAnswersUnclickable();
        if(rightAnswer.equals("c")) {
            score++;
            buttonC.setBackgroundColor(getResources().getColor(R.color.right_answer));
        }
        else
            buttonC.setBackgroundColor(getResources().getColor(R.color.wrong_answer));
        if(rightAnswer.equals("a"))
            buttonA.setBackgroundColor(getResources().getColor(R.color.right_answer));
        if(rightAnswer.equals("b"))
            buttonB.setBackgroundColor(getResources().getColor(R.color.right_answer));
        if(rightAnswer.equals("d"))
            buttonD.setBackgroundColor(getResources().getColor(R.color.right_answer));
        (new Handler()).postDelayed(this::goToNextQuestionPart2, 3000);
    }
    public void goToNextQuestion4(View view) throws InterruptedException {
        setAnswersUnclickable();
        if(rightAnswer.equals("d")) {
            score++;
            buttonD.setBackgroundColor(getResources().getColor(R.color.right_answer));
        }
        else
            buttonD.setBackgroundColor(getResources().getColor(R.color.wrong_answer));
        if(rightAnswer.equals("a"))
            buttonA.setBackgroundColor(getResources().getColor(R.color.right_answer));
        if(rightAnswer.equals("b"))
            buttonB.setBackgroundColor(getResources().getColor(R.color.right_answer));
        if(rightAnswer.equals("c"))
            buttonC.setBackgroundColor(getResources().getColor(R.color.right_answer));
        (new Handler()).postDelayed(this::goToNextQuestionPart2, 3000);
    }

    public static boolean getTraining() {
        return training;
    }

    public static void setTraining(boolean training) {
        QuestionActivity.training = training;
    }

    public void addQuestion(Question q) {
        questions.add(q);
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
