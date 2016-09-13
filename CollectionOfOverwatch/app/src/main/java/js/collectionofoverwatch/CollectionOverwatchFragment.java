package js.collectionofoverwatch;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;

/**
 * Created by Junsuk on 2016-09-12.
 */
public class CollectionOverwatchFragment extends Fragment {

    private static final String TAG = "CollectionOverwatch";
    private RecyclerView mRecyclerView;

    public static Fragment newInstance() {
        return new CollectionOverwatchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FetchItemsAsync f = new FetchItemsAsync();
        f.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_overwatch,container,false);
        mRecyclerView =  (RecyclerView) v.findViewById(R.id.fragment_overwatch_recycleview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        return v;
    }

    private class FetchItemsAsync extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String url = "https://www.bignerdranch.com";
                String result = new YoutubeFetchr().getUrlString(url);
                byte[] utf8 = result.getBytes("UTF-8");
                result = new String(utf8, "UTF-8");
                Log.d(TAG, "SHIT");
                Log.i(TAG, "The result is " + result);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
