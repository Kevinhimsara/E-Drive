package com.kevin.edrive;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class VendorHomeActivity extends AppCompatActivity {

    ImageView img07;
    Button btnVenH01, btnVenH02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendorhome); // Connects XML layout to this activity

        RadioGroup genderGroup = findViewById(R.id.lstat);
        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selected = findViewById(checkedId);
                Toast.makeText(getApplicationContext(), "Selected: " + selected.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        img07 = findViewById(R.id.img07);
        img07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to SettingsActivity
                Intent intent = new Intent(VendorHomeActivity.this, ShopProfileActivity.class);
                startActivity(intent);
            }
        });

        btnVenH01 = findViewById(R.id.btnVenH01);
        btnVenH01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to ReviewsActivity
                Intent intent = new Intent(VendorHomeActivity.this, ReviewsActivity.class);
                startActivity(intent);

            }
        });
        btnVenH02 = findViewById(R.id.btnVenH02);
        btnVenH02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to AppointmentsActivity
                Intent intent = new Intent(VendorHomeActivity.this, AppointmentsActivity.class);
                startActivity(intent);
            }
        });
    }
}
