package com.crumble.helpplus.Controller;

import android.os.Build;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.crumble.helpplus.R;
import com.crumble.helpplus.View.LoginActivity;
import com.crumble.helpplus.View.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class RegisterController {
    public static boolean checkCredentials(RegisterActivity registerActivity,String email,String pass){
        if(email.isEmpty()||pass.isEmpty()){
            updateUI(registerActivity,"Please fill all fields!");
            return false;
        }
        if(!email.matches(".+@.+\\..{2,}")){
            updateUI(registerActivity,"Illegal email format");
            return false;
        }
        if(pass.length()<8){
            updateUI(registerActivity,"Password must have at least 8 characters");
            return false;
        }
        return true;
    }
    public static void updateUI(final RegisterActivity registerActivity, String message)
    {
        registerActivity.registerResponseText.setVisibility(View.VISIBLE);
            registerActivity.registerResponseText.setText(message);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void registerAction(View view, RegisterActivity registerActivity){
        registerActivity.registerUsernameField =(EditText) registerActivity.findViewById(R.id.registerUsernameField);
        registerActivity.registerPasswordField =(EditText) registerActivity.findViewById(R.id.registerPasswordField);
        registerActivity.registerResponseText =(TextView) registerActivity.findViewById(R.id.registerResponseText);
        Editable email= registerActivity.registerUsernameField.getText();
        Editable password= registerActivity.registerPasswordField.getText();
        if(checkCredentials(registerActivity,email.toString(),password.toString()))
                registerActivity.mAuth.createUserWithEmailAndPassword(email.toString(), LoginController.hashString(password.toString()))
                    .addOnCompleteListener(registerActivity, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("register", "createUserWithEmail:success");
                                FirebaseUser user = registerActivity.mAuth.getCurrentUser();
                                RegisterController.updateUI(registerActivity,"registered successfully as "+user.getEmail());
                                registerActivity.checkLoggedIn();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("register", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(registerActivity, "Registration failed.",
                                        Toast.LENGTH_SHORT).show();
                                RegisterController.updateUI(registerActivity,"connection error");
                            }
                        }
                    });
    }

}
