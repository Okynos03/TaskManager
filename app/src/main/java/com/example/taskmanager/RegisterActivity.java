package com.example.taskmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.taskmanager.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    EditText email, registerPassword, confirmPassword, firtsName, lastName, phone, userNames;
    Spinner redGender;
    Button btnRegister;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userNames = findViewById(R.id.userNames);
        email = findViewById(R.id.email);
        registerPassword = findViewById(R.id.registerPassword);
        confirmPassword = findViewById(R.id.confirmPassword); // Assuming ID exists
        firtsName = findViewById(R.id.firtsName); // Assuming ID exists
        lastName = findViewById(R.id.lastName); // Assuming ID exists
        phone = findViewById(R.id.phone); // Assuming ID exists
        redGender = findViewById(R.id.redGender); // Assuming ID exists
        btnRegister = findViewById(R.id.btnRegister);

        // ...
// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        initSpinnerGender();
        btnRegister.setOnClickListener((v)->registerUser());
    }

    private void registerUser(){
        String emails = email.getText().toString();

        String password = registerPassword.getText().toString();

        String userName = userNames.getText().toString();
        String firts_name = firtsName.getText().toString();
        String last_name = lastName.getText().toString();
        String gender = redGender.getSelectedItem().toString();
        String phoned = phone.getText().toString();

        User newUser = new User();
        newUser.setEmail(emails);
        newUser.setFirts_name(firts_name);
        newUser.setLast_name(last_name);
        newUser.setGender(gender);
        newUser.setPhone(phoned);

        if(!TextUtils.isEmpty(emails) && !TextUtils.isEmpty(password)){
            mAuth.createUserWithEmailAndPassword(emails, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("SUCCESS", "createUserWithEmail:success");

                                FirebaseUser user = mAuth.getCurrentUser();
                                assert  user != null;
                                newUser.setUserId(user.getUid());
                                createUserData(newUser);

                                Toast.makeText(getApplicationContext(), "Authentication success.",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("ERROR", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else{
            Toast.makeText(this, "Pleas provide user name and password", Toast.LENGTH_SHORT).show();
        }

    }

    private void initSpinnerGender() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.arrGenders, android.R.layout.simple_spinner_item);
        redGender.setAdapter(adapter);
    }

    private void createUserData(User newUser){
        DatabaseReference refUser = db.getReference("Users/" + newUser.getUserId());
        refUser.setValue(newUser);
    }
}