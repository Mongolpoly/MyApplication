package com.example.java.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CreateGame extends Activity {

    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
        user = getIntent().getStringExtra("USUARIO");
        final EditText et_num = (EditText) findViewById(R.id.et_njugadores);
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
        Spinner spinner = findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        list.add(getString(R.string.red));
        list.add(getString(R.string.blue));
        list.add(getString(R.string.yellow));
        list.add(getString(R.string.purple));
        list.add(getString(R.string.orange));
        list.add(getString(R.string.green));
        list.add(getString(R.string.white));
        list.add(getString(R.string.black));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        Button btn = (Button) findViewById(R.id.btn_create_game);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CreateGame.this, Game.class);
                //i.putExtra(spinner);
                //i.putExtra()
            }
        });

    }
}