package com.example.gogo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jbox2d.dynamics.Body;

import java.util.Objects;

public class Logins extends AppCompatActivity {
    private FirebaseAuth auth;

    private EditText loginemail, loginpass, loginname, loginuser;

    private TextView signup;

    public static boolean good;

     FirebaseUser user;

    public static int thing;

    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_logins);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
                loginemail = findViewById(R.id.loginemail);
        loginpass =findViewById(R.id.loginpassword);
        loginname = findViewById(R.id.loginname);
        loginuser =findViewById(R.id.loginuser);
        login = findViewById(R.id.loginbutton);
        signup = findViewById(R.id.signupredirect);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Logins.this, Signups.class);
                startActivity(intent);
            }
        });



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*String user = loginemail.getText().toString().trim();
                String pass = loginpass.getText().toString().trim();
                if (!user.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(user).matches()) {
                    if (!pass.isEmpty()) {
                        auth.signInWithEmailAndPassword(user, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(Logins.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                checker();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Logins.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        loginpass.setError("Password cant be empty");
                    }
                } else if (pass.isEmpty()) {
                    Toast.makeText(Logins.this, "Password Cant be Empty", Toast.LENGTH_SHORT).show();
                } else if (user.isEmpty()) {
                    Toast.makeText(Logins.this, "Email Cant be Empty", Toast.LENGTH_SHORT).show();
                }

                 */

                if(!validuser() || !validpass())
                {

                }
                else {
                    checker();
                }
            }


        });
    }


    public Boolean validuser()
    {
        String v = loginuser.getText().toString();
        if(v.isEmpty())
        {
            Toast.makeText(Logins.this, "Username Cant be Empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

    public Boolean validpass()
    {
        String v = loginpass.getText().toString();
        if(v.isEmpty())
        {
            Toast.makeText(Logins.this, "Password Cant be Empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }


    public void checker()
    {
        String user = loginuser.getText().toString().trim();
        String pass = loginpass.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query check = reference.orderByChild("user").equalTo(user);
        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    loginuser.setError(null);
                    String passfromdb = snapshot.child(user).child("password").getValue(String.class);

                    if(Objects.equals(passfromdb, pass))
                    {
                        loginuser.setError(null);
                        Intent i = new Intent(Logins.this, Profile.class);
                        loginemail = findViewById(R.id.loginemail);
                        loginname = findViewById(R.id.loginname);
                        String users = loginuser.getText().toString().trim();
                        String passs = loginpass.getText().toString().trim();
                        String emails = loginemail.getText().toString().trim();
                        String names = loginname.getText().toString().trim();
                        i.putExtra("user", users);
                        i.putExtra("pass", passs);
                        i.putExtra("email", emails);
                        i.putExtra("names", names);
                        thing++;
                        good = true;
                        Log.d("SHaiking", names + emails);

                        startActivityForResult(i, RESULT_OK);
                    }
                    else {
                        loginpass.setError("Invalid Credentials");
                        loginpass.requestFocus();
                    }
                }
                else {
                    loginemail.setError("user does not exist");
                    loginemail.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    public void setting (boolean b)
    {
        good = b;
    }
    public void settingthing (int b)
    {
        thing = b;
    }

    public boolean getting()
    {
        return good;
    }
    public int gettingthing()
    {
        return thing;
    }
}


