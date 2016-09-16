package js.collectionofoverwatch;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Junsuk on 2016-09-12.
 */
public class YoutubeFetchr {


    public static String getUrlBytes(String urlSpec) throws IOException {

        URL url = new URL(urlSpec);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        InputStream inputStream = conn.getInputStream();


        StringBuffer sb = new StringBuffer();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        BufferedInputStream is = new BufferedInputStream(conn.getInputStream(),"UTF-8");

        if(conn.getResponseCode() != HttpURLConnection.HTTP_OK){
            throw new IOException("HttpConnection is failed, get this error code" + conn.getResponseCode());
        }
        else{
//            int bytesRead = 0;
//            byte[] buffer = new byte[1024];
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();
            conn.disconnect();

//            return os.toByteArray();
            return sb.toString();

        }

    }
    public static String getUrlString(String urlSpec) throws IOException {

        return getUrlBytes(urlSpec);
    }



    public static ArrayList<YoutubeVideo> searchVideos(String result) {
        String mVideoId;
        String mTitle;
        String mDate;
        ArrayList<YoutubeVideo> videoList = new ArrayList<>();
        try {
            JSONObject jsonObj = new JSONObject(result);
            JSONArray jsonArr = jsonObj.getJSONArray("items");
            for(int i =0; i<jsonArr.length();i++) {
                JSONObject obj = jsonArr.getJSONObject(i);
                String kind = obj.getJSONObject("id").getString("kind");
                if (kind.equals("youtube#video")) { //video 에 대해서만 취급
                    //동영상 재생시 반드시 필요한 videoId
                    mVideoId=obj.getJSONObject("id").getString("videoId");
                    mTitle = obj.getJSONObject("snippet").getString("title");
                    mDate = obj.getJSONObject("snippet").getString("publishedAt").substring(0,10);
                    videoList.add(new YoutubeVideo(mVideoId,mTitle,mDate));
                }
                else continue;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        finally {
            return videoList;
        }
    }
}
