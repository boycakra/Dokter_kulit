package com.example.kankerkulit;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Artikel extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artikel);
        getSupportActionBar().hide();
    }
}
