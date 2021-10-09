package com.example.tvshowv2.view_models;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tvshowv2.models.FireProfile;
import com.example.tvshowv2.repositores.FireRepositores;

public class FireProfileViewModel extends ViewModel {
    private MutableLiveData<FireProfile> mutableLiveData;


    public void init (Context context){
        if (mutableLiveData!=null){
            return;
        }
        FireRepositores repositores = FireRepositores.getInstance();
        mutableLiveData = repositores.getData(context);
    }
    public LiveData<FireProfile> getProfile (){
        return mutableLiveData;
    }
}
