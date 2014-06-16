package com.mpandey.reactivegroovy

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso
import groovy.transform.CompileStatic;

@CompileStatic
public class MovieActivity extends Activity {

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        Intent intent = getIntent();
        movie = (Movie) intent.getSerializableExtra("movie");

        TextView textView = (TextView) findViewById(R.id.title);
        textView.setText(movie.getTitle());

        TextView synopsisView = (TextView) findViewById(R.id.synopsis);
        synopsisView.setText(movie.getSynopsis());

        ImageView posterView = (ImageView) findViewById(R.id.poster);

        Picasso.with(this)
                .load(movie.getPosters().getProfile())
                .into(posterView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    /**
//     * A placeholder fragment containing a simple view.
//     */
//    public static class PlaceholderFragment extends Fragment {
//
//        public PlaceholderFragment() {
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.fragment_movie, container, false);
//            return rootView;
//        }
//    }

}