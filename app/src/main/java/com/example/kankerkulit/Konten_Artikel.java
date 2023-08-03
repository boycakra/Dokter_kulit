package com.example.kankerkulit;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Konten_Artikel extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artikel_konten);
        String judul= getIntent().getStringExtra("judul");

        String gambarkonten= getIntent().getStringExtra("gambarkonten");
        String Deskripsi= getIntent().getStringExtra("Isikonten");

        ImageView post_konten=(ImageView)findViewById(R.id.gambarkonten);
        Picasso.get().load(gambarkonten).into(post_konten);


        TextView post_judul=(TextView)findViewById(R.id.Judulkontenisi);
        post_judul.setText(judul);

        TextView post_deskripsi=(TextView)findViewById(R.id.Kontenisi);
        post_deskripsi.setText(Deskripsi);



    }
}