package com.crumble.helpplus.Controller;

import android.app.Application;
import android.content.Context;
import android.content.res.loader.ResourcesLoader;
import android.os.FileUtils;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.test.platform.app.InstrumentationRegistry;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.crumble.helpplus.Controller.FriendsController;
import com.crumble.helpplus.Controller.LoginController;
import com.crumble.helpplus.Controller.RegisterController;
import com.crumble.helpplus.Model.User;
import com.crumble.helpplus.R;
import com.crumble.helpplus.View.FriendsActivity;
import com.crumble.helpplus.View.RegisterActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static androidx.test.InstrumentationRegistry.getContext;
import static com.crumble.helpplus.Model.User.setConnectedUser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class FriendsControllerTest {

    //mocked classes
    @Mock
    FriendsController fcon;
    @Mock
    FriendsActivity fact;
    @Mock
    Context mockContext;

    //test user data
    static int id=7;
    static String email="a@b.cd";
    static String nickname="estebann2";
    static String image="profile/15.jpg";
    static float grade=7.408055555555f;
    static String friends="10,11,13,6,15,14";

    //test user
    static User testUser = new User(id,email,nickname,image,grade,friends);

    @BeforeClass
    public static void classSetup()
    {//int id, String email, String nickname, String image, float averageGrade,String friends
        setConnectedUser(testUser);
    }

    @Test
    public void getFriends()
    {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        fact.setContentView(R.layout.activity_friends);
        //new ResourcesLoader().
        fact.friendsList=(LinearLayout)fact.findViewById(R.id.friendsLayout);
        System.out.println(fact.friendsList.getId());
        assertNotNull(fact);
        assertNotNull(fcon);
        RequestQueue q = Volley.newRequestQueue(appContext);
        q.start();
        fact.getList(q);
        assertNotEquals(0,fact.friendsList.getChildCount());
    }

}