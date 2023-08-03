package com.example.kankerkulit;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kankerkulit.Model.Artikel_konten;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class Artikel extends AppCompatActivity {
    private RecyclerView artikel;
    private DatabaseReference mdatabase;
    private FirebaseRecyclerAdapter<Artikel_konten, kontencyleholder> firebaseRecyclerAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artikel);
        getSupportActionBar().hide();

        mdatabase = FirebaseDatabase.getInstance("https://ml-skin-d2633-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Konten");
        mdatabase.keepSynced(true);
        mdatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    Log.d("firebase", String.valueOf(task.getResult()));
                }
            }
        });

        artikel = findViewById(R.id.artikelcyle);
        artikel.setHasFixedSize(true);
        artikel.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Artikel_konten> options =
                new FirebaseRecyclerOptions.Builder<Artikel_konten>()
                        .setQuery(mdatabase, Artikel_konten.class)
                        .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Artikel_konten, Artikel.kontencyleholder>
                (options){

            @Override
            public Artikel.kontencyleholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = getLayoutInflater().inflate(R.layout.artikel_cyle, parent, false);
                return new Artikel.kontencyleholder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull Artikel.kontencyleholder viewHolder, int position, @NonNull Artikel_konten model) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), Konten_Artikel.class);
                        intent.putExtra("judul", model.getJudul());
                        intent.putExtra("gambarkonten", model.getFotokonten());
                        intent.putExtra("Isikonten", model.getIsikonten());

                        v.getContext().startActivity(intent);
                    }
                });
                viewHolder.setJudul(model.getJudul());
                viewHolder.setIsikonten(model.getIsikonten());
                viewHolder.setFotokonten(getApplication(), model.getFotokonten());

            }
        };
        artikel.setAdapter(firebaseRecyclerAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseRecyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseRecyclerAdapter.stopListening();
    }

    public static class kontencyleholder extends RecyclerView.ViewHolder {
        View mView;

        public kontencyleholder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setFotokonten(Application ctx, String fotokonten) {
            ImageView post_fotokonten = mView.findViewById(R.id.mainpic);
            Picasso.get().load(fotokonten).into(post_fotokonten);
        }

        public void setJudul(String judul) {
            TextView post_judul= mView.findViewById(R.id.judul_konten);
            post_judul.setText(judul);
        }


        public void setIsikonten(String isikonten) {
            TextView post_isikonten = mView.findViewById(R.id.deskrip_konten);
            post_isikonten.setText((isikonten));
        }

    }

}
