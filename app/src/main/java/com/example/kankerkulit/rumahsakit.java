package com.example.kankerkulit;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kankerkulit.Model.Rumahsakitm;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class rumahsakit extends AppCompatActivity {

    private RecyclerView rumahsakitlist;
    private DatabaseReference mdatabase;
    private FirebaseRecyclerAdapter<Rumahsakitm, divesiteholder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rumahsakit);
        getSupportActionBar().hide();

        mdatabase = FirebaseDatabase.getInstance("https://ml-skin-d2633-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("rumahsakit");
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

        rumahsakitlist = findViewById(R.id.rumahsakit_cyle);
        rumahsakitlist.setHasFixedSize(true);
        rumahsakitlist.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Rumahsakitm> options =
                new FirebaseRecyclerOptions.Builder<Rumahsakitm>()
                        .setQuery(mdatabase, Rumahsakitm.class)
                        .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Rumahsakitm, divesiteholder>
                (options){

            @Override
            public divesiteholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = getLayoutInflater().inflate(R.layout.rumahsakit_cyle, parent, false);
                return new divesiteholder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull divesiteholder viewHolder, int position, @NonNull Rumahsakitm model) {
               /* viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), rumahSakitInfo.class);
                        intent.putExtra("label", model.getLabeltempat());
                        intent.putExtra("alamat", model.getAlamat());
                        intent.putExtra("namatempat", model.getNamatempat());
                        intent.putExtra("gambartempat", model.getPictempat());


                        v.getContext().startActivity(intent);
                    }
                });*/
                viewHolder.setAlamat(model.getAlamat());
                viewHolder.setLabeltempat(model.getLabeltempat());
                viewHolder.setpictempat(getApplication(), model.getPictempat());
                viewHolder.setNamatempat(model.getNamatempat());
                viewHolder.setNomer(model.getNomer());

            }
        };
        rumahsakitlist.setAdapter(firebaseRecyclerAdapter);

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

    public static class divesiteholder extends RecyclerView.ViewHolder {
        View mView;

        public divesiteholder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setpictempat(Application ctx, String pictempat) {
            ImageView post_pictempat = mView.findViewById(R.id.pic_rs);
            Picasso.get().load(pictempat).into(post_pictempat);
        }

        public void setLabeltempat(String labeltempat) {
            TextView post_labeltempat = mView.findViewById(R.id.tempatlbl);
            post_labeltempat.setText(labeltempat);
        }

        public void setNamatempat(String namatempat) {
            TextView post_namatempat = mView.findViewById(R.id.tempatsite);
            post_namatempat.setText(namatempat);
        }

        public void setAlamat(String alamat) {
            TextView post_deskripsi = mView.findViewById(R.id.alamat);
            post_deskripsi.setText((alamat));
        }
        public void setNomer(String nomer) {
            TextView post_nomer = mView.findViewById(R.id.nomer_rs);
            post_nomer.setText((nomer));
        }
    }
}