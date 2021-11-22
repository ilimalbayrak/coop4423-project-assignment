package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Handler;

public class ProfileActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {


    TextView movieTile,movieYear,movieRating,movieTileInfo,movieYearInfo,movieRatingInfo;
    ImageView trailerVideo,poster;

    String receivedMovie,baseUrl;
    String url,movieName,movieRatingg,movieYearr,overView,posterLink;
    Bundle name;
    int movieId;
    ProgressBar progressBar;
    RequestQueue queue1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        poster=findViewById(R.id.poster_IV);

        movieTile=findViewById(R.id.movieTitle_TV);
        movieTileInfo=findViewById(R.id.overView_TV);


        movieYear=findViewById(R.id.year_TV);
        movieYearInfo=findViewById(R.id.year2_TV);

        movieRating=findViewById(R.id.rating_TV);
        movieRatingInfo=findViewById(R.id.rating2_TV);



        name = getIntent().getExtras();
        receivedMovie = name.getString("message");
         progressBar=findViewById(R.id.progressBar);
        RequestQueue queue= Volley.newRequestQueue(this);

//https://api.themoviedb.org/3/movie/550?api_key=11b35c91685ae3ded9f38c2d48fd1a72
        url="https://api.themoviedb.org/3/search/movie?query="+receivedMovie
                +"&api_key=11b35c91685ae3ded9f38c2d48fd1a72&page=1";

        JsonObjectRequest request=new JsonObjectRequest(url,null,this,this);
        queue.add(request);



    }




    @Override
    public void onErrorResponse(VolleyError error) {


    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            progressBar.setVisibility(View.GONE);
            JSONArray results= response.getJSONArray("results");

            for (int i = 0; i <1; i++) {
                JSONObject jsonObject = results.getJSONObject(i);
                movieName=jsonObject.getString("original_title");
                overView=jsonObject.getString("overview");
                movieYearr=jsonObject.getString("release_date");
                movieRatingg=jsonObject.getString("vote_average");
                posterLink=jsonObject.getString("poster_path");
                movieId=jsonObject.getInt("id");
            }
            movieTile.setText(movieName);
            movieTileInfo.setText(overView);
            movieYearInfo.setText(movieYearr);
            movieRatingInfo.setText(movieRatingg);
            queue1=Volley.newRequestQueue(this);
            url="https://api.themoviedb.org/3/configuration?api_key=fc73b580050359be605bc05ef910989c";
            JsonObjectRequest request2=new JsonObjectRequest(url, null, response1 -> {
                JSONArray sizes;

                try {
                    JSONObject image= response1.getJSONObject("images");
                    baseUrl=image.getString("base_url");


                    Picasso.get().load(baseUrl+"original"+posterLink).into(poster);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, this);
            queue1.add(request2);

        } catch (JSONException e) {
            Toast.makeText(this, "please check your connection", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }
}