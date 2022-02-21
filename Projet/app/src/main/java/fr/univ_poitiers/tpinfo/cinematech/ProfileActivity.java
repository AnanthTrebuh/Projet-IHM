package fr.univ_poitiers.tpinfo.cinematech;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity  extends AppCompatActivity {
    Button buttonMovie;
    Button buttonDvd;
    Button buttonAccount;
    Button buttonExit;
    TextView nbMovie;
    TextView timeSpend;
    TextView profileName;
    ImageView profilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        this.buttonMovie = findViewById(R.id.buttonMovie);
        this.buttonDvd = findViewById(R.id.buttonDvd);
        this.buttonAccount = findViewById(R.id.buttonAccount);
        this.buttonExit = findViewById(R.id.buttonExit);
        this.nbMovie = findViewById(R.id.nbMovieAdded);
        this.timeSpend = findViewById(R.id.nbTimeSPend);
        this.profileName = findViewById(R.id.profileName);
        this.profilePicture = findViewById(R.id.profilePicture);

        buttonExit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }
        );
        buttonDvd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        action_movies_button();
                    }
                }
        );
        buttonMovie.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        action_dvd_button();
                    }
                }
        );
        buttonAccount.setEnabled(false);


    }

    private void action_movies_button(){
        Log.d(TAG, "action_movie_button: ");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void action_dvd_button(){
        Log.d(TAG, "action_dvd_button: ");
        Intent intent = new Intent((this, ActivityDVD.class));
        startActivity(intent);
    }

}
