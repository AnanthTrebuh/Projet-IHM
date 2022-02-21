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
        vpAdapter.addFragment(new FragmentMovieToSee(), this.getString(R.string.to_see));
        vpAdapter.addFragment(new FragmentMovieToCome(), this.getString(R.string.to_come));
        viewpager.setAdapter(vpAdapter);
        buttonMovie.setEnabled(false);

    }

    private void action_dvd_button(){
        Log.d(TAG, "action_dvd_button: ");
        Intent intent = new Intent(this, DVDActivity.class);
        startActivity(intent);
        //this.onStop();
    }

    private  void action_account_button(){
        Log.d(TAG, "action_account_button: ");
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
}