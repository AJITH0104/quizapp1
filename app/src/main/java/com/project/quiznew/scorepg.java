package com.project.quiznew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class scorepg extends AppCompatActivity {

    TextView scorecorrect,scorewrong;
    Button playagain,exit;

    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference ref=database.getReference().child("scores");

    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseUser user=auth.getCurrentUser();
    String usercorrect,userwrong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorepg);

        scorecorrect=findViewById(R.id.correctvalue);
        scorewrong=findViewById(R.id.wrongvalue);
        playagain=findViewById(R.id.AGAIN);
        exit=findViewById(R.id.exit);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String useruid=user.getUid();
                usercorrect=snapshot.child(useruid).child("correct").getValue().toString();
                userwrong=snapshot.child(useruid).child("wrong").getValue().toString();
                scorecorrect.setText(""+usercorrect);
                scorewrong.setText(""+userwrong);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        playagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(scorepg.this,MainActivity.class);
                startActivity(i);
                finish();

            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}