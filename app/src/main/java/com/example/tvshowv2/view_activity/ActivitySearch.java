package com.example.tvshowv2.view_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvshowv2.R;
import com.example.tvshowv2.adapter.TVShowAdapter;
import com.example.tvshowv2.databinding.ActivitySearchBinding;
import com.example.tvshowv2.listeners.TVShowListener;
import com.example.tvshowv2.models.TVShows;
import com.example.tvshowv2.view_models.SearchTVShowViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ActivitySearch extends AppCompatActivity {
    private ActivitySearchBinding activitySearchBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);



    }
}