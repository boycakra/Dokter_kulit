package com.example.kankerkulit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

//import com.example.tbcapp.R;
//import com.example.kankerkulit.R;
import com.google.firebase.auth.FirebaseAuth;

public class menu extends AppCompatActivity {
    CardView predict_skin, recomendhospital, consulchat, article;
    ImageView map, tombollogout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tampilan_menu);
        getSupportActionBar().hide();

        predict_skin = findViewById(R.id. home1);
        recomendhospital = findViewById(R.id. home2);
        consulchat = findViewById(R.id. home3);
        article = findViewById(R.id. home4);
        map = findViewById(R.id. map);
        tombollogout = findViewById(R.id. tombollogout);

        consulchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(menu.this, MainActivity.class);
                startActivity(intent);
            }
        });
        article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(menu.this, Artikel.class);
                startActivity(intent);
            }
        });
        predict_skin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(menu.this, prediksikanker.class);
                startActivity(intent);
            }
        });
        recomendhospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(menu.this, rumahsakit.class);
                startActivity(intent);
            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(menu.this, rumahsakit.class);
                startActivity(intent);
            }
        });

//        ImageView map, tombollogout;
        tombollogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(menu.this,LoginActivity.class));
            }
        });
    }

}