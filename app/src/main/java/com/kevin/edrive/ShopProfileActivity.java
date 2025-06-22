package com.kevin.edrive;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ShopProfileActivity extends AppCompatActivity {
    EditText etShopName, etOwnerName, etAddress, etContact;
    Button btnSave;
    DatabaseHelper dbHelper;
    private static final int IMAGE_PICK_CODE = 1000;
    ImageView imgProfile;
    Button btnSelectImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopprofile);

        imgProfile = findViewById(R.id.imgProfile);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        etShopName = findViewById(R.id.etShopName);
        etOwnerName = findViewById(R.id.etOwnerName);
        etAddress = findViewById(R.id.etAddress);
        etContact = findViewById(R.id.etContact);
        btnSave = findViewById(R.id.btnSave);

        dbHelper = new DatabaseHelper(this);

        loadShopProfile(); // Load saved data if exists

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shop = etShopName.getText().toString();
                String owner = etOwnerName.getText().toString();
                String address = etAddress.getText().toString();
                String contact = etContact.getText().toString();

                boolean result = dbHelper.upsertShopProfile(shop, owner, address, contact);
                if (result) {
                    Toast.makeText(ShopProfileActivity.this, "Profile saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ShopProfileActivity.this, "Error saving profile", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromGallery();
            }
        });
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    private void loadShopProfile() {
        Cursor cursor = dbHelper.getShopProfile();
        if (cursor != null && cursor.moveToFirst()) {
            etShopName.setText(cursor.getString(cursor.getColumnIndexOrThrow("ShopName")));
            etOwnerName.setText(cursor.getString(cursor.getColumnIndexOrThrow("VendorName")));
            etAddress.setText(cursor.getString(cursor.getColumnIndexOrThrow("Address")));
            etContact.setText(cursor.getString(cursor.getColumnIndexOrThrow("Contact")));
            cursor.close();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE && data != null) {
            Uri imageUri = data.getData();
            imgProfile.setImageURI(imageUri);
        }
    }
}
