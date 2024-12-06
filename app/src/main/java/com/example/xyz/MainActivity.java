package com.example.xyz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    Button button;
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progresS);
        button = findViewById(R.id.btnBienvenida);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        counter++;
                        progressBar.setProgress(counter);
                        if (counter==20){
                            timer.cancel();

                            Intent intent = new Intent(MainActivity.this, Lista.class);
                            startActivity(intent);
                        }
                    }
                };
                timer.schedule(timerTask,20,20);
            }
        });

    }
    public void entrar (View view){
        Intent intent = new Intent(this, Lista.class);
        startActivity(intent);
    }
}