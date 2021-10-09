package com.example.tvshowv2.view_activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.tvshowv2.R;
import com.example.tvshowv2.adapter.EpisodesAdapter;
import com.example.tvshowv2.adapter.ImageSliderAdapter;
import com.example.tvshowv2.databinding.ActivityDetailsBinding;
import com.example.tvshowv2.databinding.LayoutEpisodesBottomSheetBinding;
import com.example.tvshowv2.models.Episode;
import com.example.tvshowv2.models.TVShowDetails;
import com.example.tvshowv2.models.TVShows;
import com.example.tvshowv2.view_models.TVShowDetailsViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ActivityDetails extends AppCompatActivity implements View.OnClickListener {
    private ActivityDetailsBinding activityDetailsBinding;
    private TVShowDetailsViewModel tvShowDetailsViewModel;
    private String uriWebsite;
    private List<Episode> responseEpisodes;
    private final Context context = ActivityDetails.this;
    private BottomSheetDialog episodesBottomSheetDialog;
    private LayoutEpisodesBottomSheetBinding layoutEpisodesBottomSheetBinding;
    private TVShows tvShows;
    private Boolean isTVShwoedInShowedList = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        tvShows = (TVShows) getIntent().getSerializableExtra("tv_Show");

        doInitialization();
    }

    private void doInitialization() {
        tvShowDetailsViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(TVShowDetailsViewModel.class);
//        activityDetailsBinding.imageBack.setOnClickListener(v -> onBackPressed());
        activityDetailsBinding.collapseActionView.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        activityDetailsBinding.collapseActionView.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);
        checkIsShowed();
        getTVShowDetails();

    }

    private void checkIsShowed() throws NullPointerException {
        CompositeDisposable compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(tvShowDetailsViewModel.getTVShoweFromListShowedList(String.valueOf(tvShows.getId()))
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((new Consumer<TVShows>() {
                            @Override
                            public void accept(TVShows tvShows1) throws Exception {
                                isTVShwoedInShowedList = true;
//                        activityDetailsBinding.btn.setImageResource(R.drawable.ic_true);
                                compositeDisposable.dispose();
                            }
                        }))
        );
    }

    private void getTVShowDetails() {
        activityDetailsBinding.setIsLoading(true);
        String tvShowId = String.valueOf(tvShows.getId());
        tvShowDetailsViewModel.getTVShowDetails(tvShowId).observe(this, tvShowDetailsResponse -> {
            activityDetailsBinding.setIsLoading(false);
            if (tvShowDetailsResponse.getTvShowDetails() != null) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                List<String> listPicture = Arrays.asList(tvShowDetailsResponse.getTvShowDetails().getPictures());
                List<String> listGenere = Arrays.asList(tvShowDetailsResponse.getTvShowDetails().getGenres());

                TVShowDetails tvShowDetails = new TVShowDetails(tvShowDetailsResponse.getTvShowDetails().getUrl(),
                        tvShowDetailsResponse.getTvShowDetails().getDescription(),
                        tvShowDetailsResponse.getTvShowDetails().getRunTime(),
                        tvShowDetailsResponse.getTvShowDetails().getImagePath(),
                        tvShowDetailsResponse.getTvShowDetails().getRating(),
                        listPicture,
                        listGenere,
                        tvShowDetailsResponse.getTvShowDetails().getEpisodes());
                reference.child("list").setValue(tvShowDetails);
                loadImageSlider(tvShowDetailsResponse.getTvShowDetails().getPictures());
                activityDetailsBinding.setTvShowImage(tvShowDetailsResponse.getTvShowDetails().getImagePath());
                activityDetailsBinding.imageTVShow.setVisibility(View.VISIBLE);
                activityDetailsBinding.imageTVShow.setOnClickListener(this);
                activityDetailsBinding.setDescription(String.valueOf(HtmlCompat.fromHtml(
                        tvShowDetailsResponse.getTvShowDetails().getDescription(),
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                )));
                activityDetailsBinding.textDescription.setVisibility(View.VISIBLE);
                activityDetailsBinding.textKeepReading.setVisibility(View.VISIBLE);
                activityDetailsBinding.textKeepReading.setOnClickListener(this);
                activityDetailsBinding.btnEpisodes.setOnClickListener(this);
                if (tvShowDetailsResponse.getTvShowDetails().getUrl() != null) {
                    uriWebsite = tvShowDetailsResponse.getTvShowDetails().getUrl();
                    activityDetailsBinding.btnWebsite.setOnClickListener(this);
                }

                activityDetailsBinding.setRating(String.format(Locale.getDefault(), "%.2f", Double.parseDouble(tvShowDetailsResponse.getTvShowDetails().getRating())));
                String[] genere = tvShowDetailsResponse.getTvShowDetails().getGenres();

                if (genere.length == 4 || genere.length < 4 || genere.length > 0) {
                    TextView textView;
                    for (int i = 0; i < genere.length; i++) {
                        if (i >= 1) {
                            int r = R.id.genere + i;
                            textView = findViewById(r);
                        } else {
                            textView = findViewById(R.id.genere);
                        }
                        textView.setText(genere[i]);
                    }
                } else {
                    activityDetailsBinding.setGenre1("N/A");
                }

//                if (tvShowDetailsResponse.getTvShowDetails().getGenres().length > 0) {
//                    activityDetailsBinding.setGenre1(tvShowDetailsResponse.getTvShowDetails().getGenres()[0]);
//                } else {
//                    activityDetailsBinding.setGenre1("N/A");
//                }
                activityDetailsBinding.setRuntime(tvShowDetailsResponse.getTvShowDetails().getRunTime() + "Min");
//                activityDetailsBinding.viewDivider1.setVisibility(View.VISIBLE);
//                activityDetailsBinding.viewDivider2.setVisibility(View.VISIBLE);
                activityDetailsBinding.layoutMisc.setVisibility(View.VISIBLE);
                activityDetailsBinding.imgStar.setVisibility(View.VISIBLE);
                activityDetailsBinding.btnWebsite.setVisibility(View.VISIBLE);
                activityDetailsBinding.btnEpisodes.setVisibility(View.VISIBLE);
                activityDetailsBinding.btnEpisodes.setOnClickListener(this);
                responseEpisodes = tvShowDetailsResponse.getTvShowDetails().getEpisodes();
                Collections.reverse(responseEpisodes);
                activityDetailsBinding.btnShowed.setOnClickListener(this);
                activityDetailsBinding.btnShowed.setVisibility(View.VISIBLE);

                loadBasicTVShow();
            }
        });
    }

    private void loadImageSlider(String[] imageSlider) {
        activityDetailsBinding.sliderViewPager.setAdapter(new ImageSliderAdapter(imageSlider));

        activityDetailsBinding.sliderViewPager.setVisibility(View.VISIBLE);
        transitionSliderImage();
//        activityDetailsBinding.viewFadingEdge.setVisibility(View.VISIBLE);
        setupSliderIndicators(imageSlider.length);
        activityDetailsBinding.sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentSliderIndicator(position);
            }
        });
    }

    private void transitionSliderImage() {
        ViewPager2 viewPager2 = activityDetailsBinding.sliderViewPager;
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
        });
        viewPager2.setPageTransformer(compositePageTransformer);

    }

    private void setupSliderIndicators(int count) {
        ImageView[] indicator = new ImageView[count];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < indicator.length; i++) {
            indicator[i] = new ImageView(getApplicationContext());
            indicator[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(), R.drawable.background_slider_indicator_inactive
            ));
            indicator[i].setLayoutParams(layoutParams);
            activityDetailsBinding.layoutSliderIndicators.addView(indicator[i]);
        }
        activityDetailsBinding.layoutSliderIndicators.setVisibility(View.VISIBLE);
        setCurrentSliderIndicator(0);
    }

    private void setCurrentSliderIndicator(int position) {
        int childCount = activityDetailsBinding.layoutSliderIndicators.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) activityDetailsBinding.layoutSliderIndicators.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        getApplicationContext(), R.drawable.background_slider_indicator_active));

            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        getApplicationContext(), R.drawable.background_slider_indicator_inactive
                ));
            }
        }
    }

    private void loadBasicTVShow() {
        activityDetailsBinding.setTvShowName(tvShows.getName());
        activityDetailsBinding.setNetworkCountry(tvShows.getNetwork() + "(" + tvShows.getCountry() + ")");
        activityDetailsBinding.setStatus(tvShows.getStatus());
        activityDetailsBinding.setStartDate(tvShows.getStartDate());
        activityDetailsBinding.textName.setVisibility(View.VISIBLE);
        activityDetailsBinding.textNetWork.setVisibility(View.VISIBLE);
        activityDetailsBinding.textStatus.setVisibility(View.VISIBLE);
        activityDetailsBinding.textStarted.setVisibility(View.VISIBLE);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == activityDetailsBinding.textKeepReading.getId()) {
            if (activityDetailsBinding.textKeepReading.getText().equals("keep reading")) {
                activityDetailsBinding.textDescription.setMaxLines(Integer.MAX_VALUE);
                activityDetailsBinding.textDescription.setEllipsize(null);
                activityDetailsBinding.textKeepReading.setText("Read Less");
            } else {
                activityDetailsBinding.textDescription.setMaxLines(4);
                activityDetailsBinding.textDescription.setEllipsize(TextUtils.TruncateAt.END);
                activityDetailsBinding.textKeepReading.setText("keep reading");
            }
        } else if (id == activityDetailsBinding.btnEpisodes.getId()) {

            if (episodesBottomSheetDialog == null) {

                episodesBottomSheetDialog = new BottomSheetDialog(context);
                layoutEpisodesBottomSheetBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_episodes_bottom_sheet, findViewById(R.id.episodesContainer),
                        false);
                episodesBottomSheetDialog.setContentView(layoutEpisodesBottomSheetBinding.getRoot());
                layoutEpisodesBottomSheetBinding.recyclerEpisodesView.setAdapter(new EpisodesAdapter(responseEpisodes));
                layoutEpisodesBottomSheetBinding.textTitle.setText(String.format("Episodes | %s", tvShows.getName()));
                layoutEpisodesBottomSheetBinding.imageClose.setOnClickListener(v1 -> episodesBottomSheetDialog.dismiss());
                FrameLayout frameLayout = episodesBottomSheetDialog.findViewById(
                        com.google.android.material.R.id.design_bottom_sheet);
                if (frameLayout != null) {
                    BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(frameLayout);
                    bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }
            episodesBottomSheetDialog.show();
        } else if (id == activityDetailsBinding.btnWebsite.getId()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(uriWebsite));
            startActivity(intent);
        } else if (id == activityDetailsBinding.btnShowed.getId()) {
            CompositeDisposable compositeDisposable = new CompositeDisposable();
            if (isTVShwoedInShowedList) {
                compositeDisposable.add(tvShowDetailsViewModel.removeTVShowFromShowedList(tvShows)
                                .subscribeOn(Schedulers.computation())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> {
                                    isTVShwoedInShowedList = false;
//                            activityDetailsBinding.fabWatchList.setImageResource(R.drawable.ic_watch_list);
                                    Toast.makeText(context, "is removed", Toast.LENGTH_SHORT).show();

                                })
                );
            } else {
                compositeDisposable.add(tvShowDetailsViewModel.addToWatchList(tvShows)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action() {
                            @Override
                            public void run() throws Exception {
                                isTVShwoedInShowedList = true;
//                            activityDetailsBinding.btnShowed.setImageResource(R.drawable.ic_true);
                                Toast.makeText(context, "is Showed", Toast.LENGTH_SHORT).show();
                            }
                        }));
            }
            compositeDisposable.dispose();

        }
    }

}

