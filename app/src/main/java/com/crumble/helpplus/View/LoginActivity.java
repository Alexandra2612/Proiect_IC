package com.crumble.helpplus.View;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.annotation.XmlRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crumble.helpplus.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    private EditText loginUsernameField;
    private EditText loginPasswordField;
    private TextView loginResponseText;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //database test
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://help-plus-ba67d-default-rtdb.firebaseio.com");
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
        //auth test
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            //go to home activity
        }
    }

    void updateUI(FirebaseUser user)
    {
        loginResponseText.setVisibility(View.VISIBLE);
        if(user!=null)
            loginResponseText.setText("logged in successfully as " + user.getEmail());
        else
            loginResponseText.setText("login fail");
    }

    public void loginAction(View view){

        loginUsernameField=(EditText)findViewById(R.id.loginUsernameField);
        loginPasswordField=(EditText)findViewById(R.id.loginPasswordField);
        loginResponseText=(TextView)findViewById(R.id.loginResponseText);
        Editable email= loginUsernameField.getText();
        Editable password= loginPasswordField.getText();

        if(!email.toString().isEmpty()&&!password.toString().isEmpty())
            mAuth.signInWithEmailAndPassword(email.toString(), password.toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("login", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("login", "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
        else updateUI(null);
    }

}