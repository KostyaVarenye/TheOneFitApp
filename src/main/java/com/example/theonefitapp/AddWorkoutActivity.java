package com.example.theonefitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddWorkoutActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout layout_list;
    Button add,save,cancel;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    List<String> exrList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);
        //linking with xml file
        layout_list=findViewById(R.id.layout_list);
        add=(Button) findViewById(R.id.button_add);
        save=(Button)findViewById(R.id.button_save);
        cancel=(Button)findViewById(R.id.button_cancel);

        //add listeners for the buttons
        add.setOnClickListener( this);
        save.setOnClickListener( this);
        cancel.setOnClickListener( this);



    }

    @Override
    public void onClick(View v){
        //if user chose the add button then we add a new view and a new line will appear
        if(v.getId()==R.id.button_add)
            addView();

        // if the user pressed save we will check that all the fields are not empty, put all of the info into an array, save the array into the FireBase
        if(v.getId()==R.id.button_save){

            //getting the inflated layers
            LinearLayout yourLinearLayoutView = (LinearLayout)findViewById(R.id.layout_list);
            //array for all input
            String[] textInput= new String[yourLinearLayoutView.getChildCount() * 4];

            //going trough each child inside the inflated layer
            for(int i=0; i < yourLinearLayoutView.getChildCount(); i++) {
                //translating the edit text of the specific layer into String
                EditText exNameI= yourLinearLayoutView.getChildAt(i).findViewById(R.id.exName);
                String exNameText = exNameI.getText().toString();
                //checking that the specific exercise name is not empty
                if (exNameText.matches("")){
                    Toast.makeText(this, "You did not enter all exercise's names", Toast.LENGTH_LONG).show();
                    break;
                }
                //inserting the name of the exercise into the array
                textInput[i*4+0]=exNameText;

                //translating the edit text of the specific layer into String
                EditText setI = yourLinearLayoutView.getChildAt(i).findViewById(R.id.set);
                String setText = setI.getText().toString();

                //checking that the specific number of sets is not empty
                if (setText.matches("")){
                    Toast.makeText(this, "You did not enter all the numbers of sets", Toast.LENGTH_LONG).show();
                    break;
                }
                //inserting the number of sets into the array
                textInput[i * 4 + 1] = setText;


                EditText repI= yourLinearLayoutView.getChildAt(i).findViewById(R.id.rep);
                String repText = repI.getText().toString();
                //checking that the specific number of reps is not empty
                if (repText.matches("")){
                    Toast.makeText(this, "You did not enter all the number of reps", Toast.LENGTH_LONG).show();
                    break;
                }
                //inserting the number of reps into the array

                textInput[i * 4 + 2] = repText;



                EditText weightI = yourLinearLayoutView.getChildAt(i).findViewById(R.id.weight);
                String weightText = weightI.getText().toString();
                //checking that the specific weight number is not empty
                if (weightText.matches("")){
                    Toast.makeText(this, "You did not enter all the weights", Toast.LENGTH_LONG).show();
                    break;
                }
                //inserting the weight into the array

                textInput[i*4+3]=weightText;


                if (i==yourLinearLayoutView.getChildCount()-1) {
                    // save to firestore
                    Toast.makeText(this, "Saving to database", Toast.LENGTH_LONG).show();
                    saveToFirestore(textInput);
                    // return to home screen
                    startActivity( new Intent(this, DashboardActivity.class));


                }

            }


        }


        if(v.getId()==R.id.button_cancel){
            //  return to home screen
            startActivity( new Intent(this, DashboardActivity.class));

        }
    }
    //method to upload workout to database
    private void saveToFirestore(String[] textInput) {
        //Converting the array into a list in order to store it
        Arrays.asList(textInput);
        //Creating a map for thedatabase
        Map<String, Object> workout = new HashMap<>();
        //Uploading the data
        workout.put("exercise", Arrays.asList(textInput));
        workout.put("Date", new Timestamp(new Date()));
        workout.put("uid", mAuth.getCurrentUser().getUid());

    // Add a new document with a generated ID
        db.collection("workouts")
                .add(workout)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("addSUCCESS", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("addFAIL", "Error adding document", e);
                        Toast.makeText(AddWorkoutActivity.this, "error: " + e.getMessage(), Toast.LENGTH_LONG);
                    }
                });


    }

    //a method that adds rows to the screen
    private void addView() {
        // making a inflater so that number of rows in the table could increase and decrease dynamically
        View exerciseView=getLayoutInflater().inflate(R.layout.add_row,null,false);

        //remove row implementation
        ImageView close= (ImageView)exerciseView.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                removeView(exerciseView);
            }
        });
        // Insert the row into the empty layout
        layout_list.addView(exerciseView);
    }

    //remove the row in screen
    private void removeView(View v) {
        layout_list.removeView(v);
    }

}