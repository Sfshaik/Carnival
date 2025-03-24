package com.example.gogo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {
    TextView name, user, email, pass;
    Button logout;
    ImageView main, leftone, lefttwo, leftthree, leftfour, rightone, righttwo, rightthree, rightfour;

    Logins logins = new Logins();
    FirebaseUser users;
    FirebaseAuth auth;

    FloatingActionButton goback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mains), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        name = findViewById(R.id.profilename);
        user = findViewById(R.id.profileuser);
        email = findViewById(R.id.profileemail);
        pass = findViewById(R.id.profilepass);

        logout = findViewById(R.id.logout);

        main = findViewById(R.id.profilepic);

        main.setImageResource(R.drawable.carnival);
        leftone = findViewById(R.id.leftone);
        lefttwo = findViewById(R.id.lefttwo);
        leftthree = findViewById(R.id.leftthree);
        leftfour = findViewById(R.id.leftfour);


        rightone = findViewById(R.id.rightone);
        righttwo = findViewById(R.id.righttwo);
        rightthree = findViewById(R.id.rightthree);
        rightfour = findViewById(R.id.rightfour);

        auth = FirebaseAuth.getInstance();

        leftone.setImageResource(R.drawable.cart);
        lefttwo.setImageResource(R.drawable.clown);
        leftthree.setImageResource(R.drawable.ticket);
        leftfour.setImageResource(R.drawable.wheel);
        rightone.setImageResource(R.drawable.mask);
        righttwo.setImageResource(R.drawable.ride);
        rightthree.setImageResource(R.drawable.horse);
        rightfour.setImageResource(R.drawable.sign);

        users = auth.getCurrentUser();

if(users != null)
{
    String emailed = users.getEmail();
    String uided = users.getUid();
    String displayNameed = users.getDisplayName();


    name.setText(displayNameed);
    user.setText(uided);
    email.setText(emailed);




}
        name.setText(getIntent().getStringExtra("names"));
        user.setText(getIntent().getStringExtra("user"));
        email.setText(getIntent().getStringExtra("email"));
        pass.setText(getIntent().getStringExtra("pass"));

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Profile.this, MainActivity.class);
                startActivity(i);
                FirebaseAuth.getInstance().signOut();
            }
        });


        leftone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.setImageResource(R.drawable.cart);
            }
        });

        lefttwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.setImageResource(R.drawable.clown);
            }
        });

        leftthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.setImageResource(R.drawable.ticket);
            }
        });

        leftfour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.setImageResource(R.drawable.wheel);
            }
        });

        rightone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.setImageResource(R.drawable.mask);
            }
        });

        righttwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.setImageResource(R.drawable.ride);
            }
        });


        rightthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.setImageResource(R.drawable.horse);
            }
        });


        rightfour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.setImageResource(R.drawable.sign);
            }
        });

        goback = findViewById(R.id.goback);


        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Profile.this, MainActivity.class);
                startActivity(i);
            }
        });

    }
}



