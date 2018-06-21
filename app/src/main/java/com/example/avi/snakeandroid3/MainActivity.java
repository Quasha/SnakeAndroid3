package com.example.avi.snakeandroid3;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.avi.snakeandroid3.classes.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    Button bPlay;
    Button bScores;
    DatabaseHelper scoresDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bPlay = findViewById(R.id.playButton);
        bScores = findViewById(R.id.scoresButton);
        scoresDb = new DatabaseHelper(this);


        bPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameIntent = new Intent(MainActivity.this, GameActivity.class);
                MainActivity.this.startActivity(gameIntent);
            }
        });

        viewScores();
    }

    public void viewScores(){
        bScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor result = scoresDb.getAllData();
                if(result.getCount() == 0){
                    Toast.makeText(MainActivity.this, "No high scores :(", Toast.LENGTH_SHORT).show();
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while(result.moveToNext()){
                    buffer.append(result.getInt(0)+ "\n");
                }

                showMessage("Highscores: ", buffer.toString());
            }
        });
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
