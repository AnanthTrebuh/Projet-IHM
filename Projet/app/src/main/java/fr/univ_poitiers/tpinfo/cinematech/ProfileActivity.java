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
import android.widget.Toast;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.Set;

public class ProfileActivity  extends AppCompatActivity {
    public static String TAG = "CineTech";
    Button buttonMovie;
    Button buttonDvd;
    Button buttonAccount;
    Button buttonSearch;
    Button newProfileButton;
    Button changeProfileButton;
    Button deleteProfileButton;

    TextView nbMovie;
    TextView timeSpend;
    TextView profileName;
    TextView toSee;
    TextView seen;
    TextView dvd;
    TextView dvdAquired;
    ImageView profilePicture;
    String precActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        this.buttonMovie = findViewById(R.id.buttonMovie);
        this.buttonDvd = findViewById(R.id.buttonDvd);
        this.buttonAccount = findViewById(R.id.buttonAccount);
        this.buttonSearch = findViewById(R.id.buttonSearch);
        this.newProfileButton = findViewById(R.id.newProfile);
        this.changeProfileButton = findViewById(R.id.changeProfile);
        this.deleteProfileButton = findViewById(R.id.delProfile);

        this.nbMovie = findViewById(R.id.nbMovieAdded);
        this.timeSpend = findViewById(R.id.nbTimeSPend);
        this.profileName = findViewById(R.id.profileName);
        this.toSee = findViewById(R.id.textViewToSee);
        this.seen = findViewById(R.id.textViewSeen);
        this.dvd = findViewById(R.id.textViewDvd);
        this.dvdAquired = findViewById(R.id.textViewDvdAquired);

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
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    action_search_button();
            }
        });
        newProfileButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        action_dialog();
                    }
                }
        );

        changeProfileButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        action_dialog_change();
                    }
                }
        );

        deleteProfileButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        action_dialog_del();
                    }
                }
        );

        buttonAccount.setEnabled(false);
        actualise_profile();
        profilePicture.setImageResource(R.drawable.default_user);

        
        precActivity = getIntent().getStringExtra("precActivity");

    }

    @Override
    @MainThread
    public void onBackPressed(){
        if(precActivity != null){
            switch (precActivity){
                case "dvd" :  back_dvd();break;
                case "movie" :back_movie();break;
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
    private void back_movie(){
        Intent intent = new Intent(this, MoviesActivity.class);
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
        intent.putExtra("precActivity", "account");
        startActivity(intent);
        finish();
    }

    private void action_movies_button(){
        Log.d(TAG, "action_movie_button: ");
        Intent intent = new Intent(this, MoviesActivity.class);
        intent.putExtra("precActivity", "account");
        startActivity(intent);
        finish();
    }
    private void action_dvd_button(){
        Log.d(TAG, "action_dvd_button: ");
        Intent intent = new Intent(this, DVDActivity.class);
        intent.putExtra("precActivity", "account");
        startActivity(intent);
        finish();
    }

    private void action_dialog()  {
        DialoProfileActivity.FullNameListener listener = new DialoProfileActivity.FullNameListener() {
            @Override
            public void fullNameEntered(String fullName) {
                SharedPreferences sharedPreferences = getSharedPreferences("CinemaTech", Context.MODE_PRIVATE );
                SharedPreferences.Editor e = sharedPreferences.edit();
                Set<String> set = sharedPreferences.getStringSet("List_Profils", new HashSet<String>());
                set.add(fullName);
                e.putStringSet("List_Profils", set);
                e.putString("Active_Profile", fullName);

                Set<String> movieList = new HashSet<>();
                for (int i = 1; i < 10; i++){
                    movieList.add("76874"+String.valueOf(i));
                }

                e.putStringSet(fullName+"_movie",movieList);
                e.putStringSet(fullName+"_movie_seen",  new HashSet<String>());
                e.putStringSet(fullName+"_dvd",  new HashSet<String>());
                e.putStringSet(fullName+"_dvd_buy",  new HashSet<String>());

                e.apply();
                actualise_profile();
            }
        };
        final DialoProfileActivity dialog = new DialoProfileActivity(this, listener);

        dialog.show();
    }

    private void action_dialog_change(){
        DialoProfileChangeActivity.ChangeNameListener listener = new DialoProfileChangeActivity.ChangeNameListener() {
            @Override
            public void fullNameSelected(String fullName){
                SharedPreferences sharedPreferences = getSharedPreferences("CinemaTech", Context.MODE_PRIVATE );
                SharedPreferences.Editor e = sharedPreferences.edit();
                e.putString("Active_Profile", fullName);
                e.commit();
                actualise_profile();
            }
        };
        SharedPreferences sharedPreferences = getSharedPreferences("CinemaTech", Context.MODE_PRIVATE );

        final DialoProfileChangeActivity dialog = new DialoProfileChangeActivity(
                this,
                listener,
                (HashSet<String>) sharedPreferences.getStringSet("List_Profils", new HashSet<String>())
        );
        dialog.show();
    }

    private void action_dialog_del(){
        SharedPreferences sharedPreferences = getSharedPreferences("CinemaTech", Context.MODE_PRIVATE );
        Set<String> profils = new HashSet<>(sharedPreferences.getStringSet("List_Profils", new HashSet<String>()));
        if(profils.size() <= 1){
            Toast.makeText(this, "Need more than one profile", Toast.LENGTH_SHORT).show();
        }
        else {
            DialoProfileChangeActivity.ChangeNameListener listener = new DialoProfileChangeActivity.ChangeNameListener() {
                @Override
                public void fullNameSelected(String fullName) {
                    SharedPreferences sharedPreferences = getSharedPreferences("CinemaTech", Context.MODE_PRIVATE);
                    SharedPreferences.Editor e = sharedPreferences.edit();
                    Set<String> profils = new HashSet<>(sharedPreferences.getStringSet("List_Profils", new HashSet<String>()));
                    profils.remove(fullName);
                    e.remove(fullName + "_movie");
                    e.remove(fullName + "_movie_seen");
                    e.remove(fullName + "_dvd");
                    e.remove(fullName + "_dvd_buy");
                    e.putStringSet("List_Profils", profils);

                    if (fullName.equals(sharedPreferences.getString("Active_Profile", ""))) {
                        e.putString("Active_Profile", profils.stream().findFirst().get());
                    }

                    e.commit();
                    actualise_profile();
                }
            };

            final DialoProfileChangeActivity dialog = new DialoProfileChangeActivity(
                    this,
                    listener,
                    (HashSet<String>) sharedPreferences.getStringSet("List_Profils", new HashSet<String>())
            );
            dialog.show();
        }
    }

    private void actualise_profile(){
        SharedPreferences sharedPreferences = getSharedPreferences("CinemaTech", Context.MODE_PRIVATE );
        String pName = sharedPreferences.getString("Active_Profile","error");
        profileName.setText(pName);
        int movie = sharedPreferences.getStringSet(pName+"_movie", new HashSet<String>()).size();
        int movie_seen = sharedPreferences.getStringSet(pName+"_movie_seen", new HashSet<String>()).size();
        int nbDVD =sharedPreferences.getStringSet(pName+"_dvd",new HashSet<String>()).size();
        int nbDVDAquired = sharedPreferences.getStringSet(pName+"_dvd_buy",new HashSet<String>()).size();
        nbMovie.setText( Integer.toString(movie + movie_seen));
        toSee.setText( Integer.toString(movie));
        seen.setText( Integer.toString(movie_seen));

        dvd.setText(Integer.toString(nbDVD));
        dvdAquired.setText(Integer.toString(nbDVDAquired));

    }

}
