package com.example.tvshowv2.view_fragmet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tvshowv2.R;
import com.example.tvshowv2.adapter.ViewPager2Adapter;
import com.example.tvshowv2.databinding.FragmentProfileBinding;
import com.example.tvshowv2.view_models.FireProfileViewModel;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProfile extends Fragment {
    private FragmentProfileBinding fragmentProfileBinding;
    private FireProfileViewModel fireProfileViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
//        if (FirebaseAuth.getInstance().getCurrentUser() !=null){
//            isSingIn();
//        }
        initialize();
        return fragmentProfileBinding.getRoot();
    }

    private void isSingIn() {

    }

    @Override
    public void onStart() {
        super.onStart();
        fragmentProfileBinding.collapseActionView.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        fragmentProfileBinding.collapseActionView.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);
    }

    private void initialize() {
        fireProfileViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(FireProfileViewModel.class);
        fireProfileViewModel.init(getContext());
        fireProfileViewModel.getProfile().observe(getViewLifecycleOwner(), fireProfile -> {
            fragmentProfileBinding.setName(fireProfile.getName());
            fragmentProfileBinding.setShowed(" " + fireProfile.getShowed() + "\nshowed");
            fragmentProfileBinding.setWantShow(" " + fireProfile.getWanted() + "\nwanted");
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadTabLayout();
    }

    private void loadTabLayout() {
        ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(getActivity());
        viewPager2Adapter.addFragment(new FragmentShowed());
        viewPager2Adapter.addFragment(new FragmentWonted());
        fragmentProfileBinding.viewPager2.setAdapter(viewPager2Adapter);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(fragmentProfileBinding.tabLayout,
                fragmentProfileBinding.viewPager2,
                (tab, position) -> {
                    switch (position) {
                        case 1:
                            tab.setText("Wanted");
                            break;
                        case 0:
                        default:
                            tab.setText("Showed");

                    }
                });

        tabLayoutMediator.attach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentProfileBinding = null;
    }
}