package com.example.hall_management;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText etemail;
    private Button rstbtn;
    private ProgressBar progressBar;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etemail = (EditText) findViewById(R.id.for_email);
        rstbtn = (Button) findViewById(R.id.for_btn);
        progressBar = (ProgressBar) findViewById(R.id.for_progbar);

        auth = FirebaseAuth.getInstance();
        /**selecting forgot password button**/
        rstbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetpassword();
            }
        });

    }

    private void resetpassword() {
        String email = etemail.getText().toString().trim();

        if (email.isEmpty()){
            etemail.setError("Email required");
            etemail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etemail.setError("Please provide valid email");
            etemail.requestFocus();
            return;
        }

        /**making progress-bar visible to wait a little bit**/
        progressBar.setVisibility(View.VISIBLE);

        /**sending password reset email to user**/

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    //sending password reset email to user
                    Toast.makeText(ForgotPassword.this, "Check your email", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
                else{
                    Toast.makeText(ForgotPassword.this, "Failed...Try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}