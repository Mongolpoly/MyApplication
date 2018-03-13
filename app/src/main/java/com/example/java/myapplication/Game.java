package com.example.java.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class Game extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //Recoger el intent
        Bundle bundle = getIntent().getExtras();
        String user = bundle.getString("USUARIO");
        Toast.makeText(this, "Soy el usuario "+user, Toast.LENGTH_SHORT).show();

        /*TextView tv1 = new TextView(this);
        tv1.setText("TV1");
        tv1.setTextSize(40);
        tv1.setTextColor(Color.BLACK);

        TextView tv2 = new TextView(this);
        tv2.setTextSize(50);
        tv2.setGravity(Gravity.RIGHT);
        tv2.setText("TV2");
        tv2.setTextColor(Color.WHITE);

        FrameLayout fl = new FrameLayout(this);
        fl.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT));
        fl.addView(tv1);
        fl.addView(tv2);
        setContentView(fl);*/
        Toast.makeText(this, "METER EL JUEGO AQUÍ", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
    }

    public static int TiraDado(){
        int max = 6;
        int roll = (int) (Math.random() * max) + 1;
        return roll;
    }
}
