package com.vgt.textskap_messagingapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class chatListAdapter extends BaseAdapter {

    private Activity mActivity;
    private DatabaseReference mDatabase;
    private String mDisplayName;
    private ArrayList<DataSnapshot>mSnapShot;

    private ChildEventListener mListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            mSnapShot.add(snapshot);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };
    private ViewGroup parent;

    public chatListAdapter(Activity mActivity, DatabaseReference mDatabase, String mDisplayName) {
        this.mActivity = mActivity;
        this.mDatabase = mDatabase.child("message");
        this.mDisplayName = mDisplayName;
        mSnapShot = new ArrayList<>();
        mDatabase.addChildEventListener(mListener);
    }


    static class DesignMessageRow{
        TextView userName;
        TextView message;
        LinearLayout.LayoutParams layout;
    }



    @Override
    public int getCount() {
        return mSnapShot.size();
    }

    @Override
    public IndividualMessage getItem(int i) {

        DataSnapshot snapshot = mSnapShot.get(i);
        return snapshot.getValue(IndividualMessage.class);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view==null){

            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.chat, parent, false);

            final DesignMessageRow holder = new DesignMessageRow();
            holder.userName = view.findViewById(R.id.author);
            holder.message = view.findViewById(R.id.message);
            holder.layout = (LinearLayout.LayoutParams) holder.userName.getLayoutParams();
            view.setTag(holder);
        }

        final IndividualMessage message = (IndividualMessage) getItem(i);
        final DesignMessageRow holder = (DesignMessageRow) view.getTag();

        String author = message.getUserName();
        holder.userName.setText(author);

        String msg = message.getMessage();
        holder.message.setText(msg);

        return view;
    }

    public void cleanup(){
        mDatabase.removeEventListener(mListener);
    }
}
