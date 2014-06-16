package com.mpandey.reactivegroovy

import android.util.Log;
import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.OkHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.io.IOUtils;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

import dagger.Module;
import dagger.Provides;
import rx.util.async.Async;

import javax.inject.Inject;
import javax.inject.Singleton;

public class NetworkManager {

    private OkHttpClient client = new OkHttpClient();

    private static final String TAG = NetworkManager.class.getName();

    public NetworkManager() {
    }

    public Observable<String> getData(final String url) {
        return  Async.start({getContent(url)},Schedulers.io());
    }

    private String getContent(String urlString) {
        try {
            HttpURLConnection connection = client.open(new URL(urlString));
            Log.d(TAG,"making connection : "+urlString);
            byte [] bytes = readData(connection.getInputStream(), connection);
            return new String(bytes, "UTF-8");
        } catch (Exception e) {
            Log.e(TAG,"What!! be specific in exception handling",e);
            //return "{movies:[]}";
            throw new RuntimeException("test");
        }
    }

    private byte[] readData(InputStream inputStream, HttpURLConnection connection) throws IOException {
        ByteArrayOutputStream outputStream = null;

        try {
            byte[] data = new byte[0];
            final int contentLength = connection.getContentLength();
            if (contentLength > 0) {
                outputStream = new ByteArrayOutputStream(contentLength);
            } else {
                outputStream = new ByteArrayOutputStream();
            }
            IOUtils.copy(inputStream, outputStream);

            data = outputStream.toByteArray();
            return data;
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        }
    }
}