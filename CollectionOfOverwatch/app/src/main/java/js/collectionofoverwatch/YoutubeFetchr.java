package js.collectionofoverwatch;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Junsuk on 2016-09-12.
 */
public class YoutubeFetchr {
    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        InputStream is = connection.getInputStream();

        if(connection.getResponseCode() != HttpURLConnection.HTTP_OK){
            throw new IOException("HttpConnection is failed, get this error code" + connection.getResponseCode());
        }
        else{
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = is.read(buffer)) > 0) {
                os.write(buffer, 0, buffer.length);
            }
            os.close();
            connection.disconnect();

            return os.toByteArray();

        }

    }
    public String getUrlString(String urlSpec) throws IOException {

        return new String(getUrlBytes(urlSpec));
    }
}
