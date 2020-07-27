package com.example.helloworld.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.helloworld.R;

import org.xml.sax.ext.Locator2;

import controller.controller.User;



public class MainActivity extends AppCompatActivity {

    private TextView mGreeting;
    private EditText mNameInput;
    private Button mValid;

    private User mUser;
    public static final int GAME_ACTIVITY_REQUEST_CODE=42;
    private SharedPreferences mPreferences;
    public static final String PREF_KEY_SCORE="PREF_KEY_SCORE";
    public static final String PREF_KEY_FIRSTNAME="PREF_KEY_FIRSTNAME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Link widgets
        mGreeting=(TextView)findViewById(R.id.activity_main_greeting_txt);
        mNameInput=(EditText)findViewById(R.id.activity_main_name_input);
        mValid=(Button)findViewById(R.id.activity_main_play_btn);

        //Make the button unclickable when the name is not typed yet
        mValid.setEnabled(false);

        //Initialize a user and get his name
        mUser=new User();


        //Initialize the preferences to save the player info
        mPreferences=getPreferences(MODE_PRIVATE);

        //Make the button clickable when we start typing a name using a listener
        mNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mValid.setEnabled(charSequence.toString().length()!=0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //Use an onClick Listener for the button to take actions
        mValid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUser.setFirstName(mNameInput.getText().toString());
                // We store the user's name in the preferences
                mPreferences.edit().putString(PREF_KEY_FIRSTNAME,mUser.getFirstName()).apply();
                //User clicked the button
                Intent gameActivity = new Intent(MainActivity.this, GameActivity.class);
                startActivityForResult(gameActivity,GAME_ACTIVITY_REQUEST_CODE);
                    }
                });

            }

            //Get the the previous first name and the score of the user's last game
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(GAME_ACTIVITY_REQUEST_CODE==requestCode && RESULT_OK==resultCode){
            //Fetch the score from the intent
            int score= data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE,0);
            mPreferences.edit().putInt(PREF_KEY_SCORE,score).apply();
            greetUser();
        }
    }
    private void greetUser() {
        String firstname = mPreferences.getString(PREF_KEY_FIRSTNAME, null);

        if (null != firstname) {
            int score = mPreferences.getInt(PREF_KEY_SCORE, 0);

            String fulltext = "Welcome back, " + firstname
                    + "!\nYour last score was " + score
                    + ", will you do better this time?";
            mGreeting.setText(fulltext);
            mNameInput.setText(firstname);
            mNameInput.setSelection(firstname.length());
            mValid.setEnabled(true);
        }
    }

    }
