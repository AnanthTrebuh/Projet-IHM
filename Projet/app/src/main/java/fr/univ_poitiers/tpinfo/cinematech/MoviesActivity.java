package fr.univ_poitiers.tpinfo.cinematech;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;


public class MoviesActivity extends AppCompatActivity {
    public static String TAG = "CineTech";
    TabLayout movieTab;
    Button buttonMovie;
    Button buttonDvd;
    Button buttonAccount;
    ViewPager viewpager;
    String precActivity;

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

        precActivity = getIntent().getStringExtra("precActivity");
            Log.d(TAG, "onCreate: " + precActivity);


        viewpager = findViewById(R.id.viewPagerMovie);
        movieTab.setupWithViewPager(viewpager);
        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new FragmentMovieToSee(), this.getString(R.string.to_see));
        vpAdapter.addFragment(new FragmentMovieToCome(), this.getString(R.string.to_come));
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
}