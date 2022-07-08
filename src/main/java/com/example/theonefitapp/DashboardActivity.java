package com.example.theonefitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayDeque;
import java.util.Deque;

public class DashboardActivity extends AppCompatActivity {
    //nav view
    BottomNavigationView bottomNavigationView;
    Deque<Integer> integerDeque = new ArrayDeque<>(3);
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //bottom nav view instance
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        // add home fragment in deque list
        integerDeque.push(R.id.bn_workout);
        //load the fragment
        loadFragment(new WorkoutFragment());
        //set Workout as default fragment
        bottomNavigationView.setSelectedItemId(R.id.bn_workout);
        //logic of what happens when selecting from the nav-bar menu
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        //Get selected item id
                        int id = item.getItemId();
                        // Check condition
                        if (integerDeque.contains(id)) {
                            //When deque list contains selected id
                            // check condition
                            if (id == R.id.bn_workout) {
                                // when the selected id is workout
                                //check condition
                                if (integerDeque.size() != 1) {
                                    //When deque list size is not equal to 1
                                    if (flag) {
                                        //When flag value is true Add workout fragment in deque list
                                        integerDeque.addFirst(R.id.bn_workout);
                                        flag = false;
                                    }
                                }
                            }
                            //Remove selected id from deque list
                            integerDeque.remove(id);
                        }
                        // push selected id in deque list
                        integerDeque.push(id);
                        loadFragment(getFragment(item.getItemId()));
                        //return true
                        return true;
                    }
                }
        );
    }
    //initialization of the navigation view here we would like to put our menu bar items

    private Fragment getFragment(int itemId) {
        switch (itemId){
            case R.id.bn_profile:
                //Set checked dashboard fragment
                bottomNavigationView.getMenu().getItem(0).setChecked(true);
                //return dashboard fragment
                return new ProfileFragment();
            case R.id.bn_workout:
                // Set checked workout fragment
                bottomNavigationView.getMenu().getItem(1).setChecked(true);
                //return to workouts fragment
                return new WorkoutFragment();
            case R.id.bn_measurements:
                bottomNavigationView.getMenu().getItem(2).setChecked(true);
                return new MeasuremntsFragment();
        }
        // Set checked default home fragment
        bottomNavigationView.getMenu().getItem(1).setChecked(true);
        return new WorkoutFragment();
    }

    //load the selected fragment
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, fragment, fragment.getClass().getSimpleName())
                .commit();
    }

    //utilizing the deque for the back button functionality
    @Override
    public void onBackPressed() {
        //Pop to previous fragment
        integerDeque.pop();
        if (!integerDeque.isEmpty()){
            //When deque list is not empty load fragment
            loadFragment(getFragment(integerDeque.peek()));
        } else {
            //when the deque list is empty finish activity so that we could exit the app while pressing back
            finish();
        }
    }
}