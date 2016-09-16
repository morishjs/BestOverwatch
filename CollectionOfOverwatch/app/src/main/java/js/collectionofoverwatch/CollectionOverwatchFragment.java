package js.collectionofoverwatch;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Junsuk on 2016-09-12.
 */
public class CollectionOverwatchFragment extends Fragment {

    private static final String TAG = "CollectionOverwatch";
    private static final String APPID = "AIzaSyBYp1u6jOKf-Q_6NuZzG9uJEOWA5i_E4UQ";

    private RecyclerView mRecyclerView;
    //Video model
    private ArrayList<YoutubeVideo> mVideos = new ArrayList<>();

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
        setAdapter();
        return v;
    }

    public void setAdapter(){
        if (isAdded()) {
             mRecyclerView.setAdapter(new YoutubeAdapter(mVideos));
        }
    }

    private class FetchItemsAsync extends AsyncTask<Void,Void,ArrayList<YoutubeVideo>>{


        @Override
        protected ArrayList<YoutubeVideo> doInBackground(Void... voids) {
            String result=null;
            try {
                String url = Uri.parse("https://www.googleapis.com/youtube/v3/search")
                        .buildUpon()
                        .appendQueryParameter("part", "snippet")
                        .appendQueryParameter("q", "Overwatch")
                        .appendQueryParameter("key", APPID)
                        .appendQueryParameter("maxResults", "3")
                        .build().toString();

                result = YoutubeFetchr.getUrlString(url);



            } catch (IOException e) {
                e.printStackTrace();
            }
            return YoutubeFetchr.searchVideos(result);
        }

        @Override
        protected void onPostExecute(ArrayList<YoutubeVideo> youtubeVideos) {
            mVideos = youtubeVideos;
            setAdapter();
        }


    }

    private class YoutubeDataHolder extends RecyclerView.ViewHolder {
        private ImageView mVideo;
        private TextView mTitle;
        private TextView mDate;

        public YoutubeDataHolder(View itemView) {
            super(itemView);
            mVideo = (ImageView) itemView.findViewById(R.id.imageView);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mDate = (TextView) itemView.findViewById(R.id.description);

        }

        public void bindItem(YoutubeVideo item) {
            //mVideo.setImageDrawable
            // ;
            final Handler handler = new Handler();
            final String imageUrl = item.getmImageUrl();
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(imageUrl);
                        InputStream is = url.openStream();
                        final Bitmap bitmap = BitmapFactory.decodeStream(is);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                mVideo.setImageBitmap(bitmap);
                            }
                        });
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            t.start();
            mTitle.setText(item.getmTitle());
            mDate.setText(item.getmDate());

        }
    }

    private class YoutubeAdapter extends RecyclerView.Adapter<YoutubeDataHolder> {

        ArrayList<YoutubeVideo> videos;

        public YoutubeAdapter(ArrayList<YoutubeVideo> videos) {
            this.videos = videos;
        }

        @Override
        public YoutubeDataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View v = inflater.inflate(R.layout.video_item, parent, false);
            return new YoutubeDataHolder(v);
        }

        @Override
        public void onBindViewHolder(YoutubeDataHolder holder, int position) {
            YoutubeVideo video = mVideos.get(position);
            holder.bindItem(video);
        }

        @Override
        public int getItemCount() {
            return videos.size();
        }
    }







}
