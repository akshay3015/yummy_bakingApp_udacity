package com.example.android.bakingapp.recipedetails;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.beans.Recipe;
import com.example.android.bakingapp.beans.Steps;
import com.example.android.bakingapp.custom.ShowOrHideBackButtonInActionBar;
import com.example.android.bakingapp.utils.Utility;
import com.google.android.exoplayer2.C;
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
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by akshayshahane on 19/07/17.
 */

public class FragmentRecipeSteps extends Fragment implements PlaybackControlView.VisibilityListener {
    private static final String TAG = "FragmentRecipeSteps";
    @BindView(R.id.exoplayer_recipe)
    SimpleExoPlayerView mExoplayer;
    Unbinder unbinder;
    @BindView(R.id.tv_step)
    TextView mTvStep;
    @BindView(R.id.sv_container)
    ScrollView mSvContainer;
    @BindView(R.id.step_thumbnail)
    ImageView mStepThumbnail;
    @BindView(R.id.btn_previous)
    Button mBtnPrevious;
    @BindView(R.id.btnNext)
    Button mBtnNext;
    @BindView(R.id.buttons)
    LinearLayout mButtons;
    private Steps mSteps;
    private SimpleExoPlayer recipeVideoPlayer;
//    private SimpleExoPlayerView exoPlayerView;
    private boolean isTwoPane;
    private ShowOrHideBackButtonInActionBar callBackActionbar;
    private List<Steps> mStepsList = new ArrayList<>();
    int position = 0;
    private FragmentRecipeDetails.DataPassToStepsListenerNew callBackSteps;
    private Recipe recipe;
    private static final BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
    private TrackSelection.Factory videoTrackSelectionFactory;
    private TrackSelector trackSelector;
    private int resumeWindow;
    private long resumePosition;
    private Handler mainHandler;
    private static final CookieManager DEFAULT_COOKIE_MANAGER;

    static {
        DEFAULT_COOKIE_MANAGER = new CookieManager();
        DEFAULT_COOKIE_MANAGER.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_steps, container, false);
        unbinder = ButterKnife.bind(this, view);


        isTwoPane = getActivity().getResources().getBoolean(R.bool.is_two_pane);
        getDataFromArgumentsAndShowOrHideNextOrPreviousButton();

        clearResumePosition();
        showOrHideButtons();
        initPlayer();

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

    private void getDataFromArgumentsAndShowOrHideNextOrPreviousButton() {
        Bundle args = getArguments();
        if (null != args && args.containsKey("recipe")) {
            recipe = args.getParcelable("recipe");
            mStepsList = recipe.getStepsList();

        }

        if (null != args && args.containsKey("steps")) {
            mSteps = (Steps) args.getSerializable("steps");
        }

        if (null != args && args.containsKey("position")) {

            position = args.getInt("position");
            if (0 == position) {
                mBtnPrevious.setVisibility(View.GONE);
            } else {
                mBtnPrevious.setVisibility(View.VISIBLE);
            }

            if (position == mStepsList.size() - 1) {
                mBtnNext.setVisibility(View.GONE);
            } else {
                mBtnNext.setVisibility(View.VISIBLE);
            }


            mBtnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position < mStepsList.size())
                        mSteps = mStepsList.get(position + 1);
                    callBackSteps.passDataToStepsN(mSteps, position + 1, recipe);

                }
            });

            mBtnPrevious.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position > 0)
                        mSteps = mStepsList.get(position - 1);
                    callBackSteps.passDataToStepsN(mSteps, position - 1, recipe);

                }
            });


        }
    }

    private void showOrHideButtons() {
        if (isTwoPane) {
            mButtons.setVisibility(View.GONE);
        } else {
            mButtons.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }


    @Override
    public void onResume() {
        super.onResume();

                initPlayer();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getContext().getResources().getBoolean(R.bool.is_two_pane)) {
            callBackActionbar.showOrHide(true);

        } else {
            callBackActionbar.showOrHide(false);
        }
        if (mSteps != null) {
            mTvStep.setText(mSteps.getDescription());

            if (TextUtils.isEmpty(mSteps.getThumbnailURL())) {
                mStepThumbnail.setVisibility(View.GONE);
            } else {
                mStepThumbnail.setVisibility(View.VISIBLE);
                Picasso.with(getContext())
                        .load(mSteps.getThumbnailURL())
                        .placeholder(R.drawable.error)
                        .error(R.drawable.ic_error_outline_black_24dp)
                        .into(mStepThumbnail, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {
                                try {
                                    mStepThumbnail.setImageBitmap(Utility.retriveVideoFrameFromVideo(mSteps.getThumbnailURL()));
                                } catch (Throwable throwable) {
                                    throwable.printStackTrace();
                                }
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
            callBackSteps = (FragmentRecipeDetails.DataPassToStepsListenerNew) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement DataPassListener");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            getActivity().onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initPlayer() {
        // Exoplayer


        // 1. Create a default TrackSelector
//        Handler mainHandler = new Handler();
        videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        RenderersFactory render = new DefaultRenderersFactory(getContext());
        mainHandler = new Handler();
        if (CookieHandler.getDefault() != DEFAULT_COOKIE_MANAGER) {
            CookieHandler.setDefault(DEFAULT_COOKIE_MANAGER);
        }

// 2. Create a default LoadControl
        LoadControl loadControl = new DefaultLoadControl();

// 3. Create the player
        recipeVideoPlayer = ExoPlayerFactory.newSimpleInstance(render, trackSelector, loadControl);

//        exoPlayerView = (SimpleExoPlayerView) v.findViewById(R.id.exoplayer_recipe);

//Set media controller
        mExoplayer.setUseController(true);
        mExoplayer.setControllerVisibilityListener(this);

        mExoplayer.requestFocus();


// Bind the player to the view.

        mExoplayer.setPlayer(recipeVideoPlayer);

        if (TextUtils.isEmpty(mSteps.getVideoURL())) {

            mExoplayer.setVisibility(View.GONE);


        } else {

            mExoplayer.setVisibility(View.VISIBLE);
            Uri video = Uri.parse(mSteps.getVideoURL());
            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "recipes"), null);
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            MediaSource videoSource = new ExtractorMediaSource(video, dataSourceFactory, extractorsFactory, mainHandler, null);
            boolean haveResumePosition = resumeWindow != C.INDEX_UNSET;
            if (haveResumePosition) {
                recipeVideoPlayer.seekTo(resumeWindow, resumePosition);
            }
            recipeVideoPlayer.prepare(videoSource,!haveResumePosition,false);

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
                    clearResumePosition();
                }

                @Override
                public void onPositionDiscontinuity() {
                    updateResumePosition();
                }

                @Override
                public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

                }


            });
        }


    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recipeVideoPlayer.release();
        updateResumePosition();
    }

    @Override
    public void onPause() {
        super.onPause();
        recipeVideoPlayer.release();
        updateResumePosition();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        recipeVideoPlayer.release();
        updateResumePosition();
    }


    @Override
    public void onVisibilityChange(int visibility) {

    }

    private void updateResumePosition() {
        resumeWindow = recipeVideoPlayer.getCurrentWindowIndex();
        resumePosition = recipeVideoPlayer.isCurrentWindowSeekable() ? Math.max(0, recipeVideoPlayer.getCurrentPosition())
                : C.TIME_UNSET;
    }

    private void clearResumePosition() {
        resumeWindow = C.INDEX_UNSET;
        resumePosition = C.TIME_UNSET;
    }


}
