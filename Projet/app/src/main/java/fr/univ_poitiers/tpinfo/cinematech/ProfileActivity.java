package fr.univ_poitiers.tpinfo.cinematech;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity  extends AppCompatActivity {
    public static String TAG = "CineTech";
    Button buttonMovie;
    Button buttonDvd;
    Button buttonAccount;
    TextView nbMovie;
    TextView timeSpend;
    TextView profileName;
    ImageView profilePicture;
    String precActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        this.buttonMovie = findViewById(R.id.buttonMovie);
        this.buttonDvd = findViewById(R.id.buttonDvd);
        this.buttonAccount = findViewById(R.id.buttonAccount);
        this.nbMovie = findViewById(R.id.nbMovieAdded);
        this.timeSpend = findViewById(R.id.nbTimeSPend);
        this.profileName = findViewById(R.id.profileName);
        this.profilePicture = findViewById(R.id.profilePicture);

        buttonMovie.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        action_movies_button();
                    }
                }
        );
        buttonDvd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        action_dvd_button();
                    }
                }
        );
        buttonAccount.setEnabled(false);
        SharedPreferences sharedPreferences = this.getSharedPreferences("CinemaTech", Context.MODE_PRIVATE);
        profileName.setText(sharedPreferences.getString("Active_Profile", "error"));

        
        precActivity = getIntent().getStringExtra("precActivity");

    }

    @Override
    @MainThread
    public void onBackPressed(){
        if(precActivity != null){
            switch (precActivity){
                case "dvd" :  back_dvd();break;
                case "movie" :back_movie();break;
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
    private void back_movie(){
        Intent intent = new Intent(this, MoviesActivity.class);
        finish();
        startActivity(intent);
    }


    private void action_movies_button(){
        Log.d(TAG, "action_movie_button: ");
        Intent intent = new Intent(this, MoviesActivity.class);
        startActivity(intent);
    }
    private void action_dvd_button(){
        Log.d(TAG, "action_dvd_button: ");
        Intent intent = new Intent(this, DVDActivity.class);
        startActivity(intent);
    }

}
