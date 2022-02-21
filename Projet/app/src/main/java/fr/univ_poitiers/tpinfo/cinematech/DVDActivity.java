package fr.univ_poitiers.tpinfo.cinematech;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class DVDActivity extends AppCompatActivity {
    public static String TAG = "CineTech";
    TabLayout dvdTab;
    Button buttonMovie;
    Button buttonDvd;
    Button buttonAccount;
    Button buttonSearch;
    ViewPager viewpager;
    String precActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dvd);
        Log.d(TAG, "onCreate: begin dvd activity");
        dvdTab = findViewById(R.id.TabLayoutDvd);
        buttonDvd = findViewById(R.id.buttonDvd);

        buttonMovie = findViewById(R.id.buttonMovie);
        buttonMovie.setOnClickListener(view -> action_movies_button());

        buttonSearch = findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(view -> action_search_button());
        buttonAccount = findViewById(R.id.buttonAccount);
        buttonAccount.setOnClickListener(view -> action_account_button());

        precActivity = getIntent().getStringExtra("precActivity");
        Log.d(TAG, "onCreate: " + precActivity);

        viewpager = findViewById(R.id.viewPagerDvd);
        dvdTab.setupWithViewPager(viewpager);
        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new FragmentDvdToBuy(), this.getString(R.string.to_buy));
        vpAdapter.addFragment(new FragmentDvdToCome(), this.getString(R.string.to_come));
        viewpager.setAdapter(vpAdapter);

        buttonDvd.setEnabled(false);
        Log.d(TAG, "onCreate: DVD activity end onCreate");
    }

    @Override
    @MainThread
    public void onBackPressed(){
        if(precActivity != null){
            switch (precActivity){
                case "movie" :  back_movie();break;
                case "account" : back_account();break;
                case "search" : back_search(); break;
                default: finish();break;
            }
        }else{
            finish();
        }
    }

    private void back_search() {
        Intent intent = new Intent(this, ResearchActivity.class);
        finish();
        startActivity(intent);
    }

    private void back_movie(){
        Intent intent = new Intent(this, MoviesActivity.class);
        finish();
        startActivity(intent);
    }
    private void back_account(){
        Intent intent = new Intent(this, ProfileActivity.class);
        finish();
        startActivity(intent);
    }
    private void action_movies_button(){
        Log.d(TAG, "action_dvd_button: ");
        Intent intent = new Intent(this, MoviesActivity.class);
        intent.putExtra("precActivity", "dvd");
        finish();
        startActivity(intent);
    }

    private void action_search_button() {
        Log.d(TAG, "action_search_button: ");
        Intent intent = new Intent(this, ResearchActivity.class);
        intent.putExtra("precActivity", "dvd");
        finish();
        startActivity(intent);
    }

    private  void action_account_button(){
        Log.d(TAG, "action_account_button: ");
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("precActivity", "dvd");
        finish();
        startActivity(intent);
    }

}