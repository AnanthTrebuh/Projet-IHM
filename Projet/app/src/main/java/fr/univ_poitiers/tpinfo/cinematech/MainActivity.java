package fr.univ_poitiers.tpinfo.cinematech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static String TAG = "CineTech";
    TabLayout movieTab;
    Button buttonMovie;
    Button buttonDvd;
    Button buttonAccount;
    ListView listview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieTab = findViewById(R.id.TabLayoutMovies);
        buttonMovie = findViewById(R.id.buttonMovie);
        buttonDvd = findViewById(R.id.buttonDvd);
        buttonDvd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action_dvd_button();
            }
        });
        buttonAccount = findViewById(R.id.buttonAccount);
        listview = findViewById(R.id.listviewMovie);

        ArrayList<Movies> movies = new ArrayList<>();
        String[] acteurs = {"jean bon", "jean michel"};
        for(int i = 0; i < 50; i++){
            Movies m1 = new Movies("test"+i, "Michel","17/02/2022", acteurs, 90);
            movies.add(m1);
        }

        ArrayAdapter<Movies> arrayAdapter = new ArrayAdapter<Movies>(this, android.R.layout.simple_list_item_1 , movies);

        listview.setAdapter(arrayAdapter);
    }

    private void action_dvd_button(){
        Log.d(TAG, "action_dvd_button: ");
        Intent intent = new Intent(this, ActivityDVD.class);
        startActivity(intent);
    }

    private  void action_account_button(){
        Log.d(TAG, "action_account_button: ");
        //Intent intent = new Intent(this, ActivityAccount.class);
        //startActivity(intent);
    }
}