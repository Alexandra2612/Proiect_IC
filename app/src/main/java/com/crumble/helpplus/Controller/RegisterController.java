package com.crumble.helpplus.Controller;

import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.crumble.helpplus.R;
import com.crumble.helpplus.View.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class RegisterController {
    
    public static void updateUI(final RegisterActivity registerActivity, FirebaseUser user)
    {
        registerActivity.registerResponseText.setVisibility(View.VISIBLE);
        if(user!=null)
            registerActivity.registerResponseText.setText("registered successfully as " + user.getEmail());
        else
            registerActivity.registerResponseText.setText("register fail");
    }
    public static void registerAction(View view, RegisterActivity registerActivity){
        registerActivity.registerUsernameField =(EditText) registerActivity.findViewById(R.id.registerUsernameField);
        registerActivity.registerPasswordField =(EditText) registerActivity.findViewById(R.id.registerPasswordField);
        registerActivity.registerResponseText =(TextView) registerActivity.findViewById(R.id.registerResponseText);
        Editable email= registerActivity.registerUsernameField.getText();
        Editable password= registerActivity.registerPasswordField.getText();
        if(!email.toString().isEmpty()&&!password.toString().isEmpty())
                registerActivity.mAuth.createUserWithEmailAndPassword(email.toString(), password.toString())
                    .addOnCompleteListener(registerActivity, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("register", "createUserWithEmail:success");
                                FirebaseUser user = registerActivity.mAuth.getCurrentUser();
                                RegisterController.updateUI(registerActivity,user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("register", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(registerActivity, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                RegisterController.updateUI(registerActivity,null);
                            }
                        }
                    });
    }
}
