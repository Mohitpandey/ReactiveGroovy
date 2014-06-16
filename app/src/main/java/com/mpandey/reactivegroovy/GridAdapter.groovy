package com.mpandey.reactivegroovy;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso
import groovy.transform.CompileStatic;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

import java.util.ArrayList;
import java.util.List;

@CompileStatic
class GridAdapter extends BaseAdapter {

    private Context context;
    private static final NetworkManager networkManager = new NetworkManager();
    private List<Movie> movies = new ArrayList<Movie>();
    private Subscription subscription;

    public GridAdapter(Context context, String source) {
        this.context = context;
        Log.e(this.getClass().getName(),"new grid adapter");

        subscription = networkManager.getData(source)
                .flatMap({new FeedParser().getMovies(it)})
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorFlatMap({e -> return Observable.empty()})
                .subscribe({Movies m ->
                    movies.clear();
                    movies.addAll(m.movies);
                    notifyDataSetChanged();
                });
    }

    public int getCount() {
        return movies.size();
    }

    public Movie getItem(int position) {
        return movies.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        MovieView movieView;
        if (convertView == null) {
            movieView = new MovieView(this.context);
        } else {
            movieView = (MovieView)convertView;
        }
        movieView.setMovie(getItem(position));
        return movieView;
    }
}