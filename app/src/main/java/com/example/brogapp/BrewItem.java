package com.example.brogapp;

public class BrewItem {
    private int mImageResource;
    private String brewName, brewDescription, brewScore;

    public BrewItem(int imageResource, String name, String description, String score){
        mImageResource = imageResource;
        brewName = name;
        brewDescription = description;
        brewScore = score;
    }

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
