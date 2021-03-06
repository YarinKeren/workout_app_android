package com.example.finalproject;

import android.os.Parcel;
import android.os.Parcelable;

public class Exercise implements Parcelable {
    private String exerciseName;
    private int reps;
    private int weight;

    public Exercise(String n, int r, int w){
        this.exerciseName = n;
        this.reps = r;
        this.weight = w;
    }
    public String getExerciseName() {return exerciseName;}

    public void setExerciseName(String exerciseName) {this.exerciseName = exerciseName;}

    public int getReps() {return reps;}

    public void setReps(int reps) {this.reps = reps;}

    public int getWeight() {return weight;}

    public void setWeight(int weight) {this.weight = weight;}


    protected Exercise(Parcel in) {
        exerciseName = in.readString();
        reps = in.readInt();
        weight = in.readInt();
    }

    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(exerciseName);
        dest.writeInt(reps);
        dest.writeInt(weight);
    }
}
