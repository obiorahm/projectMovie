package com.example.mgo983.project3;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mgo983 on 12/4/16.
 */

public class FilmFragment extends Fragment {

    public FilmFragment(){}
    public ImageGridAdapter adapter;

    public static final String EXTRA_TEXT = "com.example.mgo983.project1.EXTRA_TEXT";

    /*public static String[] filmArray = {"http://i.imgur.com/rFLNqWI.jpg",
            "http://ab.pbimgs.com/pbimgs/ab/images/dp/wcm/201640/0079/trieste-side-chair-o.jpg",
            "http://ab.pbimgs.com/pbimgs/ab/images/dp/wcm/201640/0079/trieste-side-chair-o.jpg",
            "http://i.imgur.com/rFLNqWI.jpg"};*/

    public String[] filmArray = {};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.filmfragment, container, false);



        //ImageView imageView = (ImageView) getActivity().findViewById(R.id.film_fragment_image_view);

        //Picasso.with(getActivity()).load("http://ab.pbimgs.com/pbimgs/ab/images/dp/wcm/201640/0079/trieste-side-chair-o.jpg").intoimageView);

        //Integer[] filmArray = {R.mipmap.ic_launcher, R.mipmap.ic_launcher};

        adapter = new ImageGridAdapter(getActivity(), filmArray);

        GridView gridview = (GridView) rootView.findViewById(R.id.film_gridview);
        //ListView listView = (ListView) rootView.findViewById(R.id.film_gridview);
        gridview.setAdapter(adapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l){
                Intent detailActivityIntent = new Intent(getActivity(), MovieDetailActivity.class);
                startActivity(detailActivityIntent);
            }

        });



        return rootView;

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater){
        //inflater.inflate(R.menu.main,menu);
    }

    @Override
    public void onStart(){
        super.onStart();
        updateMovieSearch();
    }


    public void updateMovieSearch(){
        FetchMovieTask fetchMovieTask = new FetchMovieTask();
        fetchMovieTask.execute();
    }

    public class FetchMovieTask extends AsyncTask<Void, Void, String[]>{
        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

        @Override
        protected void onPostExecute(final String[] Result){
            adapter = new ImageGridAdapter(getActivity(), Result);
            GridView gridview = (GridView) getActivity().findViewById(R.id.film_gridview);
            gridview.setAdapter(adapter);

        }

        protected String getJSONData(String baseUrl, String apiKey, String lang,int movieOrConf){
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String MovieJsonStr = null;

            try{

                //final String MOVIEDB_BASE_URL = "https://api.themoviedb.org/3/search/movie?";
                //final String API_KEY = "api_key";
                //final String QUERY = "query";

                final String MOVIEDB_BASE_URL = baseUrl;
                final String API_KEY = apiKey;
                final String LANG = lang;
                Uri buildUri = null;

                String api_Key_val = "c435a65e6d1dcd430f752c8b07f77e97";

                if (movieOrConf == 0){
                    buildUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                            .appendQueryParameter(API_KEY,api_Key_val)
                            .appendQueryParameter(LANG,"en-US")
                            .build();

                }else{
                    buildUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                            .appendQueryParameter(API_KEY,api_Key_val)
                            .build();
                }


                URL url = new URL(buildUri.toString());
                Log.v(LOG_TAG, "the built Uri " + url);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null){
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null){
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0){
                    return null;
                }

                MovieJsonStr = buffer.toString();

                Log.v(LOG_TAG, "Movie JSON String " + MovieJsonStr);

                return MovieJsonStr;





            }catch (IOException e){
                Log.e(LOG_TAG, "Error ", e);
                return null;
            }finally {
                if(urlConnection != null){
                    urlConnection.disconnect();
                }
                if(reader != null){
                    try{
                        reader.close();
                    }catch (final IOException e){
                        Log.e(LOG_TAG, "Error closing stream ", e);
                    }
                }
            }
        }

        @Override
        protected String[] doInBackground(Void... params){

            String MovieJsonStr = getJSONData("https://api.themoviedb.org/3/movie/popular?",
                    "api_key", "language", 0);

            String ConfJsonStr = getJSONData("https://api.themoviedb.org/3/configuration?",
                    "api_key", "query", 1);


            try{
                    MovieDBParser ParsePosterData = new MovieDBParser(MovieJsonStr, ConfJsonStr);

                    String[] PosterData = ParsePosterData.getMoviePoster();
                    return PosterData;
                }catch (JSONException jsonex){
                    Log.e(LOG_TAG, "Error ", jsonex);
                }



            return null;

        }

    }
}
