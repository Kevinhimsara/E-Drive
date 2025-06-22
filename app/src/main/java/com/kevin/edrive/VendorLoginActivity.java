package com.kevin.edrive;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class VendorLoginActivity extends AppCompatActivity {

    Button btn03, btn04;
    private EditText uName01, pw01;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendorlogin); // Connects XML layout to this activity

        uName01 = findViewById(R.id.uName01);
        pw01 = findViewById(R.id.pw01);
        btn03 = findViewById(R.id.btn03); // login button
        btn04 = findViewById(R.id.btn04); // signup button

        dbHelper = new DatabaseHelper(this);

        btn03.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onLoginClick(view);
            }
        });

        btn04.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to VendorSignupActivity
                Intent intent = new Intent(VendorLoginActivity.this, VendorSignupActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onLoginClick(View view) {
        String username = uName01.getText().toString().trim();
        String password = pw01.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isValid = dbHelper.checkUser(username, password);
        if (isValid) {
            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(VendorLoginActivity.this, VendorHomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
        }
    }
}
