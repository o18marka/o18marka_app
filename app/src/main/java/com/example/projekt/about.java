package com.example.projekt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class about extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        Intent intent = getIntent(); // Här
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE); //Här

        TextView textView = findViewById(R.id.aboutText); // Här
        textView.setText(message);
    }
}
