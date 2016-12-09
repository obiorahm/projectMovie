package com.example.mgo983.project3;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by mgo983 on 12/5/16.
 */

public class MovieDetailActivity extends ActionBarActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailactivity);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.det_frame, new PlaceholderFragment())
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View layout = inflater.inflate(R.layout.detailfragment, container, false);

            //ViewGroup layout = (ViewGroup) getActivity().findViewById(R.id.det_frame);

            Intent intent = getActivity().getIntent();
            String movieTitle = intent.getStringExtra(FilmFragment.TITLE_TEXT);


            Picasso
                    .with(getActivity())
                    .load(intent.getStringExtra(FilmFragment.EXTRA_TEXT))
                    .into((ImageView) layout.findViewById(R.id.det_poster));

            TextView textView = (TextView) layout.findViewById(R.id.det_title);
            textView.setText(movieTitle);



            TextView movieOverview = (TextView) layout.findViewById(R.id.det_overview);
            movieOverview.setText(intent.getStringExtra(FilmFragment.OVERVIEW_TEXT));


            TextView voteAverages = (TextView) layout.findViewById(R.id.det_vote_averages);
            voteAverages.setText(intent.getStringExtra(FilmFragment.VOTE_AVERAGES_TEXT));


            TextView releaseDates = (TextView) layout.findViewById(R.id.det_release_date);
            releaseDates.setText(intent.getStringExtra(FilmFragment.RELEASE_DATE_TEXT));

            getActivity().setTitle(movieTitle);

            return layout;
        }
    }
}
