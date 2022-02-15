package fr.univ_poitiers.tpinfo.cinematech;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    TabLayout movieTab;
    TabLayout optionTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieTab = findViewById(R.id.TabLayoutMovies);
        optionTab = findViewById(R.id.tabLayoutOption);


    }
}