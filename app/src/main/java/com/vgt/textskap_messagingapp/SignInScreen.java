package com.vgt.textskap_messagingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInScreen extends AppCompatActivity {

    // TODO: Add member variables here:
    private FirebaseAuth mAuth;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_screen);

        mEmailView = (EditText) findViewById(R.id.signInEmail);
        mPasswordView = (EditText) findViewById(R.id.signInPassword);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.integer.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        // TODO: Grab an instance of FirebaseAuth

        mAuth = FirebaseAuth.getInstance();


    }


    public void signInButton(View view) {

        attemptLogin();

    }
   /* Executed when Register button pressed
    public void registerNewUser(View v) {
        Intent intent = new Intent(this, .class);
        finish();
        startActivity(intent);
    } */

    // TODO: Complete the attemptLogin() method
    private void attemptLogin() {

        // TODO: Use FirebaseAuth to sign in with email & password

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        if (email.equals("") || password.equals("")) return;

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {

                    showMessageAlert("There was a problem in signing in");

                } else {

                    Intent nextScreen = new Intent(SignInScreen.this, ChatActivity.class);
                    finish();
                    startActivity(nextScreen);

                }
            }

        });


    }

    // TODO: Show error on screen with an alert dialog

    private void showMessageAlert(String message) {

        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .show();

    }

}




