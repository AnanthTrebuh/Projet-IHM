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
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import java.util.HashSet;
import java.util.Set;

public class MoviesActivity extends AppCompatActivity {
    /*Variables for json*/
    public static final String KEY = "?api_key=38cf06282c993b63af90dd9de695152f";
    public static final String URL_ID_MOVIE = "https://api.themoviedb.org/3/movie/";
    public static final String URL_TITLE_MOVIE =  "https://api.themoviedb.org/3/search/movie";
    RequestQueue queue;

    public static String TAG = "CineTech";
    TabLayout movieTab;
    Button buttonMovie;
    Button buttonDvd;
    Button buttonAccount;
    Button buttonSearch;
    ViewPager viewpager;
    String precActivity;
    FragmentMovie fragmentMovie;
    FragmentMovieToSee fragmentMovieToSee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        Log.d(TAG, "onCreate: movieActivity");
        queue = Volley.newRequestQueue(this);
        
        movieTab = findViewById(R.id.TabLayoutMovies);
        buttonMovie = findViewById(R.id.buttonMovie);
        buttonDvd = findViewById(R.id.buttonDvd);
        buttonDvd.setOnClickListener(view -> action_dvd_button());
        buttonAccount = findViewById(R.id.buttonAccount);
        buttonAccount.setOnClickListener(view -> action_account_button());
        buttonSearch = findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(view -> action_search_button());

        SharedPreferences sharedPreferences = this.getSharedPreferences("CinemaTech", Context.MODE_PRIVATE );
        if(!sharedPreferences.contains("Active_Profile")){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Active_Profile","default");
            Set<String> set = new HashSet<>();
            set.add("default");
            editor.putStringSet("List_Profils",set);
            editor.putInt("default_img",R.drawable.default_user);
            editor.apply();
        }
        precActivity = getIntent().getStringExtra("precActivity");
            Log.d(TAG, "onCreate: " + precActivity);

        viewpager = findViewById(R.id.viewPagerMovie);
        movieTab.setupWithViewPager(viewpager);
        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        fragmentMovie = new FragmentMovie();
        fragmentMovieToSee = new FragmentMovieToSee();

        vpAdapter.addFragment(new FragmentMovieToSee(), this.getString(R.string.to_see));
        vpAdapter.addFragment(fragmentMovie, this.getString(R.string.seen));

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                              @Override
                                              public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                                              }

                                              @Override
                                              public void onPageSelected(int position) {
                                                  Thread t1 = new Thread(new Runnable() {
                                                      @Override
                                                      public void run() {
                                                          fragmentMovie.initList();
                                                      }
                                                  });
                                                  t1.start();
                                              }

                                              @Override
                                              public void onPageScrollStateChanged(int state) {

                                              }
                                          }
        );

        viewpager.setAdapter(vpAdapter);
        buttonMovie.setEnabled(false);


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
        startActivity(intent);
        finish();
    }
    private void back_account(){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        finish();
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
        startActivity(intent);
        finish();
    }

    private void back_search() {
        Intent intent = new Intent(this, ResearchActivity.class);
        startActivity(intent);
        finish();
    }
    private void action_search_button() {
        Log.d(TAG, "action_search_button: ");
        Intent intent = new Intent(this, ResearchActivity.class);
        intent.putExtra("precActivity", "movies");
        startActivity(intent);
        finish();
    }

    public RequestQueue getQueue(){
        return this.queue;
    }

    @Override
    public void onStart(){
        Log.d(TAG, "onStart: movieActivity");
        super.onStart();
    }
    @Override
    public void onResume(){
        Log.d(TAG, "onResume: movieActivity");
        super.onResume();
    }
    @Override
    public void onPause(){
        Log.d(TAG, "onPause: movieactivity");
        super.onPause();
    }
    @Override
    public  void onStop(){
        Log.d(TAG, "onStop: movieActyivity");
        super.onStop();
    }
    @Override
    public void onRestart(){
        Log.d(TAG, "onRestart: movieActivity");
        super.onRestart();
    }
    @Override
    public void onDestroy(){
        Log.d(TAG, "onDestroy: movieActivity");
        super.onDestroy();
    }
}