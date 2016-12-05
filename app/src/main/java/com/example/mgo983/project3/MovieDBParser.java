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

    public String[] getMoviePoster(String MovieDBdata, String ConfDBdata) throws JSONException{

        final String MOVIEDB_RESULTS = "results";
        final String MOVIEDB_POSTERURL = "poster_path";


        List<String> PosterUrls = new ArrayList<String>();

        //get the result object

        JSONObject MovieSearchResults = new JSONObject(MovieDBdata);
        JSONArray movieArray = MovieSearchResults.getJSONArray(MOVIEDB_RESULTS);


        //get conf results
        JSONObject ConfData = new JSONObject(ConfDBdata);
        JSONObject imageData = ConfData.getJSONObject("images");
        String baseUrl = imageData.getString("base_url");
        String posterSize = imageData.getJSONArray("poster_sizes").getString(4);

        for(int i = 0; i < movieArray.length(); i++){

            String imageFilePath = movieArray.getJSONObject(i).getString(MOVIEDB_POSTERURL).replace("\\" , "");

            if(imageFilePath.equals("null")){

            }else{
                Log.v("IMAGE URLS ", imageFilePath + i);
                PosterUrls.add(baseUrl + posterSize + "/" + imageFilePath);
            }



        }

        String[] simplePosterUrlsArray = new String[PosterUrls.size()];
        return PosterUrls.toArray(simplePosterUrlsArray);

    }
}
