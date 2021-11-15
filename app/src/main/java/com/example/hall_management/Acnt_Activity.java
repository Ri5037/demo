package com.example.hall_management;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Acnt_Activity extends AppCompatActivity {



    Button btn_student,btn_admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acnt);

        btn_student = findViewById(R.id.student_login);
        btn_admin = findViewById(R.id.admin_login);

        /**selecting student-login button**/

        btn_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Acnt_Activity.this, Student_LogIn_Activity.class));

            }
        });

        /**selecting admin-login button**/

        btn_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Acnt_Activity.this,Admin_Activity.class));
            }
        });
    }


}