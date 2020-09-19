package com.example.brogapp;

public class BrewItem {
    private int mImageResource;
    private String brewName, brewDescription, brewScore;

    public BrewItem(int imageResource, String name, String description, String score){
        mImageResource = imageResource;
        brewDescription = name;
        brewDescription = description;
        brewScore = score;
    }

    public int getmImageResource() {
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
