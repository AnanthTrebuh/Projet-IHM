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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

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
        /*
        searchBar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if( hasFocus ){
                    buttonAccount.setVisibility(View.INVISIBLE);
                    buttonDvd.setVisibility(View.INVISIBLE);
                    buttonMovie.setVisibility(View.INVISIBLE);
                    buttonSearch.setVisibility(View.INVISIBLE);
                }
                else{
                    if (buttonMovie.getVisibility() == View.INVISIBLE){
                        buttonAccount.setVisibility(View.VISIBLE);
                        buttonMovie.setVisibility(View.VISIBLE);
                        buttonDvd.setVisibility(View.VISIBLE);
                        buttonSearch.setVisibility(View.VISIBLE);
                    }
                }
            }
        } );
        */
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                action_research();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
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

    private void action_research(){
        String s = this.searchBar.getQuery().toString();
        s = s.replaceAll("\\s+","+");
        Toast toast = Toast.makeText(this, s, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void onStart(){
        Log.d(TAG, "onStart: searchActivity");
        super.onStart();
    }
    @Override
    public void onResume(){
        Log.d(TAG, "onResume: searchActivity");
        super.onResume();
    }
    @Override
    public void onPause(){
        Log.d(TAG, "onPause: searchActivity");
        super.onPause();
    }
    @Override
    public  void onStop(){
        Log.d(TAG, "onStop: searchActivity");
        super.onStop();
    }
    @Override
    public void onRestart(){
        Log.d(TAG, "onRestart: searchActivity");
        super.onRestart();
    }
    @Override
    public void onDestroy(){
        Log.d(TAG, "onDestroy: searchActivity");
        super.onDestroy();
    }
}