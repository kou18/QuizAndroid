package com.example.helloworld.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.R;

import org.xml.sax.ext.Locator2;

import java.util.Arrays;

import controller.controller.Question;
import controller.controller.QuestionBank;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mQuestion;
    private Button mbtn1,mbtn2,mbtn3,mbtn4;
    private QuestionBank mQuestionBank;
    private Question mCurrentQuestion;

    private int mScore;
    private int mNumberOfQuestions;

    public static final String BUNDLE_EXTRA_SCORE="BUNDLE_EXTRA_SCORE";
    public static final String BUNDLE_STATE_SCORE="current score";
    public static final String BUNDLE_STATE_QUESTION="currentQuestion";
    private boolean mEnableTouchEvents;

    @Override
    protected void onSaveInstanceState(Bundle outState){
        outState.putInt(BUNDLE_STATE_SCORE,mScore);
        outState.putInt(BUNDLE_STATE_QUESTION,mNumberOfQuestions);
        super.onSaveInstanceState(outState);
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mEnableTouchEvents=true;


        if(savedInstanceState!=null){
            mScore=savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mNumberOfQuestions=savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
        }
        else{
            mScore=0;
            mNumberOfQuestions=3;
        }


        // Wire widgets
        mQuestion=(TextView)findViewById(R.id.activity_game_question_text);
        mbtn1=(Button)findViewById(R.id.activity_game_answer1_btn);
        mbtn2=(Button)findViewById(R.id.activity_game_answer2_btn);
        mbtn3=(Button)findViewById(R.id.activity_game_answer3_btn);
        mbtn4=(Button)findViewById(R.id.activity_game_answer4_btn);



        // Name the buttons
        mbtn1.setTag(0);
        mbtn2.setTag(1);
        mbtn3.setTag(2);
        mbtn4.setTag(3);

        mbtn1.setOnClickListener(this);
        mbtn2.setOnClickListener(this);
        mbtn3.setOnClickListener(this);
        mbtn4.setOnClickListener(this);

        //Generate our questions
        mQuestionBank=this.generateQuestions();

        // Get the Question
        mCurrentQuestion=mQuestionBank.getQuestion();
        this.displayQuestion(mCurrentQuestion);

    }



    @Override
    public void onClick(View view) {
        int responseIndex= (int) view.getTag();
        if (responseIndex==mCurrentQuestion.getmAnswerIdx()){
            //Good Answer
            Toast.makeText(this,"Correct",Toast.LENGTH_SHORT).show();
            mScore++;
        }
        else {
            //Wrong Answer
            Toast.makeText(this,"Wrong",Toast.LENGTH_SHORT).show();
        }
        mEnableTouchEvents=false;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //If this is the last question, end the game
                //else display the next question
                mEnableTouchEvents=true;
                if(--mNumberOfQuestions== 0){
                    //End Game
                    endGame();
                }
                else{
                    mCurrentQuestion=mQuestionBank.getQuestion();
                    displayQuestion(mCurrentQuestion);
                }

            }
        },2000); //LENGTH_SHORT is 2s;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    private void displayQuestion(final Question question){
        mQuestion.setText(question.getmQuestion());
        mbtn1.setText(question.getmChoiceList().get(0));
        mbtn2.setText(question.getmChoiceList().get(1));
        mbtn3.setText(question.getmChoiceList().get(2));
        mbtn4.setText(question.getmChoiceList().get(3));
    }

    private void endGame(){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Well done!")
                .setMessage("Your score is "+mScore)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //End Activity
                        Intent intent=new Intent();
                        intent.putExtra(BUNDLE_EXTRA_SCORE,mScore);
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                })
                .create()
                .show();
    }

    public QuestionBank generateQuestions(){
        Question question1 = new Question("Who is the creator of Android?",
                Arrays.asList("Andy Rubin",
                        "Steve Wozniak",
                        "Jake Wharton",
                        "Paul Smith"),
                0);

        Question question2 = new Question("When did the first man land on the moon?",
                Arrays.asList("1958",
                        "1962",
                        "1967",
                        "1969"),
                3);

        Question question3 = new Question("What is the house number of The Simpsons?",
                Arrays.asList("42",
                        "101",
                        "666",
                        "742"),
                3);

        return new QuestionBank(Arrays.asList(question1,
                question2,
                question3));
    }


}