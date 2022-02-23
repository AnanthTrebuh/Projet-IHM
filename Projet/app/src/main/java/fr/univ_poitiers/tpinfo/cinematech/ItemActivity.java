package fr.univ_poitiers.tpinfo.cinematech;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ItemActivity  extends AppCompatActivity {
    TextView synopsis;
    TextView realisation;
    TextView time;
    TextView actors;
    TextView dateExit;
    TextView title;
    TextView scenario;

    Button addToWatch;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String id = (String)savedInstanceState.get("movie");
        JsonMovie jm = new JsonMovie(id, queue, false);
        this.synopsis = findViewById(R.id.synopsisMovie);
        this.realisation = findViewById(R.id.realizationMovie);
        this.time = findViewById(R.id.timeMovie);
        this.actors = findViewById(R.id.actorsMovie);
        this.dateExit = findViewById(R.id.dateExitMovie);
        this.title = findViewById(R.id.titleMovie);
        this.scenario = findViewById(R.id.scenarioMovie);
        this.addToWatch = findViewById(R.id.buttonAddListMovie);

        addToWatch.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferences sharedPreferences = getSharedPreferences("CinemaTech", Context.MODE_PRIVATE );
                        SharedPreferences.Editor e = sharedPreferences.edit();
                        String name = sharedPreferences.getString("Active_Profile","default");
                        Set<String> movieList = sharedPreferences.getStringSet(name+"_movie", new HashSet<String>());
                        try {
                            movieList.add(jm.getTitle());
                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }
                        e.putStringSet(name+"_movie", movieList);
                        e.apply();

                    }
                }
        );

        try {
            this.synopsis.setText(jm.getOverview());
            this.realisation.setText(jm.getRealisator());
            this.time.setText(jm.getRunTime());
            ArrayList<String> actors = jm.getCharacters();
            String actorsString = "";
            for(String s : actors)
                actorsString += ", " + s;
            this.actors.setText(actorsString);
            this.dateExit.setText(jm.getReleaseDate());
            this.title.setText(jm.getTitle());
            this.scenario.setText(jm.getWriter());

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
