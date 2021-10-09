package com.example.tvshowv2.view_fragmet;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.tvshowv2.R;
import com.example.tvshowv2.adapter.TVShowedAdapter;
import com.example.tvshowv2.databinding.FragmentShowedBinding;
import com.example.tvshowv2.listeners.ShowedListListener;
import com.example.tvshowv2.models.TVShows;
import com.example.tvshowv2.view_activity.ActivityDetails;
import com.example.tvshowv2.view_models.ViewModelShowed;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class FragmentShowed extends Fragment implements ShowedListListener {
    private FragmentShowedBinding binding;
    private ViewModelShowed viewModelShowed;
    private TVShowedAdapter adapter;
    private List<TVShows> showsList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_showed, container, false);

        doInitialization();
//        filter();

        return binding.getRoot();
    }



    private void doInitialization() {
        viewModelShowed = new ViewModelProvider(getActivity(), new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(ViewModelShowed.class);
        showsList = new ArrayList<>();
//        TODO way
        loadShowedList();
    }

    private void loadShowedList() {
        binding.setIsLoading(true);
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(viewModelShowed.loadWatchedList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<TVShows>>() {
                    @Override
                    public void accept(List<TVShows> tvShows) throws Exception {
                        binding.setIsLoading(false);
                        if (showsList.size() > 0) {
                            showsList.clear();
                        }
                        showsList.addAll(tvShows);
                        adapter = new TVShowedAdapter(tvShows, FragmentShowed.this);
                        binding.recyclerShowed.setHasFixedSize(true);
                        binding.recyclerShowed.setAdapter(adapter);
                        compositeDisposable.dispose();
                    }
                })
        );
        binding.recyclerShowed.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadShowedList();
    }

    @Override
    public void onTVShowedClicked(TVShows tvShows) {
        Intent intent = new Intent(getActivity(), ActivityDetails.class);
        intent.putExtra("tv_Show", tvShows);
        startActivity(intent);
    }

    @Override
    public void removeTVShowedFromShowedList(TVShows tvShows, int position) {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(viewModelShowed.removeFromShowedList(tvShows)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    if (showsList.size() >= 1) {
                        showsList.remove(position);
//                        adapter.notifyItemRemoved(position);
//                        adapter.notifyItemChanged(position, adapter.getItemCount());
//                        adapter.notifyItemRangeRemoved(position, adapter.getItemCount());
                        onResume();
                        compositeDisposable.dispose();
                    } else {
                        onResume();
                    }
                })
        );

    }
}