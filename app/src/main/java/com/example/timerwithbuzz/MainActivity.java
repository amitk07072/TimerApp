package com.example.timerwithbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar timerSeekBar;
    TextView timerTextView;
    Boolean counterActive=false;
    Button controlButton;
    CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView=findViewById(R.id.timerTextView);
        timerSeekBar=findViewById(R.id.timerSeekBar);
        controlButton=findViewById(R.id.controlButton);


        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(100);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void updateTimer(int secondsLeft)
    {
        int minutes=(int)secondsLeft/60;
        int seconds=secondsLeft-minutes*60;
        String secondString=Integer.toString(seconds);
        if(secondString.equals("0"))
            secondString="00";
        if(seconds<=9)
            secondString="0"+seconds;
        timerTextView.setText(minutes+":"+secondString);
    }

    public void controlTimer(View view)
    {
        if(counterActive==false)
        {
            counterActive = true;
            timerSeekBar.setEnabled(false);
            controlButton.setText("STOP");
            countDownTimer= new CountDownTimer(timerSeekBar.getProgress() * 1000, 1000) {

                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                public void onFinish() {
                   // timerTextView.setText("0:00");

                    MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.bell);
                    mp.start();
                    timerSeekBar.setEnabled(true);
                    counterActive=false;
                    controlButton.setText("START");

                }
            }.start();

        }
        else
        {
            countDownTimer.cancel();
            counterActive=false;
            timerTextView.setText("0:00");
            timerSeekBar.setEnabled(true);
            controlButton.setText("START");


        }
    }


}
