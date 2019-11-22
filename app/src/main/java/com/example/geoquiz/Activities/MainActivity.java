package com.example.geoquiz.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.geoquiz.AsyncTasks.InsertCountriesAsyncTask;
import com.example.geoquiz.Database.AppDatabase;
import com.example.geoquiz.Fragments.LearningFragment;
import com.example.geoquiz.Fragments.ProfileFragment;
import com.example.geoquiz.Fragments.QuizFragment;
import com.example.geoquiz.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set the title of the page on load to GeoQuiz
        setTitle("GeoQuiz");

        //fill the database with countries
        InsertCountriesAsyncTask insertCountriesAsyncTask = new InsertCountriesAsyncTask();
        insertCountriesAsyncTask.setContext(getApplicationContext());
        insertCountriesAsyncTask.execute();


        // I want there to be a Fragment in the slot from the start
        swapFragment(launchQuiz(getString(R.string.flag_quiz)));

        //setting up the bottom nav view
        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                // menuItem = the item on the bottom nav view that was selected
                // The id's here belong to the items in the menu of the BottomnNavigationView
                // The menu is chunked out as bottom_nav_menu.xml in the res > menu folder
                if (menuItem.getItemId() == R.id.nav_flag_quiz) {
                    //Todo transistions for fragments
                    swapFragment(launchQuiz(getString(R.string.flag_quiz)));
                    setTitle("Flags Quiz");
                    return true;
                } else if (menuItem.getItemId() == R.id.nav_learning) {
                    setTitle("Learn about the countries here!");
                    swapFragment(new LearningFragment());
                    return true;
                } else if (menuItem.getItemId() == R.id.nav_city_quiz) {
                    swapFragment(launchQuiz(getString(R.string.cities_quiz)));
                    return true;
                }else if (menuItem.getItemId() == R.id.nav_profile) {
                    setTitle("Profile");
                    swapFragment(new ProfileFragment());
                    return true;
                }

                return false;
            }
        });


    }

    /**
     * Helper method to change the fragment displayed in the activity. We put this here so we don't
     * have to repeat the code every time we want to saw
     * @param fragment: instance of the fragment to go into the slot
     */
    private void swapFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_slot, fragment);
        fragmentTransaction.commit();
    }

    private Fragment launchQuiz(String quizType) {
        Fragment quizFragment = new QuizFragment();
        Bundle bundle = new Bundle();
        bundle.putString("quizType", quizType);
        // set Fragment Arguments
        quizFragment.setArguments(bundle);
        swapFragment(quizFragment);
        setTitle(quizType);
        return quizFragment;
    }



}

