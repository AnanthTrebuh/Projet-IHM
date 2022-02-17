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

public class ActivityDVD extends AppCompatActivity {
    public static String TAG = "CineTech";
    TabLayout movieTab;
    Button buttonMovie;
    Button buttonDvd;
    Button buttonAccount;
    ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dvd);

        movieTab = findViewById(R.id.TabLayoutMovies);
        buttonMovie = findViewById(R.id.buttonMovie);
        buttonDvd = findViewById(R.id.buttonDvd);
        buttonMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action_movies_button();
            }
        });
        buttonAccount = findViewById(R.id.buttonAccount);
        listview = findViewById(R.id.listviewDvd);

        ArrayList<Dvd> dvds = new ArrayList<>();
        String[] acteurs = {"jean bon", "jean michel"};
        for(int i = 0; i < 50; i++){
            Dvd m1 = new Dvd("testDvd"+i, "Michel","17/02/2022", acteurs, 90, false);
            dvds.add(m1);
        }

        ArrayAdapter<Dvd> arrayAdapter = new ArrayAdapter<Dvd>(this, android.R.layout.simple_list_item_1 , dvds);

        listview.setAdapter(arrayAdapter);
    }

    private void action_movies_button(){
        Log.d(TAG, "action_dvd_button: ");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private  void action_account_button(){
        Log.d(TAG, "action_account_button: ");
        //Intent intent = new Intent(this, ActivityAccount.class);
        //startActivity(intent);
    }

}