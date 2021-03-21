package com.crumble.helpplus.Controller;

import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.crumble.helpplus.R;
import com.crumble.helpplus.View.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class LoginController {

    public static void updateUI(final LoginActivity loginActivity, FirebaseUser user)
    {
        loginActivity.loginResponseText.setVisibility(View.VISIBLE);
        if(user!=null)
            loginActivity.loginResponseText.setText("logged in successfully as " + user.getEmail());
        else
            loginActivity.loginResponseText.setText("login fail");
    }

    public static void loginAction(final LoginActivity loginActivity, View view){

        loginActivity.loginUsernameField =(EditText) loginActivity.findViewById(R.id.loginUsernameField);
        loginActivity.loginPasswordField =(EditText) loginActivity.findViewById(R.id.loginPasswordField);
        loginActivity.loginResponseText =(TextView) loginActivity.findViewById(R.id.loginResponseText);
        Editable email= loginActivity.loginUsernameField.getText();
        Editable password= loginActivity.loginPasswordField.getText();

        if(!email.toString().isEmpty()&&!password.toString().isEmpty())
            loginActivity.mAuth.signInWithEmailAndPassword(email.toString(), password.toString())
                    .addOnCompleteListener(loginActivity, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("login", "signInWithEmail:success");
                                FirebaseUser user = loginActivity.mAuth.getCurrentUser();
                                LoginController.updateUI(loginActivity, user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("login", "signInWithEmail:failure", task.getException());
                                Toast.makeText(loginActivity, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                LoginController.updateUI(loginActivity, null);
                            }
                        }
                    });
        else LoginController.updateUI(loginActivity, null);
    }
}
