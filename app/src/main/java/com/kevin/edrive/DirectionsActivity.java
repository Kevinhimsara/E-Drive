package com.kevin.edrive;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DirectionsActivity extends AppCompatActivity {
    private Button btnGmap;
    private EditText etStart, etEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);

        btnGmap = findViewById(R.id.btnGmap);
        etStart = findViewById(R.id.etStart);
        etEnd = findViewById(R.id.etEnd);

        btnGmap.setOnClickListener(v -> {
                    String start = etStart.getText().toString();
                    String end = etEnd.getText().toString();

                    if (!start.isEmpty() && !end.isEmpty()) {
                        String url = "https://www.google.com/maps/dir/" + start + "/" + end;
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        intent.setPackage("com.google.android.apps.maps");
                        startActivity(intent);
                    } else {
                        etStart.setError("Enter start location");
                        etEnd.setError("Enter end location");
                    }
                }
        );
    }
        private void getDirections(String etStart,String etEnd) {
            try {
                String url = "https://www.google.com/maps/dir/" + etStart + "/" + etEnd;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.setPackage("com.google.android.apps.maps");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            } catch (Exception e) {
                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }