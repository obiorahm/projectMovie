package com.example.mgo983.project3;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mgo983 on 12/5/16.
 */

public class MovieDBParser {

    private String[] movieIds = {};
    private String[] moviePosters = {};
    private String baseUrlandPosterSize;

    private String getBaseUrlandPosterSize(String ConfDBdata) throws  JSONException{

        //get conf results
        JSONObject ConfData = new JSONObject(ConfDBdata);
        JSONObject imageData = ConfData.getJSONObject("images");
        String baseUrl = imageData.getString("base_url");
        String posterSize = imageData.getJSONArray("poster_sizes").getString(4);
        return baseUrl + posterSize;
    }

    public MovieDBParser(String MovieDBdata, String ConfDBdata) throws JSONException{
        final String MOVIEDB_RESULTS = "results";
        final String MOVIEDB_POSTERURL = "poster_path";
        final String MOVIEDB_ID = "id";


        List<String> PosterUrls = new ArrayList<String>();

        //get the result object

        JSONObject MovieSearchResults = new JSONObject(MovieDBdata);
        JSONArray movieArray = MovieSearchResults.getJSONArray(MOVIEDB_RESULTS);

        for(int i = 0; i < movieArray.length(); i++){

            String imageFilePath = movieArray.getJSONObject(i).getString(MOVIEDB_POSTERURL).replace("\\" , "");
            String imageId = movieArray.getJSONObject(i).getString(MOVIEDB_ID);

            if(imageFilePath.equals("null")){

            }else{
                Log.v("IMAGE URLS ", imageFilePath + i);
                PosterUrls.add(getBaseUrlandPosterSize(ConfDBdata) + "/" + imageFilePath);
            }

        }

        String[] simplePosterUrlsArray = new String[PosterUrls.size()];
        moviePosters = PosterUrls.toArray(simplePosterUrlsArray);
    }



    public String[] getMovieId() {
        return movieIds;
    }

    public String[] getMoviePoster(){
        return moviePosters;
    }
    
}
