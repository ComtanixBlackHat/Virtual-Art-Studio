package com.example.virtualartstudio.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.virtualartstudio.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class login extends AppCompatActivity {
    private TextView textViewCreateNewAccount;
    private Button buttonLoginEmailPassword;
    private FirebaseAuth auth;
    private GoogleSignInClient googleSignInClient;

    private  String TAG = "VIRTUALARTSTUDIO_LOGIN";
    private EditText editTextEmail, editTextPassword;
    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                    try {
                        GoogleSignInAccount signInAccount = accountTask.getResult(ApiException.class);
                        AuthCredential authCredential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
                        auth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // User signed in successfully
                                    Toast.makeText(login.this, "Signed in successfully!", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Sign in failed
                                    Toast.makeText(login.this, "Failed to sign in: " + task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } catch (ApiException e) {
                        e.printStackTrace();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textViewCreateNewAccount = findViewById(R.id.LOGIN_createAccountTextView);
        SignInButton signInButton = findViewById(R.id.LOGIN_googleLoginButton);
        buttonLoginEmailPassword = findViewById(R.id.LOGIN_loginButton);

        editTextEmail = findViewById(R.id.LOGIN_editTextEmail);
        editTextPassword = findViewById(R.id.LOGIN_editTextPassword);

        // Initialize FirebaseAuth instance
        auth = FirebaseAuth.getInstance();

        // Set click listener for "Create New Account" TextView
        textViewCreateNewAccount.setOnClickListener(view -> {
            Intent registerIntent = new Intent(login.this, register.class);
            startActivity(registerIntent);
        });

        // Set click listener for Google Sign-In button
        signInButton.setOnClickListener(view -> {
            Intent intent = googleSignInClient.getSignInIntent();
            activityResultLauncher.launch(intent);
        });

        buttonLoginEmailPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if email and password fields are not empty
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(login.this, "Email and password cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Sign in with email and password
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success
                                    Toast.makeText(login.this, "Signed in successfully!", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Sign in failed
                                    Log.e(TAG , task.getException().toString());
                                    Toast.makeText(login.this, "Authentication failed: " + task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        // Check if user is already logged in
        if (auth.getCurrentUser() != null) {
            // User is already logged in

            Toast.makeText(login.this, "User is already logged in", Toast.LENGTH_SHORT).show();
        }
    }
}