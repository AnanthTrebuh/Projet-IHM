package fr.univ_poitiers.tpinfo.cinematech;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MoviesActivity extends AppCompatActivity {
    public static String TAG = "CineTech";
    TabLayout movieTab;
    Button buttonMovie;
    Button buttonDvd;
    Button buttonAccount;
    Button buttonSearch;
    ViewPager viewpager;
    String precActivity;

    private static String JSON_URL = "https://api.themoviedb.org/3/movie/157336?api_key=38cf06282c993b63af90dd9de695152f";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        movieTab = findViewById(R.id.TabLayoutMovies);
        buttonMovie = findViewById(R.id.buttonMovie);
        buttonDvd = findViewById(R.id.buttonDvd);
        buttonDvd.setOnClickListener(view -> action_dvd_button());
        buttonAccount = findViewById(R.id.buttonAccount);
        buttonAccount.setOnClickListener(view -> action_account_button());
        buttonSearch = findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(view -> back_search());

        precActivity = getIntent().getStringExtra("precActivity");
            Log.d(TAG, "onCreate: " + precActivity);


        viewpager = findViewById(R.id.viewPagerMovie);
        movieTab.setupWithViewPager(viewpager);
        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new FragmentMovieToSee(), this.getString(R.string.to_see));
        vpAdapter.addFragment(new FragmentMovieToCome(), this.getString(R.string.to_come));
        viewpager.setAdapter(vpAdapter);
        buttonMovie.setEnabled(false);

        SharedPreferences sharedPreferences = this.getSharedPreferences("CinemaTech", Context.MODE_PRIVATE );
        if(sharedPreferences != null){

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Active_Profile","default");
            Set<String> set = new HashSet<String>();
            set.add("default");
            editor.putStringSet("List_Profils",set);
            editor.putInt("default_img",R.drawable.default_user);
            editor.apply();
        }
        /*try {
            JsonReader js = new JsonReader(JSON_URL);
            js.test();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }
    @Override
    @MainThread
    public void onBackPressed(){
        if(precActivity != null){
            switch (precActivity){
                case "dvd" :  back_dvd();break;
                case "account" :back_account();break;
                case "search" : back_search();break;
                default: finish();break;
            }
        }else{
            finish();
        }
    }
    private void back_dvd(){
        Intent intent = new Intent(this, DVDActivity.class);
        finish();
        startActivity(intent);
    }
    private void back_account(){
        Intent intent = new Intent(this, ProfileActivity.class);
        finish();
        startActivity(intent);
    }
    private void action_dvd_button(){
        Log.d(TAG, "action_dvd_button: ");
        Intent intent = new Intent(this, DVDActivity.class);
        intent.putExtra("precActivity", "movie");
        startActivity(intent);
        finish();

    }

    private  void action_account_button(){
        Log.d(TAG, "action_account_button: ");
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("precActivity", "movie");
        finish();
        startActivity(intent);
    }

    private void back_search() {
        Intent intent = new Intent(this, ResearchActivity.class);
        finish();
        startActivity(intent);
    }
    private void action_search_button() {
        Log.d(TAG, "action_search_button: ");
        Intent intent = new Intent(this, ResearchActivity.class);
        intent.putExtra("precActivity", "movies");
        finish();
        startActivity(intent);
    }
}