package com.example.tvshowv2.view_fragmet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvshowv2.R;
import com.example.tvshowv2.adapter.TVShowAdapter;
import com.example.tvshowv2.databinding.FragmentSearchBinding;
import com.example.tvshowv2.listeners.TVShowListener;
import com.example.tvshowv2.models.TVShows;
import com.example.tvshowv2.view_activity.ActivityDetails;
import com.example.tvshowv2.view_activity.MainActivity;
import com.example.tvshowv2.view_models.SearchTVShowViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;


public class FragmentSearch extends Fragment implements TVShowListener {
    private FragmentSearchBinding fragmentSearchBinding;
    private SearchTVShowViewModel viewModel;
    private List<TVShows> tvShowsList = new ArrayList<>();
    private int currentPage = 1, totaleAvalabePage = 1;
    private TVShowAdapter tvShowAdapter;
    private Timer timer;
    private InputMethodManager keyboardListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentSearchBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        viewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(SearchTVShowViewModel.class);


        fragmentSearchBinding.inputSearch.requestFocus();
        if (fragmentSearchBinding.inputSearch.requestFocus()) {
            keyboardListener = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            keyboardListener.showSoftInput(fragmentSearchBinding.inputSearch, InputMethodManager.SHOW_IMPLICIT);
        }


        doInitialization();
        return fragmentSearchBinding.getRoot();
    }

    private final TextWatcher searchInList = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String textInput = s.toString().trim();
            boolean isEmpty = textInput.isEmpty();
            if (before > 0 && before > s.length() && !isEmpty) {
                currentPage = 1;
                totaleAvalabePage = 1;
                tvShowsList.clear();
                searchTVShow(textInput);
                tvShowAdapter.notifyDataSetChanged();
            } else if (isEmpty) {
                tvShowsList.clear();
                tvShowAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            String textInput = s.toString().trim();
            boolean isEmpty = s.toString().trim().isEmpty();
            fragmentSearchBinding.setHasTextSearch(true);
            fragmentSearchBinding.fabSearchRecycler.setOnClickListener(v -> {
                if (!isEmpty) {
                    currentPage = 1;
                    totaleAvalabePage = 1;
                    tvShowsList.clear();
                    searchTVShow(textInput);
                    tvShowAdapter.notifyDataSetChanged();
                }
            });
            fragmentSearchBinding.removeTextSearch.setOnClickListener(v -> {
                if (!isEmpty) {
                    s.clear();
                    tvShowsList.clear();
                    tvShowAdapter.notifyDataSetChanged();
                }
            });


        }

    };

    private void doInitialization() {
        fragmentSearchBinding.recycleSearch.setHasFixedSize(true);
        tvShowAdapter = new TVShowAdapter(tvShowsList,this);
        fragmentSearchBinding.recycleSearch.setAdapter(tvShowAdapter);

        fragmentSearchBinding.imageBack.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), MainActivity.class));

        });
        fragmentSearchBinding.inputSearch.addTextChangedListener(searchInList);
        fragmentSearchBinding.recycleSearch.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!fragmentSearchBinding.recycleSearch.canScrollVertically(1)) {
                    if (!fragmentSearchBinding.inputSearch.getText().toString().isEmpty()) {
                        if (currentPage < totaleAvalabePage) {
                            currentPage += 1;
                            searchTVShow(fragmentSearchBinding.inputSearch.getText().toString());
                        }
                    }
                }

            }
        });
        fragmentSearchBinding.inputSearch.requestFocus();
    }

    private void searchTVShow(String query) {
        toggleLoading();
        viewModel.SearchTVSHow(query, currentPage).observe(this, tvShowResponse -> {
            toggleLoading();
            if (tvShowResponse != null) {
                totaleAvalabePage = tvShowResponse.getTotalPages();
                if (tvShowResponse.getTvShows() != null) {
                    int oldCount = tvShowsList.size();
                    tvShowsList.addAll(tvShowResponse.getTvShows());
                    tvShowAdapter.notifyItemRangeInserted(oldCount, tvShowsList.size());
//                    if (tvShowAdapter.getItemCount() == 1)
//                        onTVShowCLicked(tvShowResponse.getTvShows().get(0));
                }
            }
        });

    }

    private void toggleLoading() {
        if (currentPage == 1) {
            fragmentSearchBinding.setIsLoading(fragmentSearchBinding.getIsLoading() == null || fragmentSearchBinding.getIsLoading() == null);
        } else {
            fragmentSearchBinding.setIsLoadingMore(fragmentSearchBinding.getIsLoadingMore() == null || !fragmentSearchBinding.getIsLoadingMore());
        }
    }

    @Override
    public void onTVShowCLicked(TVShows tvShows) {
        Intent intent = new Intent(getActivity(), ActivityDetails.class);
        intent.putExtra("tv_Show", tvShows);
        startActivity(intent);
    }

}