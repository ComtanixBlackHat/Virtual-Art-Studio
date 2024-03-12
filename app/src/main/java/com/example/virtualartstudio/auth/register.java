package com.example.virtualartstudio.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.virtualartstudio.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {
    private EditText editTextFullName, editTextEmail, editTextPhoneNumber, editTextPassword, editTextConfirmPassword;
    private Button registerButton;
    private TextView loginTextView;
    private Spinner rolespinner;
    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextFullName = findViewById(R.id.editTextFullName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);

        registerButton = findViewById(R.id.registerButton);

        loginTextView = findViewById(R.id.loginTextView);


        rolespinner= findViewById(R.id.SpinnerConfirmPassword);
        populateSpinner(this, rolespinner);

        mAuth = FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");

        registerButton.setOnClickListener(view -> registerUser());

        loginTextView.setOnClickListener(view -> {
           startActivity(new Intent(this , login.class));
        });
    }

    private void registerUser() {
        String fullName = editTextFullName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();
        String role = rolespinner.getSelectedItem().toString();

        if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phoneNumber) ||
                TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Registration successful, save user details to Firebase Realtime Database
                        String userId = mAuth.getCurrentUser().getUid();
                        DatabaseReference currentUserRef = usersRef.child(userId);
                        currentUserRef.child("fullName").setValue(fullName);
                        currentUserRef.child("email").setValue(email);
                        currentUserRef.child("phoneNumber").setValue(phoneNumber);
                        currentUserRef.child("role").setValue(role)
                                .addOnCompleteListener(databaseTask -> {
                                    if (databaseTask.isSuccessful()) {
                                        // Database operation successful
                                        Toast.makeText(register.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Database operation failed
                                        Toast.makeText(register.this, "Database operation failed: " + databaseTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                        // Optionally, you can redirect the user to the login activity after successful registration
                        // Intent loginIntent = new Intent(register.this, login.class);
                        // startActivity(loginIntent);
                    } else {
                        // Registration failed
                        Toast.makeText(register.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        }
    private void populateSpinner(Context context , Spinner spinner) {
        String[] values = new String[]{"Admin", "Artist", "Client"};
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, values);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }
}