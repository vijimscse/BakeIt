package com.udacity.bakeit.ui;

import android.content.Context;
import android.net.Uri;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
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

    private SimpleExoPlayer mExoPlayer;
    private Uri mUri;
    private MediaSessionCompat mMediaSessionCompat;

    private ExoPlayerHandler() {
    }

    public static ExoPlayerHandler getInstance() {
        if (sInstance == null) {
            sInstance = new ExoPlayerHandler();
        }
        return sInstance;
    }

    /**
     * Intialises the video player
     *
     * @param context
     * @param str
     * @param exoPlayerView
     * @param currentPos
     */
    public void prepare(Context context, String str, SimpleExoPlayerView exoPlayerView, long currentPos) {
        if (context != null && exoPlayerView != null) {

            mMediaSessionCompat = new MediaSessionCompat(context, ExoPlayerHandler.class.getSimpleName());
            mMediaSessionCompat.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
            mMediaSessionCompat.setMediaButtonReceiver(null);
            setPlaybackStateBuilder();
            setPlayerCallbackListener();
            mMediaSessionCompat.setActive(true);

            if (!TextUtils.isEmpty(str)) {
                Uri tempUri = Uri.parse(str);
                if (!tempUri.equals(mUri) || mExoPlayer == null) {
                    mUri = tempUri;
                    mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                            new DefaultRenderersFactory(context),
                            new DefaultTrackSelector(), new DefaultLoadControl());
                    exoPlayerView.setPlayer(mExoPlayer);
                    MediaSource mediaSource = buildMediaSource();
                    mExoPlayer.prepare(mediaSource);
                    mExoPlayer.setPlayWhenReady(true);
                }
                mExoPlayer.clearVideoSurface();
                mExoPlayer.setVideoSurfaceView(
                        (SurfaceView) exoPlayerView.getVideoSurfaceView());
                mExoPlayer.seekTo(currentPos + 1);
            }
        }
    }

    /**
     * Initialises the state builder
     */
    public void setPlaybackStateBuilder() {
        PlaybackStateCompat.Builder stateBuilder = new PlaybackStateCompat.Builder().setActions(PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);
        mMediaSessionCompat.setPlaybackState(stateBuilder.build());
    }

    /**
     * Callback listener for player
     */
    public void setPlayerCallbackListener() {
        mMediaSessionCompat.setCallback(new MediaSessionCompat.Callback() {
            @Override
            public void onPlay() {
                mExoPlayer.setPlayWhenReady(true);
            }

            @Override
            public void onPause() {
                mExoPlayer.setPlayWhenReady(false);
            }

            @Override
            public void onSkipToPrevious() {
                mExoPlayer.seekTo(0);
            }
        });
    }

    /**
     * Media source builder
     *
     * @return
     */
    private MediaSource buildMediaSource() {
        return new ExtractorMediaSource(mUri, new DefaultHttpDataSourceFactory("ua"),
                new DefaultExtractorsFactory(), null, null);
    }

    /**
     * Releases the player and the memory held by player and sessions.
     */
    public void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
        }
        if (mMediaSessionCompat != null) {
            mMediaSessionCompat.setActive(false);
            mMediaSessionCompat = null;
        }
        mExoPlayer = null;
    }

    /**
     * Provides the current playback position
     *
     * @return
     */
    public long getCurrentPlaybackPosition() {
        long currentPlayPosition = 0;

        if (mExoPlayer != null) {
            currentPlayPosition = mExoPlayer.getCurrentPosition();
        }

        return currentPlayPosition;
    }
}
