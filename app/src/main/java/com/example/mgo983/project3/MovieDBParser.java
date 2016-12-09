package com.example.mgo983.project3;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mgo983 on 12/5/16.
 */

public class MovieDBParser {

    private List <String[]> MovieData = new ArrayList<String[]>();



    final String MOVIEDB_POSTER_URL = "poster_path";
    final String MOVIEDB_ID = "id";
    final String MOVIEDB_ORIGINAL_TITLE = "original_title";
    final String MOVIEDB_OVERVIEW = "overview";
    final String MOVIEDB_VOTE_AVERAGE = "vote_average";
    final String MOVIEDB_RELEASE_DATE = "release_date";
    final String MOVIEDB_RESULTS = "results";

    private String getBaseUrlandPosterSize(String ConfDBdata) throws  JSONException{

        //get conf results
        JSONObject ConfData = new JSONObject(ConfDBdata);
        JSONObject imageData = ConfData.getJSONObject("images");
        String baseUrl = imageData.getString("base_url");
        String posterSize = imageData.getJSONArray("poster_sizes").getString(4);
        return baseUrl + posterSize;
    }

    public MovieDBParser(String MovieDBdata, String ConfDBdata) throws JSONException{


        List<String> movieIds = new ArrayList<String>();
        List<String> moviePosters = new ArrayList<String>();
        List<String> movieOriginaltitles = new ArrayList<String>();
        List<String> movieOverviews = new ArrayList<String>();
        List<String> movieVoteAverages= new ArrayList<String>();
        List<String> movieReleaseDates = new ArrayList<String>();




        //get the result object

        JSONObject MovieSearchResults = new JSONObject(MovieDBdata);
        JSONArray movieArray = MovieSearchResults.getJSONArray(MOVIEDB_RESULTS);

        for(int i = 0; i < movieArray.length(); i++){

            String imageFilePath = movieArray.getJSONObject(i).getString(MOVIEDB_POSTER_URL).replace("\\" , "");




            if(imageFilePath.equals("null")){

            }else{
                Log.v("IMAGE URLS ", imageFilePath + i);



                String imageId = movieArray.getJSONObject(i).getString(MOVIEDB_ID);
                String originalTitle = movieArray.getJSONObject(i).getString(MOVIEDB_ORIGINAL_TITLE);
                String overview = movieArray.getJSONObject(i).getString(MOVIEDB_OVERVIEW);
                String voteAverage = movieArray.getJSONObject(i).getString(MOVIEDB_VOTE_AVERAGE);
                String releaseDate = movieArray.getJSONObject(i).getString(MOVIEDB_RELEASE_DATE);


                movieIds.add(imageId);
                moviePosters.add(getBaseUrlandPosterSize(ConfDBdata) + imageFilePath);
                movieOriginaltitles.add(originalTitle);
                movieOverviews.add(overview);
                movieVoteAverages.add(voteAverage);
                movieReleaseDates.add(releaseDate);


            }

        }



        int ArraySize = movieIds.size();
        String[] simpleMovieIds = new String[ArraySize];
        String[] simpleMoviePosters = new String[ArraySize];
        String[] simpleMovieOriginalTitles = new String[ArraySize];
        String[] simpleMovieOverviews = new String[ArraySize];
        String[] simpleMovieVoteAverages = new String[ArraySize];
        String[] simpleMovieReleaseDates = new String[ArraySize];

        MovieData.add(moviePosters.toArray(simpleMoviePosters));
        MovieData.add(movieIds.toArray(simpleMovieIds));
        MovieData.add(movieOriginaltitles.toArray(simpleMovieOriginalTitles));
        MovieData.add(movieOverviews.toArray(simpleMovieOverviews));
        MovieData.add(movieVoteAverages.toArray(simpleMovieVoteAverages));
        MovieData.add(movieReleaseDates.toArray(simpleMovieReleaseDates));


    }



    public List<String[]> getMovieData(){
        return MovieData;
    }

}
