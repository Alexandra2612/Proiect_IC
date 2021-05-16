package com.crumble.helpplus.Controller;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.crumble.helpplus.Model.Quiz;
import com.crumble.helpplus.View.QuizActivity;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
@RunWith(MockitoJUnitRunner.class)
public class QuestionControllerTest{

    @Before
    public void setUp() throws Exception {

    }
    @Test
    public void getDailyQuiz(){
        QuizActivity qa=mock(QuizActivity.class);
        qa.handleSSLHandshake();
        Context context= InstrumentationRegistry.getInstrumentation().getTargetContext();
        RequestQueue queue= Volley.newRequestQueue(context);
        queue.start();
        qa.getDailyQuiz(queue);

        assertNotNull(Quiz.getSelectedQuiz());
    }
    /*@Test
    public void populateQuestions() {
    }

    @Test
    public void sendScore() {
    }*/
}