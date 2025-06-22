package com.kevin.edrive;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ChargingStationsActivity extends AppCompatActivity {

    TextView tvid01, tvid02, tvid03;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chargingstations); // Connects XML layout to this activity

        tvid01 = findViewById(R.id.tvid01);
        tvid02 = findViewById(R.id.tvid02);
        tvid03 = findViewById(R.id.tvid03);


        tvid01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChargingStationsActivity.this, UserHomeActivity.class);
                intent.putExtra("latitude", 6.905169215465542);
                intent.putExtra("longitude", 79.85832986652439);
                intent.putExtra("title", "Keells Kollupitiya");
                startActivity(intent);
            }
        });

        tvid02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChargingStationsActivity.this, UserHomeActivity.class);
                intent.putExtra("latitude", 6.921905704582723);
                intent.putExtra("longitude", 79.85618866837551);
                intent.putExtra("title", "Keells Union Place");
                startActivity(intent);
            }
        });
       tvid03.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(ChargingStationsActivity.this, UserHomeActivity.class);
               intent.putExtra("latitude", 6.912087448941932);
               intent.putExtra("longitude", 79.85570451565978);
               intent.putExtra("title", "E Shift Kollupitiya");
               startActivity(intent);
           }
       });

    }
}