package com.example.java.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

public class CreateGame extends Activity {

    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
        user = getIntent().getStringExtra("USUARIO");
        final EditText et_num = (EditText) findViewById(R.id.et_njugadores);
        Toast.makeText(this, "usuario: "+user, Toast.LENGTH_SHORT).show();
        et_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String num = et_num.getText().toString();
                int n = 0;
                if (!num.equals("")) {
                    n = Integer.parseInt(num);
                    if (n < 2 || n > 8) {
                        et_num.setText("");
                    }
                }
            }

            @Override
            public void afterTextChanged (Editable editable){}
        });
    }
}