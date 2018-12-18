package com.example.nik7.braingame;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    TextView time,ques,score,result;
    Button reset;
    char[] op = {'+','-'};
    int quesAsked = 0;
    int correctAnswered = 0;
    int ans;
    int gameOver = 0;

    int[] arr = {1,2,3};
    List<Integer> allOptions;
    List<Integer> fourOptions;


    public void Reset(View view){
        gameOver = 0;
        quesAsked = 0;
        correctAnswered = 0;

//        Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(i);


        finish();
        startActivity(getIntent());
    }

    public void Timer(){
        new CountDownTimer(31000,1000){
            public void onTick(long left){
                left /= 1000;
                int min = (int) left/60;
                int sec = (int) left%60;
                String t = Integer.toString(min) + ':' + Integer.toString(sec);
                time.setText(t);
            }

            public void onFinish(){
                result.setText("Game Over");
                gameOver = 1;
                reset.setVisibility(View.VISIBLE);



            }
        }.start();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        allOptions = new ArrayList<Integer>();
        for(int i=0;i<=100;i++)
            allOptions.add(i);


        time = (TextView) findViewById(R.id.time);
        ques = (TextView) findViewById(R.id.ques);
        score = (TextView) findViewById(R.id.score);
        result = (TextView) findViewById(R.id.result);
        reset = (Button) findViewById(R.id.button5);

        Timer();

        setQuestion();

    }

    public void setQuestion(){

        //Setting the question
        Random rand = new Random();
        int first = rand.nextInt(101);
        int second = rand.nextInt(101);
        char operator = op[rand.nextInt(2)];
        String question = Integer.toString(first) + ' ' + operator + ' ' + Integer.toString(second);
        ques.setText(question);


        //Shuffle the allOptions array
        Collections.shuffle(allOptions);

        //Setting the answer
        if (operator == '+')
            ans = first + second;
        else
            ans = first - second;



//        Setting the options
        fourOptions = new ArrayList<Integer>();
        fourOptions.add(ans);
        for(int e:allOptions){
            if (e != ans)
                fourOptions.add(e);

            if (fourOptions.size() == 4)
                break;
        }

        Collections.shuffle(fourOptions);

        Button b1 = (Button) findViewById(R.id.button1);
        b1.setText(Integer.toString(fourOptions.get(0)));

        Button b2 = (Button) findViewById(R.id.button2);
        b2.setText(Integer.toString(fourOptions.get(1)));

        Button b3 = (Button) findViewById(R.id.button3);
        b3.setText(Integer.toString(fourOptions.get(2)));

        Button b4 = (Button) findViewById(R.id.button4);
        b4.setText(Integer.toString(fourOptions.get(3)));

    }


    public void checkAnswer(View view){
        if(gameOver == 1)
            return;

        Button b = (Button) view;
        int selected = Integer.parseInt(b.getText().toString());
        quesAsked++;

        if (selected == ans){
            correctAnswered++;
            result.setText("CORRECT");

        }

        else
            result.setText("WRONG");



        String curScore = Integer.toString(correctAnswered) + '/' + Integer.toString(quesAsked);
        score.setText(curScore);

        setQuestion();
    }


}
