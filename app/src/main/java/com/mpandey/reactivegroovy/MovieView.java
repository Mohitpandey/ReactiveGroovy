package com.mpandey.reactivegroovy;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class MovieView extends LinearLayout {

    TextView titleView;
    ImageView posterView;
    Context context;

    public MovieView(Context context) {
        super(context);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mainView = inflater.inflate(R.layout.movie, this);
        titleView = (TextView)mainView.findViewById(R.id.grid_item_label);
        posterView = (ImageView)mainView.findViewById(R.id.grid_item_image);
    }

    public void setMovie(Movie movie) {
        titleView.setText(movie.getTitle());
        Picasso.with(this.context).load(movie.getPosters().getProfile()).into(posterView);
    }
}