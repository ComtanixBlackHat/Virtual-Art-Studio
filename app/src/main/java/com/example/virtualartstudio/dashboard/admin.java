package com.example.virtualartstudio.dashboard;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.virtualartstudio.R;
import com.example.virtualartstudio.firebase.authApi;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class admin extends AppCompatActivity {
    private DatabaseReference usersRef;
    private TableLayout tableLayout;
    private  ImageView signOutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin);


        // Get reference to the table layout
        tableLayout = findViewById(R.id.ADMIN_tableLayout);

        // Set up Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("Users");

        // Add ValueEventListener to fetch data from Firebase
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear existing rows
                tableLayout.removeAllViews();

                // Iterate over dataSnapshot to retrieve data
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    // Get user data
                    String fullName = userSnapshot.child("fullName").getValue(String.class);
                    String email = userSnapshot.child("email").getValue(String.class);
                    String phoneNumber = userSnapshot.child("phoneNumber").getValue(String.class);
                    String role = userSnapshot.child("role").getValue(String.class);

                    // Populate the table with user data
                    addRowToTable(fullName, email, phoneNumber, role);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });


        signOutButton = findViewById(R.id.ADMIN_imageViewSignOut); // Initialize inside onCreate

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the logout function from the authApi class
                authApi.logout(admin.this);

                finish(); // Finish the current activity
            }
        });
    }

    private void addRowToTable(String fullName, String email, String phoneNumber, String role) {
        // Create a new row
        TableRow row = new TableRow(this);

        // Add TextViews for each column
        TextView nameTextView = new TextView(this);
        nameTextView.setText(fullName);
        row.addView(nameTextView);

        TextView emailTextView = new TextView(this);
        emailTextView.setText(email);
        row.addView(emailTextView);

        TextView phoneNumberTextView = new TextView(this);
        phoneNumberTextView.setText(phoneNumber);
        row.addView(phoneNumberTextView);

        TextView roleTextView = new TextView(this);
        roleTextView.setText(role);
        row.addView(roleTextView);

        // Add the row to the table layout
        tableLayout.addView(row);
    }
}
