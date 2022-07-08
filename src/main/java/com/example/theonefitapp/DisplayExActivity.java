package com.example.theonefitapp;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DisplayExActivity extends AppCompatActivity {
    LinearLayout layout_list;
    TextView tvTotal;

    List<String> exerciseNames;
    List<String> exerciseSets;
    List<String> exerciseReps;
    List<String> exerciseWeight;
    int totalW;
    int exerciseNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_ex);
        totalW=0;
        ArrayList<String> workout = getIntent().getStringArrayListExtra("Workout");
        //linking with layout from xml
        layout_list = findViewById(R.id.exList);
        tvTotal=findViewById(R.id.tvTotal);
        //creating the lists
        exerciseNames=new ArrayList<String>();;
        exerciseSets=new ArrayList<String>();;
        exerciseReps=new ArrayList<String>();;
        exerciseWeight=new ArrayList<String>();;
        //filling the lists
        makeLists(workout);
        // making the lines
        for(int i=0;i<exerciseNumber;i++) {
            addView(i);
            //calculating the total weight lifted
            totalW+=toInt(exerciseSets.get(i)) * toInt(exerciseReps.get(i)) * toInt(exerciseWeight.get(i));
        }

        // writing the total weight lifted
        tvTotal.append(String.valueOf(totalW)+"KG");



    }

    //the function creates the lists for each category
    private void makeLists (ArrayList < String > workout) {
        exerciseNumber = workout.size() / 4;
        for (int i = 0; i < workout.size(); ++i) {
            if (i % 4 == 0) {
                exerciseNames.add(workout.get(i));
            }
            if (i % 4 == 1) {
                exerciseSets.add(workout.get(i));
            }
            if (i % 4 == 2) {
                exerciseReps.add(workout.get(i));
            }
            if (i % 4 == 3) {
                exerciseWeight.add(workout.get(i));
            }

        }
    }

    private void addView(int i) {
        // making a inflater so that number of rows in the table could increase and decrease dynamically
        View exerciseView=getLayoutInflater().inflate(R.layout.display_item_exercise,null,false);
        // connecting with every empty textView in the XML
        TextView tvName=exerciseView.findViewById(R.id.tvExName);
        TextView tvSets=exerciseView.findViewById(R.id.tvSet);
        TextView tvReps=exerciseView.findViewById(R.id.tvRep);
        TextView tvWeight=exerciseView.findViewById(R.id.tvWeight);

        // displaying the relevant data on screen
        tvName.setText(exerciseNames.get(i)+"\t");
        tvSets.setText(exerciseSets.get(i)+"\t");
        tvReps.setText(exerciseReps.get(i)+"\t");
        tvWeight.setText(exerciseWeight.get(i)+"\t");


        // Insert the row into the empty layout
        layout_list.addView(exerciseView);
    }

    //convert int to string
    public int toInt(String s){

        return Integer.parseInt(s);
    }
}
