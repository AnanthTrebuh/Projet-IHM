package fr.univ_poitiers.tpinfo.cinematech;

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
    ViewPager viewpager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dvd);
        Log.d(TAG, "onCreate: begin dvd activity");
        dvdTab = findViewById(R.id.TabLayoutDvd);
        buttonMovie = findViewById(R.id.buttonMovie);
        buttonDvd = findViewById(R.id.buttonDvd);
        buttonMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action_movies_button();
            }
        });
        buttonAccount = findViewById(R.id.buttonAccount);
        buttonAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action_account_button();
            }
        });

        Log.d(TAG, "onCreate: before viewpager");

        viewpager = findViewById(R.id.viewPagerDvd);
        dvdTab.setupWithViewPager(viewpager);
        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new FragmentDvdToBuy(), this.getString(R.string.to_buy));
        vpAdapter.addFragment(new FragmentDvdToCome(), this.getString(R.string.to_come));
        viewpager.setAdapter(vpAdapter);

        buttonDvd.setEnabled(false);
        Log.d(TAG, "onCreate: DVD activity end onCreate");
    }

    private void action_movies_button(){
        Log.d(TAG, "action_dvd_button: ");
        Intent intent = new Intent(this, MoviesActivity.class);
        startActivity(intent);
    }

    private  void action_account_button(){
        Log.d(TAG, "action_account_button: ");
        //Intent intent = new Intent(this, ActivityAccount.class);
        //startActivity(intent);
    }

}