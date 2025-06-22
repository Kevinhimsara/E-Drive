package com.kevin.edrive;

import android.Manifest;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


    TextView textView05 = findViewById(R.id.textView05);
textView05.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Navigate to UserInfoActivity
            Intent intent = new Intent(SettingsActivity.this, UserInfoActivity.class);
            startActivity(intent);
        }
    });

}
}