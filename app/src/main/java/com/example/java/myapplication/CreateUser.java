package com.example.java.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

public class CreateUser extends Activity {

    private EditText mUserView;
    private EditText mPasswordView;
    private EditText mPasswordView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mUserView = (EditText) findViewById(R.id.user_rtv);
        mPasswordView = (EditText) findViewById(R.id.password_rtv);
        mPasswordView2 = (EditText) findViewById(R.id.password2_rtv);
        mPasswordView2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) { //listener para entrar con enter
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin();
                return true;
            }
            return false;
            }
        });

        Button SignInButton = (Button) findViewById(R.id.sign_in_button);
        SignInButton.setOnClickListener(new OnClickListener() { //listener del boton
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    private void attemptLogin() {
        // Reset errors.
        mUserView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String user = mUserView.getText().toString();
        String password = mPasswordView.getText().toString();
        String password2 = mPasswordView2.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid user.
        if (!isUserValid(user)) {
            mUserView.setError(getString(R.string.error_invalid_user));
            focusView = mUserView;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for equals password.
        if (!password.equals(password2)) {
            mPasswordView2.setError(getString(R.string.error_distinct_password));
            focusView = mPasswordView2;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            //showProgress(true); // Show a progress spinner, and kick off a background task to perform the user login attempt.
            JSONParser jsp = new JSONParser();
            boolean createuser = false;
            try {
                createuser = jsp.Registrousuarios(user, password);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (createuser){
                Toast.makeText(this, getString(R.string.user_created), Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(this, getString(R.string.user_alredy), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isUserValid(String user) {
        return user.length()>2 && user.length()<51; //menor que 51 porque es varchar(50) en la BBDD
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4 && password.length()<51; //menor que 51 porque es varchar(50) en la BBDD
    }
}