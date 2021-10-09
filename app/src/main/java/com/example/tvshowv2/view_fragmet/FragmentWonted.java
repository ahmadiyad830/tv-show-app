package com.example.tvshowv2.view_fragmet;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tvshowv2.R;
import com.example.tvshowv2.databinding.FragmentWontedBinding;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentWonted#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentWonted extends Fragment {
    private FragmentWontedBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wonted, container, false);
        return binding.getRoot();
    }



}