package com.example.virtualartstudio;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.virtualartstudio.auth.login;
import com.example.virtualartstudio.libs.CONSTANTS;

public class splash extends AppCompatActivity {
    private static String TAG = "SPLASHSCREEN_";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        openNewActivityWithDelay(this, login.class, 3000);
    }
    private void openNewActivityWithDelay(Context context, Class<?> cls, long delayMillis) {
        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Start the new activity here
                    startActivity(new Intent(context, cls));
                    // Finish the current activity if needed
                    finish();
                }
            }, delayMillis);
        } catch (Exception e) {
            Log.e(TAG+"Thread" , e.toString());
        }
    }
}