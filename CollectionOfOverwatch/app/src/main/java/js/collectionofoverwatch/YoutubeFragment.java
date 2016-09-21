package js.collectionofoverwatch;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;

import com.google.android.youtube.player.YouTubePlayerSupportFragment;

/**
 * Created by Junsuk on 2016-09-17.
 */
public class YoutubeFragment extends YouTubePlayerSupportFragment/* implements YouTubePlayer.OnFullscreenListener*/ {




    @SuppressLint("InlinedApi")
    private static final int PORTRAIT_ORIENTATION = Build.VERSION.SDK_INT < 9
            ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            : ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;

    @SuppressLint("InlinedApi")
    private static final int LANDSCAPE_ORIENTATION = Build.VERSION.SDK_INT < 9
            ? ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            : ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;

    String mVideoID;
    private boolean mAutoRotation = false;
//    private YouTubePlayer.OnFullscreenListener fullScreenListener = null;
    private YouTubePlayer mPlayer;

    public YoutubeFragment() {
    }

    public YoutubeFragment(String videoId) {
        mVideoID = videoId;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (mPlayer != null)
                mPlayer.setFullscreen(true);
        }
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (mPlayer != null)
                mPlayer.setFullscreen(false);
        }
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mAutoRotation = Settings.System.getInt(getActivity().getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0) == 1;
//        fullScreenListener = this;
        //오직 백스택에 아무것도 없을때에만 가능하다.
        //setRetainInstance(true);
    }


    public static YoutubeFragment newInstance(String videoId) {

        Bundle args = new Bundle();
        args.putString("VideoID", videoId);

        YoutubeFragment fragment = new YoutubeFragment(videoId);
        fragment.setArguments(args);
        fragment.init();
        return fragment;
    }

    public void init() {
        String appKey = "AIzaSyBYp1u6jOKf-Q_6NuZzG9uJEOWA5i_E4UQ";


        initialize(appKey, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
                mPlayer = youTubePlayer;
//                youTubePlayer.setOnFullscreenListener(fullScreenListener);
                if (mAutoRotation) {
                    youTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE
                            | YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION
                            | YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI
                            | YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT
                    );
                }
                else{
                    youTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION
                    |YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI
                    |YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
                }
                if(!b){
                    final String overwatchVideo = getArguments().getString("VideoID");
                    mPlayer.loadVideo(overwatchVideo);
                }
            }

                @Override
                public void onInitializationFailure (YouTubePlayer.Provider
                provider, YouTubeInitializationResult youTubeInitializationResult){
                    if (youTubeInitializationResult.isUserRecoverableError()) {
                        Log.d("YOUTUBE_ERROR", youTubeInitializationResult.toString());

                    }
                }
            }

            );
        }


//    @Override
//    public void onFullscreen(boolean b) {
//        if(b){
//            getActivity().setRequestedOrientation(LANDSCAPE_ORIENTATION);
//        }
//        else getActivity().setRequestedOrientation(PORTRAIT_ORIENTATION);
//    }



}
