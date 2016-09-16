package js.collectionofoverwatch;

/**
 * Created by Junsuk on 2016-09-13.
 */

//For video model
public class YoutubeVideo {

    private String mVideoId;
    private String mTitle;
    private String mDate;
    private String mImageUrl;

    public YoutubeVideo(String videoId, String title, String date, String imageUrl) {
        mVideoId = videoId;
        mTitle = title;
        mDate = date;
        mImageUrl = imageUrl;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public String getmVideoId() {
        return mVideoId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmDate() {
        return mDate;
    }
}
