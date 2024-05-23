package com.example.taskmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskmanager.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    TextView lblRegister;
    EditText LoginUserName, LoginPassword;
    Button btnLogin;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase db = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        lblRegister = findViewById(R.id.lblRegister);
        LoginUserName = findViewById(R.id.LoginUsername);
        LoginPassword = findViewById(R.id.LoginPassword);
        btnLogin = findViewById(R.id.btnLogin);

        lblRegister.setOnClickListener((v)->{//Al dar click a la etiqueta abre la activity register
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener((v) ->{
            String email = LoginUserName.getText().toString();
            String password = LoginPassword.getText().toString();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("SUCCESS", "signInWithEmail:success");
                                Toast.makeText(LoginActivity.this, "Login success...",
                                        Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                redirectToHome(user.getUid());
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("ERROR", "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                        }
                    });
        });

    }

    private void redirectToHome(String uid){
        DatabaseReference loggedUser = db.getReference("Users/"+ uid);
        loggedUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                //intent.putExtra("full_name", userData.getFirts_name() + " " +
                        //userData.getLast_name());

                intent.putExtra("user", user);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            //startActivity(new Intent(this, HomeActivity.class));
            redirectToHome(currentUser.getUid());

        }
    }
}