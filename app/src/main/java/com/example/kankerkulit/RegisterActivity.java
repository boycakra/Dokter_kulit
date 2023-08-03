package com.example.kankerkulit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {


    EditText userET, passET,emailET;
    Spinner professionSpinner;
    Button registerBtn;
    FirebaseAuth auth;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();


        userET=findViewById(R.id.userEditText);
        passET=findViewById(R.id.passwordEditText);
        emailET=findViewById(R.id.emailEditText);
        registerBtn=findViewById(R.id.buttonRegister);
        professionSpinner = findViewById(R.id.profesiSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.professions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        professionSpinner.setAdapter(adapter);


        auth=FirebaseAuth.getInstance();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username_text= userET.getText().toString();
                String email_text= emailET.getText().toString();
                String password_text= passET.getText().toString();
                String profesi_text= professionSpinner.getSelectedItem().toString();

                if (TextUtils.isEmpty(username_text)||TextUtils.isEmpty(email_text)||TextUtils.isEmpty(password_text) ||TextUtils.isEmpty(profesi_text)){
                    Toast.makeText(RegisterActivity.this, "Jangan Lupa tambah ", Toast.LENGTH_SHORT).show();
                }
                else{
                    RegisterNow(username_text,email_text,password_text,profesi_text);
                }



            }
        });



    }
    private void RegisterNow(final String username,String email,String password, String profesi){
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser=auth.getCurrentUser();
                            String userid=firebaseUser.getUid();

                            myRef = FirebaseDatabase.getInstance("https://ml-skin-d2633-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("MyUsers")
                                    .child(userid);

                            HashMap<String, String>hashMap=new HashMap<>();
                            hashMap.put("id",userid);
                            hashMap.put("username",username);
                            hashMap.put("imageURL","default");
                            hashMap.put("Password",password);
                            hashMap.put("profesi",profesi);
                            hashMap.put("Email",email);


                            myRef.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Intent i=new Intent(RegisterActivity.this,MainActivity.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(i);
                                        finish();
                                    }
                                }
                            });


                        }else{
                            Toast.makeText(RegisterActivity.this, "Tidak masuk", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}