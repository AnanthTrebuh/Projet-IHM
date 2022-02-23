package fr.univ_poitiers.tpinfo.cinematech;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;

public class ItemActivity  extends AppCompatActivity {
    TextView synopsis;
    TextView realisation;
    TextView time;
    TextView actors;
    TextView dateExit;
    TextView title;
    TextView scenario;
    TextView actorPrincipal;

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
        this.actorPrincipal = findViewById(R.id.actorsPrincipalMovie);
        this.scenario = findViewById(R.id.scenarioMovie);

        this.synopsis.setText(jm.);







    }
}
