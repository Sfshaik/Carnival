package com.example.gogo;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Logins logins = new Logins();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomnav);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main,  new Home())
                    .commit();
        }




        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;
                int thing = 0;
                if(item.getItemId() == R.id.home)
                {
                    thing = 1;
                }
                else if(item.getItemId() == R.id.sim)
                {
                    thing = 2;

                }
                else if(item.getItemId() == R.id.chat)
                {
                    thing = 3;

                }
                else if(item.getItemId() == R.id.profile)
                {
                    thing = 4;

                }
                else if(item.getItemId() == R.id.lesson)
                {
                    thing = 5;

                }
                switch (thing)
                {
                    case 1:
                    {
                        fragment = new Home();
                        break;
                    }
                    case 2:
                    {
                        fragment = new Sims();
                        break;
                    }
                    case 3:
                    {
                        fragment = new Chat();
                        break;
                    }
                    case 4:
                    {
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        if (auth.getCurrentUser() != null) {
                            // User is already signed in
                            startActivity(new Intent(MainActivity.this, Profile.class));
                            finish();
                        }
                        else {
                            startActivity(new Intent(MainActivity.this, Signups.class));
                            finish();
                        }

                        break;
                    }
                    case 5:
                    {
                        fragment = new Lessons();
                        break;
                    }
                }


                if(thing != 4)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main, fragment).commit();
                }
                return true;
            }
        });




    }
}
