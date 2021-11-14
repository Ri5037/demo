package com.example.hall_management;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Student_LogIn_Activity extends AppCompatActivity implements View.OnClickListener {

    /**declaring variables**/
    private TextView banner,upload,register,forgot;
    private EditText logemail,logpassword;
    private Button login;
    private FirebaseAuth loginfirebaseAuth;
    private ProgressBar loginprogressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_log_in);

        /**activating necessary components which were created in the activity_student_log_in xml file**/


        login = (Button) findViewById(R.id.login_btn);
        login.setOnClickListener(this);

        logemail = (EditText) findViewById(R.id.login_email);
        logpassword = (EditText) findViewById((R.id.login_password));
        register = findViewById(R.id.Std_register_btn);
        forgot = findViewById(R.id.forgot_pass);

        loginfirebaseAuth = FirebaseAuth.getInstance();

        //banner.setTextColor(Color.YELLOW);
        loginprogressBar = (ProgressBar) findViewById(R.id.login_progbar);

        register.setOnClickListener(this);
        forgot.setOnClickListener(this);



    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Std_register_btn:
                startActivity(new Intent(Student_LogIn_Activity.this, Student_Register_Activity.class));
                break;
            case R.id.login_btn:
                userlogin();
                break;
            case R.id.forgot_pass:
                startActivity(new Intent(Student_LogIn_Activity.this,ForgotPassword.class));
                break;
        }
    }
    /**logging user with firebase email authentication**/
    private void userlogin() {
        String email = logemail.getText().toString().trim();
        String password = logpassword.getText().toString().trim();

        //checking necessary details before login
        if(email.isEmpty()){
            logemail.setError("Email is required");
            logemail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            logemail.setError("Please give a valid email");
            logemail.requestFocus();
            return;
        }
        if (password.isEmpty()){
            logpassword.setError("Password is required");
            logpassword.requestFocus();
            return;
        }
        if(password.length()<6){
            logpassword.setError("Minimum length is 6");
            logpassword.requestFocus();
            return;
        }
        /**showing progressbar**/
        loginprogressBar.setVisibility(View.VISIBLE);

        /**checking firebase authentication**/
        loginfirebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if (user.isEmailVerified()) {
                        /**staring new activity if user is logged in && his/her email verified**/
                        startActivity(new Intent(Student_LogIn_Activity.this, StudentProfileActivity.class));

                    } else {
                        /**after regestering, app will send a email to user verify his/her email**/
                        user.sendEmailVerification();
                        Toast.makeText(Student_LogIn_Activity.this, "Verification Email Sent", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    /**if login failed user didn't give correct credentials**/
                    Toast.makeText(Student_LogIn_Activity.this, "Failed...Please Give Correct Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
