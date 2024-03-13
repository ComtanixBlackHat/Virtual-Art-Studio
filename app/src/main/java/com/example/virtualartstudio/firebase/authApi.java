package com.example.virtualartstudio.firebase;

import android.content.Context;
import android.content.Intent;

import com.example.virtualartstudio.auth.login;
import com.google.firebase.auth.FirebaseAuth;

public class authApi {

    public static void logout(Context context) {
        FirebaseAuth.getInstance().signOut();

        // Open the login activity
        Intent intent = new Intent(context, login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
