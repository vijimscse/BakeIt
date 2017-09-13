package com.udacity.bakeit.ui;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.SurfaceView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

/**
 * ExoPlayerHandler - Singleton class used for handling the preparing video and releasing when required.
 * <p>
 * Created by VijayaLakshmi.IN on 9/5/2017.
 */
// Reference: https://medium.com/tall-programmer/fullscreen-functionality-with-android-exoplayer-5fddad45509f
public class ExoPlayerHandler {
    private static ExoPlayerHandler sInstance;

    public static ExoPlayerHandler getInstance() {
        if (sInstance == null) {
            sInstance = new ExoPlayerHandler();
        }
        return sInstance;
    }

    private SimpleExoPlayer mExoPlayer;
    private Uri mUri;
    private boolean isPlayerPlaying = true;

    private ExoPlayerHandler() {
    }

    public void prepare(Context context, String str, SimpleExoPlayerView exoPlayerView, long currentPos) {
        if (context != null && exoPlayerView != null) {
            if (!TextUtils.isEmpty(str)) {
                Uri tempUri = Uri.parse(str);
                if (!tempUri.equals(mUri) || mExoPlayer == null) {
                    mUri = tempUri;
                    mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                            new DefaultRenderersFactory(context),
                            new DefaultTrackSelector(), new DefaultLoadControl());
                    MediaSource mediaSource = buildMediaSource();
                    mExoPlayer.prepare(mediaSource);
                }
                mExoPlayer.clearVideoSurface();
                mExoPlayer.setVideoSurfaceView(
                        (SurfaceView) exoPlayerView.getVideoSurfaceView());
                mExoPlayer.seekTo(currentPos + 1);
                exoPlayerView.setPlayer(mExoPlayer);
                putForeground();
            }
        }
    }

    private MediaSource buildMediaSource() {
        return new ExtractorMediaSource(mUri, new DefaultHttpDataSourceFactory("ua"),
                new DefaultExtractorsFactory(), null, null);
    }

    public void putBackground() {
        if (mExoPlayer != null) {
            isPlayerPlaying = mExoPlayer.getPlayWhenReady();
            mExoPlayer.setPlayWhenReady(false);
        }
    }

    public void putForeground() {
        if (mExoPlayer != null) {
            mExoPlayer.setPlayWhenReady(isPlayerPlaying);
        }
    }

    public void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.release();
        }
        mExoPlayer = null;
    }

    public long getCurrentPosition() {
        long currentPlayPosition = 0;

        if (mExoPlayer != null) {
            currentPlayPosition = mExoPlayer.getCurrentPosition();
        }

        return currentPlayPosition;
    }
}
