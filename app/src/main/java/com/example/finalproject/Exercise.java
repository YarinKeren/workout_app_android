package com.example.finalproject;

import android.os.Parcel;
import android.os.Parcelable;

public class Exercise implements Parcelable {
    private String exerciseName;
    private double reps;
    private double weight;

    public Exercise(String n, double r, double w){
        this.exerciseName = n;
        this.reps = r;
        this.weight = w;
    }
    public String getExerciseName() {return exerciseName;}

    public void setExerciseName(String exerciseName) {this.exerciseName = exerciseName;}

    public double getReps() {return reps;}

    public void setReps(double reps) {this.reps = reps;}

    public double getWeight() {return weight;}

    public void setWeight(double weight) {this.weight = weight;}

    @Override
    public String toString() {
        return "Exercise{" +
                "exerciseName='" + exerciseName + '\'' +
                ", reps='" + reps + '\'' +
                ", weight=" + weight +
                '}';
    }

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
        dest.writeDouble(reps);
        dest.writeDouble(weight);
    }
}
