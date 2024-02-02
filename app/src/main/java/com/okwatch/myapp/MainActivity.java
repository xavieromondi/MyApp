package com.okwatch.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
TextView textViewlogin;
EditText editTextFullName,editTextEmail,editTextPassword,e;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    user user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewlogin = findViewById(R.id.textViewLogin);

        editTextFullName = findViewById(R.id.editTextFullName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        Button btn = findViewById(R.id.buttonSignup);

        // Write a message to the database
         firebaseDatabase = FirebaseDatabase.getInstance();
         databaseReference = firebaseDatabase.getReference("User Info");
         user = new user();

        //myRef.setValue("Hello, World!");


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = editTextFullName.getText().toString();
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if(fullName.isEmpty()||email.isEmpty()||password.isEmpty()){
                    Toast.makeText(getApplicationContext(),"ALL FIELDS MUST BE FILLED",Toast.LENGTH_LONG).show();
                }else {
                   addNewUser(fullName,email,password);

                }
            }
        });

        textViewlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRegister = new Intent(MainActivity.this, login.class);
                startActivity(intentRegister);
            }
        });
    }

    public void addNewUser(String fullName,String email,String password){
       user.setFullName(fullName);
       user.setEmail(email);
       user.setPassword(password);

       databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               databaseReference.setValue(user);
               Toast.makeText(getApplicationContext(),"User registered sucessfully", Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
Toast.makeText(getApplicationContext(),"Error User registration failed",Toast.LENGTH_SHORT).show();
           }
       });
    }
}