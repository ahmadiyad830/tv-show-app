package com.example.tvshowv2.view_fragmet;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvshowv2.R;
import com.example.tvshowv2.adapter.TVShowAdapter;
import com.example.tvshowv2.databinding.FragmentHomeMainBinding;
import com.example.tvshowv2.listeners.TVShowListener;
import com.example.tvshowv2.models.TVShows;
import com.example.tvshowv2.view_activity.ActivityDetails;
import com.example.tvshowv2.view_models.MostPopularTVShowViewModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentHomeMain#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHomeMain extends Fragment implements TVShowListener {

    private FragmentHomeMainBinding fragmentHomeMainBinding;
    private MostPopularTVShowViewModel viewModel;
    private  List<TVShows> listShows = new ArrayList<>();
    private TVShowAdapter tvShowAdapter;
    private final ViewModelStoreOwner owner = this;
    private int currentPage = 1, totaleAvalabePage = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentHomeMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_main, container, false);
        viewModel = new ViewModelProvider(owner, new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(MostPopularTVShowViewModel.class);
        doInitialization();

        return fragmentHomeMainBinding.getRoot();
    }


    private void doInitialization() {
        fragmentHomeMainBinding.tvShowRecyclerView.setHasFixedSize(true);
        tvShowAdapter = new TVShowAdapter(listShows,this);

        fragmentHomeMainBinding.tvShowRecyclerView.setAdapter(tvShowAdapter);

        fragmentHomeMainBinding.tvShowRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.canScrollVertically(1)) {
                    if (currentPage <= totaleAvalabePage) {
                        currentPage++;

                        getMostPopularTVShow();

                    }
                    if (recyclerView.canScrollVertically(-1)) {
                        fragmentHomeMainBinding.fabScrollRecycler.setVisibility(View.VISIBLE);
                        fragmentHomeMainBinding.fabScrollRecycler.setOnClickListener(v -> recyclerView.scrollToPosition(0));
                    } else {
                        fragmentHomeMainBinding.fabScrollRecycler.setVisibility(View.GONE);
                    }
//                  recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
                }
            }
        });
//        activityMainBinding.imgSearchList.setOnClickListener(v -> {
//            startActivity(new Intent(MainActivity.this, ActivitySearch.class));
//
//        });

        getMostPopularTVShow();

    }

    private void getMostPopularTVShow() {
        toggleLoading();
        viewModel.getMostPopularTVShow(currentPage).observe(requireActivity(), tvShowResponse -> {
            toggleLoading();
            if (tvShowResponse != null) {
                totaleAvalabePage = tvShowResponse.getTotalPages();
                if (tvShowResponse.getTvShows() != null) {
                    int oldCount = listShows.size();
                    listShows.addAll(tvShowResponse.getTvShows());
//                    filter(tvShowResponse.getTvShows());
                    tvShowAdapter.notifyItemRangeInserted(oldCount, listShows.size());
                }
            }
        });
    }

    private void toggleLoading() {
        if (currentPage == 1) {
            fragmentHomeMainBinding.setIsLoading(fragmentHomeMainBinding.getIsLoading() == null || fragmentHomeMainBinding.getIsLoading() == null);
        } else {
            fragmentHomeMainBinding.setIsLoadingMore(fragmentHomeMainBinding.getIsLoadingMore() == null || !fragmentHomeMainBinding.getIsLoadingMore());
        }
    }
    @Override
    public void onTVShowCLicked(TVShows tvShows) {
        Intent intent = new Intent(getActivity(), ActivityDetails.class);
        intent.putExtra("tv_Show", tvShows);
        startActivity(intent);
    }
}