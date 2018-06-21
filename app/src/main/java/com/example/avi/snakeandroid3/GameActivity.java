package com.example.avi.snakeandroid3;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.avi.snakeandroid3.classes.DatabaseHelper;
import com.example.avi.snakeandroid3.engine.GameEngine;
import com.example.avi.snakeandroid3.enums.Direction;
import com.example.avi.snakeandroid3.enums.GameStatus;
import com.example.avi.snakeandroid3.views.SnakeView;

public class GameActivity extends AppCompatActivity implements View.OnTouchListener{

    DatabaseHelper scoresDb;
    private GameEngine gameEngine;
    private SnakeView snakeView;
    private final Handler handler = new Handler();
    private final long updateDelay = 500;
    private float prevX, prevY;
    private TextView tvScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        scoresDb = new DatabaseHelper(this);

        tvScore = findViewById(R.id.scoreTextView);

        gameEngine = new GameEngine();
        gameEngine.initGame();

        snakeView = findViewById(R.id.snakeView);
        snakeView.setOnTouchListener(this);

        startUpdateHandler();
    }

    private void startUpdateHandler() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gameEngine.update();

                tvScore.setText(gameEngine.getScore()+ "");

                if(gameEngine.getCurrentGameStatus() == GameStatus.Running){
                    handler.postDelayed(this, updateDelay);

                }

                if(gameEngine.getCurrentGameStatus() == GameStatus.Over){
                    OnGameLost();
                }

                snakeView.setSnakeViewMap(gameEngine.getMap());
                snakeView.invalidate();

            }
        }, updateDelay);
    }
    private void OnGameLost(){
        scoresDb.insertData(gameEngine.getScore());
//        boolean inserted = scoresDb.insertData(gameEngine.getScore());
//        if(inserted)
//        Toast.makeText(this, "Inserted", Toast.LENGTH_SHORT).show();
//        else Toast.makeText(this, "Not inserted", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Game Over", Toast.LENGTH_SHORT).show();
    }


    //Swipe and Swipe direction detection
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                prevX = event.getX();
                prevY = event.getY();

                break;
            case MotionEvent.ACTION_UP:
                float newX = event.getX();
                float newY = event.getY();

                //check if swipe is horizontal or vertical
                if(Math.abs( newX - prevX) > Math.abs (newY - prevY)){
                    //check right or left swipe direction
                    if(newX > prevX){
                        //Swiping Right
                        gameEngine.updateDirection(Direction.Right);
                    }else{
                        //Swiping Left
                        gameEngine.updateDirection(Direction.Left);
                    }
                }else{
                    //check up or down swipe direction
                    if(newY > prevY){
                        //Swiping Down
                        gameEngine.updateDirection(Direction.Down);
                    }else{
                        //Swiping Up
                        gameEngine.updateDirection(Direction.Up);
                    }
                }

                break;
        }

        return true;
    }
}
