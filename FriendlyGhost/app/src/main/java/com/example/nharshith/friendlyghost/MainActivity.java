package com.example.nharshith.friendlyghost;

import android.content.res.AssetManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN ="user's turn" ;
    private TextView ghostTextView, gameStatus;
    private Button resetButton, challengeButton;
    private boolean turn;
    private SimpleDictonary simpleDictonary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ghostTextView = findViewById(R.id.ghost);
        gameStatus = findViewById(R.id.gameStatus);
        resetButton = findViewById(R.id.reset);
        challengeButton = findViewById(R.id.challange);

        AssetManager assetManager = getAssets();
        try {
            SimpleDictonary simpleDictonary = new SimpleDictonary(assetManager.open("words.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

            reset();
            challenge();
            begin();
        }

    private void begin() {
        turn = new Random().nextBoolean();
        if (turn) {
            gameStatus.setText("Your turn");
        } else {
            gameStatus.setText("Computer turn");
            computerTurn();
        }
    }

    public void computerTurn() {
        Log.v("Computer Turn",COMPUTER_TURN);
        gameStatus.setText(COMPUTER_TURN);
     new Handler().postDelayed(()->{
        String ghostWord = ghostTextView.getText().toString();
        if(ghostWord.length() >= 4 && simpleDictonary.isGoodWord(ghostWord)){
            gameStatus.setText("Computer Wins!");
            challengeButton.setEnabled(false);
        }else{
            String computerWord = simpleDictonary.getGoodWord(ghostWord);
            if(computerWord == null){
                gameStatus.setText("computer wins!!");
                challengeButton.setEnabled(false);
            }else{
                ghostTextView.append(computerWord.charAt(ghostWord.length())+"");
                turn = true;
                gameStatus.setText(USER_TURN);
            }
        }
        },2000);
    }

    public void reset() {
        resetButton.setOnClickListener((view) -> {
            Log.v("reset", "reset button pressed");
        });
    }

    public void challenge() {
        challengeButton.setOnClickListener((view) -> {
            Log.v("chlallenge", "challenge button pressed");
        });
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (turn) {
            turn = false;
            char userInput = (char) event.getUnicodeChar();

            if (Character.isLetter(userInput)) {
                ghostTextView.append(Character.toString(userInput));
                computerTurn();
            }
        }
        return super.onKeyUp(keyCode, event);
    }

}