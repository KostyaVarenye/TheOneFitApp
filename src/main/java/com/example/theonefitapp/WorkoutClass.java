package com.example.theonefitapp;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WorkoutClass {
    String uid;
    private List<String> exercise;
    private Date Date;
    List<String> exerciseNames;
    List<String> exerciseSets;
    List<String> exerciseReps;
    List<String> exerciseWeight;
    int exerciseNumber;

    // No-argument constructor is required to support conversion of Firestore document to WorkoutObj
    public WorkoutClass() {}

    // All-argument constructor is required to support conversion of Firestore document to WorkoutObj
    public WorkoutClass(Date Date, List<String> exercise, String uid) {
        this.exercise = exercise;
        this.uid = uid;
        this.Date = Date;


    }

    public  int getExerciseNumber(){
       return exerciseNumber;
    }


    public List<String> getExercise() {
        return exercise;
    }

    public String getSets() {
        return exercise.get(1);
    }

    public String getReps() {
        return exercise.get(2);
    }

    public String getWeight() {
        return exercise.get(3);
    }

    public String getUid() {
        return uid;
    }

    public Date getDate(){
        return Date;
    }

    public List<String> getExerciseNames() {
        return exerciseNames;
    }

    public List<String> getExerciseSets() {
        return exerciseSets;
    }

    public List<String> getExerciseReps() {
        return exerciseReps;
    }

    public List<String> getExerciseWeight() {
        return exerciseWeight;
    }

    @NonNull
    @Override
    public String toString() {
        return "Workout at: " + Date.toString();
    }
}
