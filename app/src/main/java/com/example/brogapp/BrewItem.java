package com.example.brogapp;

public class BrewItem {
    private int mImageResource;
    private String brewName, brewDescription, brewScore, brewID;

    public BrewItem(){} //Empty constructor for Firestore

    public BrewItem(int imageResource, String name, String description, String score, String brewID){
        mImageResource = imageResource;
        brewName = name;
        brewDescription = description;
        brewScore = score;
        this.brewID = brewID;
    }

    public String setbrewID(){return brewID;};

    public int getImageResource() {
        return mImageResource;
    }

    public String getBrewName() {
        return brewName;
    }

    public String getBrewDescription() {
        return brewDescription;
    }

    public String getBrewScore() {
        return brewScore;
    }

}
