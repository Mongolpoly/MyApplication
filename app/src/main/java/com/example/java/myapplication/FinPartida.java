package com.example.java.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinPartida extends Activity {

    private Button button2;
    TextView tv1;
    String win = getString(R.string.win);
    String lose = getString(R.string.lose);
    boolean gana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_partida);
        gana = getIntent().getBooleanExtra("win", false);
        tv1 = (TextView) findViewById(R.id.textView5);
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity1();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        ThreadWaiting();
    }

    public void openActivity1(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void ThreadWaiting() {
        Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    runOnUiThread(new Runnable() {
                        int c = 0;
                        @Override
                        public void run() {
                            try {
                                if (gana){
                                    tv1.setText(win);
                                }else{
                                    tv1.setText(lose);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            c++;
                        }
                    });

                } catch (InterruptedException e) {
                }

            }
        };
        Thread espera = new Thread(runnable);
        espera.start();
    }
}
