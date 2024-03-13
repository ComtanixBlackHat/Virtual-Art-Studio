package com.example.virtualartstudio.libs;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.virtualartstudio.dashboard.admin;
import com.example.virtualartstudio.dashboard.artist;
import com.example.virtualartstudio.dashboard.client;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ScreenUtil {


    public static void AuthChangeUI(FirebaseAuth auth, Context context)  {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();

            // Assuming you have a reference to the Firebase Realtime Database
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        // Assuming 'role' is a field in your user data
                        String role = snapshot.child("role").getValue(String.class);

                        // Update UI based on the user's role
                        switch (role) {
                            case "Admin":
                                context.startActivity(new Intent(context , admin.class));
                                // Update UI for admin role
                                break;
                            case "Artist":
                                context.startActivity(new Intent(context , artist.class));
                                // Update UI for artist role
                                break;
                            case "Client":
                                // Update UI for client role
                                context.startActivity(new Intent(context , client.class));
                                break;
                            default:
                                // Handle unknown role
                                break;
                        }
                    } else {
                        // Handle case where user data doesn't exist
                        Log.d("AuthChangeUI", "User data not found");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle database error
                    Log.e("AuthChangeUI", "Database error: " + error.getMessage());
                }
            });
        } else {
            // Handle case where user is not authenticated
            Log.d("AuthChangeUI", "User not authenticated");
        }
    }

}
