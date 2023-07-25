package com.project.quiznew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class quizpg extends AppCompatActivity {

    CountDownTimer countDownTimer;
    private static final long totaltime=25000;
    boolean timercont;
    long timeleft=totaltime;
    TextView time,correct,wrong,que,a,b,c,d;
    Button nxt,finish;

    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference ref=database.getReference().child("QUESTIONS");

    String question,answer,optiona,optionb,optionc,optiond,userans;
    int qcount,qnum=1,correctans=0,incorrectans=0;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseUser user=auth.getCurrentUser();
    DatabaseReference refs=database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizpg);
        time=findViewById(R.id.COUNT);
        correct=findViewById(R.id.correct);
        wrong=findViewById(R.id.WRONG);
        que=findViewById(R.id.que);
        a=findViewById(R.id.a);
        b=findViewById(R.id.b);
        c=findViewById(R.id.c);
        d=findViewById(R.id.d);
        nxt=findViewById(R.id.nxt);
        finish=findViewById(R.id.finish);
        game();

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pausetimer();
                userans="a";
                if(answer.equalsIgnoreCase(userans))
                {
                    a.setBackgroundColor(Color.GREEN);
                    correctans++;
                    correct.setText(""+correctans);
                }
                else {
                    a.setBackgroundColor(Color.RED);
                    incorrectans++;
                    wrong.setText(""+incorrectans);
                    findans();
                }
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pausetimer();
                userans="b";

                if(answer.equalsIgnoreCase(userans))
                {
                    b.setBackgroundColor(Color.GREEN);
                    correctans++;
                    correct.setText(""+correctans);
                }
                else {
                    b.setBackgroundColor(Color.RED);
                    incorrectans++;
                    wrong.setText(""+incorrectans);
                    findans();
                }
            }
        });


        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pausetimer();
                userans="c";

                if(answer.equalsIgnoreCase(userans))
                {
                    c.setBackgroundColor(Color.GREEN);
                    correctans++;
                    correct.setText(""+correctans);
                }
                else {
                    c.setBackgroundColor(Color.RED);
                    incorrectans++;
                    wrong.setText(""+incorrectans);
                    findans();
                }
            }
        });


        d.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pausetimer();
                userans="d";

                if(answer.equalsIgnoreCase(userans))
                {
                    d.setBackgroundColor(Color.GREEN);
                    correctans++;
                    correct.setText(""+correctans);
                }
                else {
                    d.setBackgroundColor(Color.RED);
                    incorrectans++;
                    wrong.setText(""+incorrectans);
                    findans();
                }
            }
        });

        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resettimer();
                game();
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendscore();
                Intent i=new Intent(quizpg.this,scorepg.class);
                startActivity(i);
                finish();

            }
        });

    }
    public void findans()
    {
       if(answer.equalsIgnoreCase("a"))
           a.setBackgroundColor(Color.GREEN);
       else if (answer.equalsIgnoreCase("b")) {
           b.setBackgroundColor(Color.GREEN);
       } else if (answer.equalsIgnoreCase("c")) {
           c.setBackgroundColor(Color.GREEN);
       } else if (answer.equalsIgnoreCase("d")) {
           d.setBackgroundColor(Color.GREEN);
       }

    }
    public void game()
    {
        starttimer();
        a.setBackgroundColor(Color.WHITE);
        b.setBackgroundColor(Color.WHITE);
        c.setBackgroundColor(Color.WHITE);
        d.setBackgroundColor(Color.WHITE);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                qcount= (int) dataSnapshot.getChildrenCount();
                question=dataSnapshot.child(String.valueOf(qnum)).child("Q").getValue().toString();
                optiona=dataSnapshot.child(String.valueOf(qnum)).child("A").getValue().toString();
                optionb=dataSnapshot.child(String.valueOf(qnum)).child("B").getValue().toString();
                optionc=dataSnapshot.child(String.valueOf(qnum)).child("C").getValue().toString();
                optiond=dataSnapshot.child(String.valueOf(qnum)).child("D").getValue().toString();
                answer=dataSnapshot.child(String.valueOf(qnum)).child("ANS").getValue().toString();
                que.setText(question);
                a.setText(optiona);
                b.setText(optionb);
                c.setText(optionc);
                d.setText(optiond);

                if (qnum<qcount)
                {
                    qnum++;
                }
                else
                {
                    Toast.makeText(quizpg.this, "You answered all the questions", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(quizpg.this, "There is an error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void starttimer() {
        countDownTimer = new CountDownTimer(timeleft,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                timeleft=millisUntilFinished;
                updatecountdowmtext();


            }

            @Override
            public void onFinish() {
                timercont=false;
                pausetimer();
                que.setText("Sorry time is up");

            }
        }.start();
        timercont=true;
    }
    public void resettimer()
    {
        timeleft=totaltime;
        updatecountdowmtext();
    }

    public void updatecountdowmtext() {
        int second= (int) ((timeleft/1000)%60);
        time.setText(""+second);
    }
    public void pausetimer(){
        countDownTimer.cancel();
        timercont=false;
    }
    public void sendscore()
    {
        String useruid=user.getUid();
        refs.child("scores").child(useruid).child("correct").setValue(correctans).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(quizpg.this, "Scores sent succesfully", Toast.LENGTH_SHORT).show();
            }
        });
        refs.child("scores").child(useruid).child("wrong").setValue(incorrectans);
    }

}
