package fr.univ_poitiers.tpinfo.cinematech;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.android.material.tabs.TabLayout;

public class ResearchActivity extends AppCompatActivity {

    public static String TAG = "CineTech";
    Button buttonMovie;
    Button buttonDvd;
    Button buttonAccount;
    Button buttonSearch;
    SearchView searchBar;
    ListView listview;
    String precActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_research);

        searchBar = findViewById(R.id.searchView);
        listview = findViewById(R.id.listView);

        buttonDvd = findViewById(R.id.buttonDvd);
        buttonDvd.setOnClickListener(view -> action_dvd_button());

        buttonMovie = findViewById(R.id.buttonMovie);
        buttonMovie.setOnClickListener(view -> action_movies_button());

        buttonSearch = findViewById(R.id.buttonSearch);

        buttonAccount = findViewById(R.id.buttonAccount);
        buttonAccount.setOnClickListener(view -> action_account_button());

        precActivity = getIntent().getStringExtra("precActivity");
        Log.d(TAG, "onCreate: precActivity : " + precActivity);

        buttonSearch.setEnabled(false);
    }

    @Override
    @MainThread
    public void onBackPressed(){
        if(precActivity != null){
            switch (precActivity){
                case "movie" :  back_movie();break;
                case "account" : back_account();break;
                case "dvd" : back_dvd(); break;
                default: finish();break;
            }
        }else{
            finish();
        }
    }

    private void back_dvd() {
        Intent intent = new Intent(this, DVDActivity.class);
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
        intent.putExtra("precActivity", "search");
        finish();
        startActivity(intent);
    }

    private void action_dvd_button() {
        Log.d(TAG, "action_search_button: ");
        Intent intent = new Intent(this, DVDActivity.class);
        intent.putExtra("precActivity", "search");
        finish();
        startActivity(intent);
    }

    private  void action_account_button(){
        Log.d(TAG, "action_account_button: ");
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("precActivity", "search");
        finish();
        startActivity(intent);
    }
}