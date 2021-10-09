package com.example.tvshowv2.view_fragmet;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.tvshowv2.R;
import com.example.tvshowv2.adapter.ViewPager2Adapter;
import com.example.tvshowv2.databinding.FragmentHomeBinding;
import com.google.android.material.tabs.TabLayoutMediator;

public class FragmentHome extends Fragment {
    private ViewPager2Adapter viewPager2Adapter;
    private FragmentHomeBinding fragmentHomeBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

//        fragmentHomeBinding.tabLayout.setupWithViewPager(fragmentHomeBinding.viewPager);
        fragmentHomeBinding.icSearch.setOnClickListener(v -> Navigation.findNavController(fragmentHomeBinding.getRoot()).navigate(R.id.action_home_to_search2));

        return fragmentHomeBinding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentHomeBinding.collapseActionView.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        fragmentHomeBinding.collapseActionView.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);
        loadTabLayout();

    }


    private void loadTabLayout() {
        viewPager2Adapter = new ViewPager2Adapter(getActivity());
        viewPager2Adapter.addFragment(new FragmentHomeMain());
        viewPager2Adapter.addFragment(new FragmentSearch());
        fragmentHomeBinding.viewPager2.setAdapter(viewPager2Adapter);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(fragmentHomeBinding.tableLayout,
                fragmentHomeBinding.viewPager2, (tab, position) -> {
            switch (position) {
                case 1:
                    tab.setText("wanted");
                    break;
                case 0:
                default:
                    tab.setText("Showed");
                    break;

            }
        });
        tabLayoutMediator.attach();
    }


}