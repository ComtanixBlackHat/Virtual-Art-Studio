package com.example.virtualartstudio.dashboard;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.virtualartstudio.R;
import com.example.virtualartstudio.firebase.authApi;

public class client extends AppCompatActivity {
    ImageView signOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_client);

        signOutButton = findViewById(R.id.CLIENT_buttonSignOut); // Initialize inside onCreate

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the logout function from the authApi class
                authApi.logout(client.this);

                finish(); // Finish the current activity
            }
        });
    }
}
