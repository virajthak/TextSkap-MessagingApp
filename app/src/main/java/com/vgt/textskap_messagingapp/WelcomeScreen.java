package com.vgt.textskap_messagingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class WelcomeScreen extends AppCompatActivity {

    ImageView firstOnClick;
    ImageView secondOnClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        secondOnClick = findViewById(R.id.imageViewTwo);
        secondOnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               openSignInScreen();
            }
        });

        firstOnClick = findViewById(R.id.imageViewOne);
        firstOnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignUpScreen();
            }
        });


    }

    public void openSignUpScreen(){
        Intent intent = new Intent(this,SignUpScreen.class);
        startActivity(intent);
    }

   public void openSignInScreen(){
        Intent nextScreen = new Intent(this,SignInScreen.class);
        startActivity(nextScreen);
    }
}