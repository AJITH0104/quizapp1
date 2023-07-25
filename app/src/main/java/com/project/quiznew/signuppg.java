package com.project.quiznew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signuppg extends AppCompatActivity {
    EditText email,pass;
    Button singup;
    ProgressBar progress;
    FirebaseAuth auth=FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signuppg);

        email=findViewById(R.id.useremail);
        pass=findViewById(R.id.userpasswordword);
        singup=findViewById(R.id.s);
        progress=findViewById(R.id.progressBar);
        progress.setVisibility(View.INVISIBLE);

        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    singup.setClickable(false);
                    String usermail=email.getText().toString();
                    String userpass=pass.getText().toString();
                    signupfirebase(usermail,userpass);
            }
        });
    }

    public void signupfirebase(String useremail,String userpassword)

    {
        progress.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(useremail,userpassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(signuppg.this, "Your account is created", Toast.LENGTH_SHORT).show();
                            finish();
                            progress.setVisibility(View.INVISIBLE);
                        }
                        else
                        {
                            Toast.makeText(signuppg.this, "Error Occured,Please try again", Toast.LENGTH_SHORT).show();
                            //check firebase page to check the authentication options
                        }

                    }
                });


    }
}