package com.example.virtualartstudio.dashboard;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.virtualartstudio.R;
import com.example.virtualartstudio.firebase.authApi;

public class artist extends AppCompatActivity {
    private ImageView signOutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_artist);


        signOutButton = findViewById(R.id.ARTIST_imageViewSignOut); // Initialize inside onCreate

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the logout function from the authApi class
                authApi.logout(artist.this);

                finish(); // Finish the current activity
            }
        });
    }
}