package com.vgt.textskap_messagingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatActivity extends AppCompatActivity {

    //TODO: Add member variable here:
    private String mDisplayName;
    private ListView mChatListView;
    private EditText mInputText;
    private ImageView mSendButton;

    private DatabaseReference mDatabaseRef;

    private chatListAdapter mChatListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //TODO: Setup the display name and get the firebase reference
        displayName();

        // Link the Views in the layout to the java code
         mInputText  = (EditText) findViewById(R.id.messageInput);
         mSendButton = (ImageView) findViewById(R.id.sendButton);
         mChatListView = (ListView) findViewById(R.id.chat_list_view);

         //TODO: Send the message when the enter button is pressed

         //TODO: Add on OnClickListener to the sendButton to send a message

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        mDatabaseRef = FirebaseDatabase.getInstance().getReference();


    }

    //TODO: Retrieve the display name from the shared preferences

    private void displayName(){

        SharedPreferences prefs = getSharedPreferences(SignUpScreen.CHAT_PREFS,MODE_PRIVATE);
        mDisplayName = prefs.getString(SignUpScreen.DISPLAY_NAME_KEY,null);

        if(mDisplayName == null){
            mDisplayName = "Anonymous";
        }

    }

    private void sendMessage(){

        //TODO: Grab the text the user typed in and push the message ti firebase

        String userInput = mInputText.getText().toString();

        if(!userInput.equals("")){

            IndividualMessage chat = new  IndividualMessage(mDisplayName,userInput);

            mDatabaseRef.child("message").push().setValue(chat);

            mInputText.setText("");

        }


    }

    //TODO: Override the onStart lifeCycle method. SetUp the adapter here.

    @Override
    public void onStart() {
        super.onStart();
        mChatListAdapter = new chatListAdapter(this,mDatabaseRef,mDisplayName);
        mChatListView.setAdapter(mChatListAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();

        //TODO: Remove the Firebase event listener on the adapter
        mChatListAdapter.cleanup();
    }
}