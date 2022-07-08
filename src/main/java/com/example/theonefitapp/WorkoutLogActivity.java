package com.example.theonefitapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WorkoutLogActivity extends AppCompatActivity  {

    private static final String TAG = "Retrieved";
    private static final String WORKOUTS = "workouts";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    //create an adapter array for the db object
    private ArrayAdapter<WorkoutClass> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_log);
        String user = mAuth.getCurrentUser().getUid();

        //create a list view object for populating the workout Log
        ListView workoutListView = findViewById(R.id.lvWorkout);

        //find the list view
        //set an adapter for it
        adapter = new WorkoutAdapter(this, new ArrayList<WorkoutClass>());
        workoutListView.setAdapter(adapter);

        db.collection(WORKOUTS)
                .whereEqualTo("uid", user)
                .orderBy("Date", Query.Direction.DESCENDING)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    //shove all workouts in this list
                    ArrayList<WorkoutClass> workoutsList = new ArrayList<>();

                    for (QueryDocumentSnapshot document : task.getResult()){
                        Log.d(TAG, document.getId() + "=>" + document.getData().get("Date"));
                        WorkoutClass w = document.toObject(WorkoutClass.class);
                        workoutsList.add(w);
                    }
                    //get all workouts into the adapter as a collection of workouts
                    adapter.clear();
                    adapter.addAll(workoutsList);
                }else{
                    Log.d("CRASH", "error", task.getException());
                }
            }
        });
    }
    //######################################

    class WorkoutAdapter extends ArrayAdapter<WorkoutClass>{

        ArrayList<WorkoutClass> workouts;
        WorkoutAdapter(Context context, ArrayList<WorkoutClass> workouts){
            //call super constructor to configure things right
            super(context, 0, workouts);
            this.workouts = workouts;
            //reference for the reference of the workouts
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //first param is the view=widged for position of the list, convert view = if its not null it is created
            if (convertView == null){
                //need to build a list view
                //what we do is call LayoutInflated to create our widget and giving him our template layout
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.workout_list_item, parent, false);
            }

            //get textviews and set the data, find it in the convertView
            TextView workoutDate = convertView.findViewById(R.id.itemDate);

            //get item's, position
            WorkoutClass w = workouts.get(position);
            workoutDate.setText(w.getDate().toString());
            //upon pressing on any of the log entries we will go to another activity that displays all of the exercises in the selected workout.
            workoutDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(WorkoutLogActivity.this, DisplayExActivity.class);
                    i.putExtra("Workout",(ArrayList<String>) w.getExercise());
                    startActivity(i);
                }
            });

            Log.d(w.getUid(), "IS IT PRINTED ?!" + w.getDate().toString());
            return convertView;

        }
    }

}
