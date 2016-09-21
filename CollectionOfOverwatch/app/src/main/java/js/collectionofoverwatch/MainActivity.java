package js.collectionofoverwatch;

import android.app.FragmentManager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;



public class MainActivity extends SingleFragmentActivity {


    private YoutubeFragment mVideoFragment;
    private static final String TAG_VIDEO_FRAGMENT = "video_fragment";


    @Override
    protected Fragment createFragment() {
        return CollectionOverwatchFragment.newInstance();
    }

    public void playVideo(int position, String videoID){

        mVideoFragment = YoutubeFragment.newInstance(videoID);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, mVideoFragment,
                TAG_VIDEO_FRAGMENT);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
