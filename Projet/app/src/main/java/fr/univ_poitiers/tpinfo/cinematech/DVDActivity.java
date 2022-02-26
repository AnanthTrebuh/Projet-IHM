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

import com.google.android.material.tabs.TabLayout;

import java.util.HashSet;
import java.util.Set;

public class DVDActivity extends AppCompatActivity {
    public static String TAG = "CineTech";
    TabLayout dvdTab;
    Button buttonMovie;
    Button buttonDvd;
    Button buttonAccount;
    Button buttonSearch;
    ViewPager viewpager;
    String precActivity;

    FragmentDvd fragmentDvd;
    FragmentDvdToBuy fragmentDvdToBuy;

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

        viewpager = findViewById(R.id.viewPagerDvd);
        dvdTab.setupWithViewPager(viewpager);
        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        fragmentDvdToBuy = new FragmentDvdToBuy();
        fragmentDvd =new FragmentDvd();

        vpAdapter.addFragment(fragmentDvdToBuy, this.getString(R.string.to_buy));
        vpAdapter.addFragment(fragmentDvd, this.getString(R.string.buy));

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Thread t1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        fragmentDvd.initList();
                    }
                });
                t1.start();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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
        startActivity(intent);
        finish();
    }

    private void back_movie(){
        Intent intent = new Intent(this, MoviesActivity.class);
        startActivity(intent);
        finish();
    }
    private void back_account(){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }
    private void action_movies_button(){
        Log.d(TAG, "action_dvd_button: ");
        Intent intent = new Intent(this, MoviesActivity.class);
        intent.putExtra("precActivity", "dvd");
        startActivity(intent);
        finish();
    }

    private void action_search_button() {
        Log.d(TAG, "action_search_button: ");
        Intent intent = new Intent(this, ResearchActivity.class);
        intent.putExtra("precActivity", "dvd");
        startActivity(intent);
        finish();
    }

    private  void action_account_button(){
        Log.d(TAG, "action_account_button: ");
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("precActivity", "dvd");
        startActivity(intent);
        finish();
    }
    /*
    @Override
    public void onStart(){
        Log.d(TAG, "onStart: dvdActivity");
        super.onStart();
    }
    @Override
    public void onResume(){
        Log.d(TAG, "onResume: dvdActivity");
        super.onResume();
    }
    @Override
    public void onPause(){
        Log.d(TAG, "onPause: dvdActivity");
        super.onPause();
    }
    @Override
    public  void onStop(){
        Log.d(TAG, "onStop: dvdActivity");
        super.onStop();
    }
    @Override
    public void onRestart(){
        Log.d(TAG, "onRestart: dvdActivity");
        super.onRestart();
    }
    @Override
    public void onDestroy(){
        Log.d(TAG, "onDestroy: dvdActivity");
        super.onDestroy();
    }*/
}