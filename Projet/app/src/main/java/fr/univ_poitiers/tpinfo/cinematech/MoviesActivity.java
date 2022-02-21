package fr.univ_poitiers.tpinfo.cinematech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MoviesActivity extends AppCompatActivity {
    public static String TAG = "CineTech";
    TabLayout movieTab;
    Button buttonMovie;
    Button buttonDvd;
    Button buttonAccount;
    ViewPager viewpager;
    ListView listview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

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
        buttonAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action_account_button();
            }
        });

        viewpager = findViewById(R.id.viewPagerMovie);
        movieTab.setupWithViewPager(viewpager);
        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new FragmentMovieToCome(), "To Come");
        vpAdapter.addFragment(new FragmentMovie(), "To See");
        viewpager.setAdapter(vpAdapter);

        /*listview = findViewById(R.id.listviewMovie);
        ArrayList<Movies> movies = new ArrayList<>();
        String[] acteurs = {"jean bon", "jean michel"};
        for(int i = 0; i < 50; i++){
            Movies m1 = new Movies("test"+i, "Michel","17/02/2022", acteurs, 90);
            movies.add(m1);
        }
        ArrayAdapter<Movies> arrayAdapter = new ArrayAdapter<Movies>(this, android.R.layout.simple_list_item_1 , movies);
        listview.setAdapter(arrayAdapter);*/


        buttonMovie.setEnabled(false);

    }

    private void action_dvd_button(){
        Log.d(TAG, "action_dvd_button: ");
        Intent intent = new Intent(this, DVDActivity.class);
        startActivity(intent);
        this.onStop();
    }

    private  void action_account_button(){
        Log.d(TAG, "action_account_button: ");
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
}