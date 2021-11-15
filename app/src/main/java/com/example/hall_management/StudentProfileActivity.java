package com.example.hall_management;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class StudentProfileActivity extends AppCompatActivity {

    /**typecast**/
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    androidx.appcompat.widget.Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        navigationView = (NavigationView)findViewById(R.id.navmenu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        /**adding drawer layout **/
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar, R.string.open, R.string.close);

        /**listenning drawerlayout**/
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        /**starting navigation drawer**/
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    /**getting menu item**/
                    case R.id.menu_info:
                        Toast.makeText(StudentProfileActivity.this, "Info Tab Opened", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(StudentProfileActivity.this,AdminInfoShow.class));
                        /**closing navigation drawer**/

                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.menu_complain:
                        Toast.makeText(StudentProfileActivity.this, "Complain Tab Opened", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(StudentProfileActivity.this,FireBasePushNoti.class));
                        /**closing navigation drawer**/
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.menu_sust:
                        Toast.makeText(StudentProfileActivity.this, "SUST Tab Opened", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(StudentProfileActivity.this,WebView_Sust.class));
                        /**closing navigation drawer**/
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;



                }

                return true;
            }
        });


    }
}