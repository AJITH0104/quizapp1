package com.project.quiznew;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

public class loginpg extends AppCompatActivity {

    Button sign;
    SignInButton gsign;
    EditText email,pass;
    TextView signup,forgot;
    ProgressBar progressBar;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    GoogleSignInClient googleSignInClient;

    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpg);
        email=findViewById(R.id.loginmail);
        pass=findViewById(R.id.password);
        sign=findViewById(R.id.signin);
        gsign=findViewById(R.id.gsign);
        signup=findViewById(R.id.createacc);
        forgot=findViewById(R.id.forgot);
        progressBar=findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.INVISIBLE);

        //register activity launcher

        registeractivityforsignin();



        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usermail=email.getText().toString();
                String userpass=pass.getText().toString();
                signinwithfirebase(usermail,userpass);

            }
        });
        gsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signingoogle();

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(loginpg.this,signuppg.class);
                startActivity(i);

            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(loginpg.this,forgot.class);
                startActivity(i);
            }
        });
    }
    public void signinwithfirebase(String useremail,String userpass)
    {
        progressBar.setVisibility(View.VISIBLE);
        sign.setClickable(false);

        auth.signInWithEmailAndPassword(useremail,userpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Intent i=new Intent(loginpg.this,MainActivity.class);
                    startActivity(i);
                    progressBar.setVisibility(View.INVISIBLE);
                    finish();
                    Toast.makeText(loginpg.this, "Signin was Sucessfull", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(loginpg.this, "Signin was Not Sucessfull!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    public void signingoogle()
    {
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken("786409207684-f94h733aj1nbm60837l716ktbj888lut.apps.googleusercontent.com" )
                .requestEmail().build();

        googleSignInClient= GoogleSignIn.getClient(this,gso);
        signing();
    }
    public void signing()
    {
        Intent signinintent=googleSignInClient.getSignInIntent();
        activityResultLauncher.launch(signinintent);
    }
    public void registeractivityforsignin()
    {
            activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    int resultcode=result.getResultCode();
                    Intent data=result.getData();

                    if(resultcode== RESULT_OK && data!=null)
                    {
                        Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
                        firebasesiginwithgoogle(task);
                    }
                }
            });
    }

    private void firebasesiginwithgoogle(Task<GoogleSignInAccount> task)
    {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            Toast.makeText(this, "Successfully", Toast.LENGTH_SHORT).show();
       Intent i=new Intent(loginpg.this,MainActivity.class);
       startActivity(i);
       finish();
       firebasegoogleacc(account);
        }
        catch (ApiException e) {
            e.printStackTrace();
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }
    private  void firebasegoogleacc(GoogleSignInAccount account)
    {
        AuthCredential authCredential= GoogleAuthProvider.getCredential(account.getIdToken(),null);
        auth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    //FirebaseUser user=auth.getCurrentUser();

                }
                else {

                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=auth.getCurrentUser();
        if(user!=null)
        {
            Intent i=new Intent(loginpg.this,MainActivity.class);
            startActivity(i);
            progressBar.setVisibility(View.INVISIBLE);
            finish();
            Toast.makeText(loginpg.this, "Signin was Sucessfull", Toast.LENGTH_SHORT).show();
        }

    }

}