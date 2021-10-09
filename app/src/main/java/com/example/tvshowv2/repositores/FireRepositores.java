package com.example.tvshowv2.repositores;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.tvshowv2.models.FireProfile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FireRepositores {
    public static FireRepositores instance;

    public static FireRepositores getInstance() {
        if (instance == null) {
            instance = new FireRepositores();
        }
        return instance;
    }

    public MutableLiveData<FireProfile> getData(Context context) {
        MutableLiveData<FireProfile> data = new MutableLiveData<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FireProfile profile = snapshot.child("profile").getValue(FireProfile.class);
                data.setValue(profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "error database mvvm \n" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return data;
    }
}
