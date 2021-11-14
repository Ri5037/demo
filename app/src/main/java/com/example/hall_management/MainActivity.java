package com.example.hall_management;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**creating splash screen at the opening of the app**/
       Handler handler = new Handler();
       handler.postDelayed(new Runnable() {
           @Override
           public void run() {
               startActivity(new Intent(MainActivity.this, Acnt_Activity.class));
               finish();
           }
       },3000);
    }
}