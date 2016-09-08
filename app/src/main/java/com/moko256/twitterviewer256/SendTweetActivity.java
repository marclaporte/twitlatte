package com.moko256.twitterviewer256;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import com.twitter.Validator;

import twitter4j.TwitterException;

/**
 * Created by moko256 on GitHub on 2015/11/08.
 */
public class SendTweetActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_tweet);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_clear_white_24dp);

        TextView counterTextView=(TextView)findViewById(R.id.tweet_text_edit_counter);
        AppCompatEditText editText=(AppCompatEditText)findViewById(R.id.tweet_text_edit);
        onEditTextChanged(editText.getText(),counterTextView);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onEditTextChanged(s,counterTextView);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        AppCompatButton button=(AppCompatButton)findViewById(R.id.tweet_text_submit);
        button.setOnClickListener(v -> {
            button.setEnabled(false);
            new AsyncTask<String,Void,Void>(){
            @Override
            public Void doInBackground(String... str){
                try {
                    Static.twitter.updateStatus(str[0]);
                    finish();
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
                return null;
            }
            }.execute(editText.getText().toString());
        });

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return false;
    }

    private void onEditTextChanged(CharSequence s,TextView counterTextView){
        int textLength=(new Validator()).getTweetLength(s.toString());
        int maxLength=Validator.MAX_TWEET_LENGTH;
        counterTextView.setText(String.valueOf(textLength)+"/"+String.valueOf(maxLength));
        if (textLength>=maxLength){
            counterTextView.setTextColor(Color.RED);
        }
        else{
            counterTextView.setTextColor(Color.GRAY);
        }
    }

}