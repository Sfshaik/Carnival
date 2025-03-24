package com.example.gogo;

import static android.app.PendingIntent.getActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signups extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseUser user;

    FirebaseDatabase database;
    DatabaseReference reference;
    private EditText singupemail, signuppass, signupname, signupuser;

    private Button signup;

    private TextView login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signups);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.signup), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");
        user = auth.getCurrentUser();
        singupemail = findViewById(R.id.signupemail);
        signuppass = findViewById(R.id.signuppassword);
        signupuser = findViewById(R.id.signupuser);
        signupname = findViewById(R.id.signupname);
        signup = findViewById(R.id.signupbutton);
        login = findViewById(R.id.loginredirect);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = signupuser.getText().toString().trim();
                String pass = signuppass.getText().toString().trim();
                String email = singupemail.getText().toString().trim();
                String name = signupname.getText().toString().trim();


                Helper helper = new Helper(email, name, user, pass);
                reference.child(user).setValue(helper);

                Intent i = new Intent(Signups.this, Logins.class);
                startActivity(i);


            /*    if(user.isEmpty())
                {
                    Toast.makeText(Signups.this, "Email Cant be Empty", Toast.LENGTH_SHORT).show();
                }
                if(pass.isEmpty())
                {
                    Toast.makeText(Signups.this, "Password Cant be Empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    auth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(Signups.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Signups.this, Profile.class);
                                startActivity(i);
                            }
                            else {
                                Toast.makeText(Signups.this, "Signup Failed", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }

             */

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signups.this, Logins.class);
                startActivity(intent);
            }

        });

}
}


















