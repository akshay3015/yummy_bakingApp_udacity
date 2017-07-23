package com.example.android.bakingapp.recipedetails;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.beans.Steps;
import com.example.android.bakingapp.custom.ShowOrHideBackButtonInActionBar;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by akshayshahane on 19/07/17.
 */

public class FragmentRecipeSteps extends Fragment {
    private static final String TAG = "FragmentRecipeSteps";
    @BindView(R.id.exoplayer)
    SimpleExoPlayerView mExoplayer;
    Unbinder unbinder;
    @BindView(R.id.tv_step)
    TextView mTvStep;
    @BindView(R.id.sv_container)
    ScrollView mSvContainer;
    private Steps mSteps;
    private SimpleExoPlayer recipeVideoPlayer;
    private SimpleExoPlayerView exoPlayerView;
    private boolean isTwoPane;
    private ShowOrHideBackButtonInActionBar callBackActionbar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_steps, container, false);
        unbinder = ButterKnife.bind(this, view);
        isTwoPane = getActivity().getResources().getBoolean(R.bool.is_two_pane);

        initPlayer(view);
        Bundle args = getArguments();
        if (null != args && args.containsKey("steps")) {
            mSteps = (Steps) args.getSerializable("steps");


        }
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE && !isTwoPane) {
            mSvContainer.setVisibility(View.GONE);
            mExoplayer.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            mExoplayer.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        callBackActionbar.showOrHide(true);
        if (mSteps != null) {
            mTvStep.setText(mSteps.getDescription());

            if (TextUtils.isEmpty(mSteps.getVideoURL())) {

                mExoplayer.setVisibility(View.GONE);
            } else {
                mExoplayer.setVisibility(View.VISIBLE);
                Uri video = Uri.parse(mSteps.getVideoURL());
                DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "recipes"), null);
                ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

                MediaSource videoSource = new ExtractorMediaSource(video, dataSourceFactory, extractorsFactory, null, null);

                recipeVideoPlayer.prepare(videoSource);

                recipeVideoPlayer.setPlayWhenReady(true);


                recipeVideoPlayer.addListener(new ExoPlayer.EventListener() {
                    @Override
                    public void onTimelineChanged(Timeline timeline, Object manifest) {

                    }

                    @Override
                    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

//                                Toast.makeText(getContext(), "Track changed" + trackSelections.length, Toast.LENGTH_SHORT).show();
//


                    }

                    @Override
                    public void onLoadingChanged(boolean isLoading) {

                    }

                    @Override
                    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {


                    }


                    @Override
                    public void onPlayerError(ExoPlaybackException error) {

                    }

                    @Override
                    public void onPositionDiscontinuity() {

                    }

                    @Override
                    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

                    }


                });
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Make sure that container activity implement the callback interface
        try {
            callBackActionbar = (ShowOrHideBackButtonInActionBar) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement DataPassListener");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            getActivity().onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initPlayer(View v) {
        // Exoplayer


        // 1. Create a default TrackSelector
//        Handler mainHandler = new Handler();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        RenderersFactory render = new DefaultRenderersFactory(getContext());


// 2. Create a default LoadControl
        LoadControl loadControl = new DefaultLoadControl();

// 3. Create the player
        recipeVideoPlayer = ExoPlayerFactory.newSimpleInstance(render, trackSelector, loadControl);
        exoPlayerView = new SimpleExoPlayerView(getContext());
        exoPlayerView = (SimpleExoPlayerView) v.findViewById(R.id.exoplayer);

//Set media controller
        exoPlayerView.setUseController(true);
        exoPlayerView.requestFocus();


// Bind the player to the view.

        exoPlayerView.setPlayer(recipeVideoPlayer);

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recipeVideoPlayer.release();
    }

    @Override
    public void onPause() {
        super.onPause();
        recipeVideoPlayer.release();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        recipeVideoPlayer.release();
    }


}
