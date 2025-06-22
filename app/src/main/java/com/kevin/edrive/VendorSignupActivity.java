package com.kevin.edrive;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class VendorSignupActivity extends AppCompatActivity {

    EditText ShopName, VendorName, Password, confirmPassword;
    Button btnRegister;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendorsignup);

        // Initialize views
        ShopName = findViewById(R.id.sName01);
        VendorName = findViewById(R.id.uName02);
        Password = findViewById(R.id.pw02);
        confirmPassword = findViewById(R.id.pw03);
        btnRegister = findViewById(R.id.btn05);

        // Initialize DB helper
        dbHelper = new DatabaseHelper(this);

        // Button click listener
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shop = ShopName.getText().toString();
                String name = VendorName.getText().toString();
                String pass = Password.getText().toString();
                String confirm = confirmPassword.getText().toString();

                if (shop.isEmpty() || name.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
                    Toast.makeText(VendorSignupActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!pass.equals(confirm)) {
                    Toast.makeText(VendorSignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean success = dbHelper.insertVendorSingUp(shop, name, pass);
                if (success) {
                    Toast.makeText(VendorSignupActivity.this, "Vendor Registered!", Toast.LENGTH_SHORT).show();

                    ShopName.setText("");
                    VendorName.setText("");
                    Password.setText("");
                    confirmPassword.setText("");

                    Intent intent = new Intent(VendorSignupActivity.this, ShopProfileActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(VendorSignupActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
