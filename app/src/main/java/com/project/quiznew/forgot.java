package com.project.quiznew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgot extends AppCompatActivity {

    EditText email;
    Button forgt;
    ProgressBar progress;
    FirebaseAuth auth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        email=findViewById(R.id.forgotAddress);
        forgt=findViewById(R.id.cont);
        progress=findViewById(R.id.pb);
        progress.setVisibility(View.INVISIBLE);

        forgt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String umail=email.getText().toString();
                reset(umail);

            }
        });
    }
    public void reset(String useremail)
    {
        progress.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(useremail).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(forgot.this, "Sent a password reset link to your email address", Toast.LENGTH_SHORT).show();
                    forgt.setClickable(false);
                    progress.setVisibility(View.INVISIBLE);
                    Intent i=new Intent(forgot.this,loginpg.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(forgot.this, "Sorry we have a problem Please try again later", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}