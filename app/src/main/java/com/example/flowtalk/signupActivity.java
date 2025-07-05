package com.example.flowtalk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.flowtalk.Models.users;
import com.example.flowtalk.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class signupActivity extends AppCompatActivity {
    ActivitySignupBinding binding;
    private FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(signupActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("creating");

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();


                auth.createUserWithEmailAndPassword(binding.editTextTextEmailAddress.getText().toString(),
                        binding.editTextTextPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            users user = new users(binding.editTextText.getText().toString(), binding.editTextTextEmailAddress.getText().toString(), binding.editTextTextPassword.getText().toString());

                            String id = task.getResult().getUser().getUid();

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            database.getReference().child("users").child(id).setValue(user);

                            Toast.makeText(signupActivity.this, "User Created Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(signupActivity.this, task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });

        binding.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(signupActivity.this, SignInActivity.class);
                startActivity(intent1);
            }
        });

    }
}