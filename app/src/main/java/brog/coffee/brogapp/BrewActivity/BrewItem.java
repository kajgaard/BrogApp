package brog.coffee.brogapp.BrewActivity;

public class BrewItem {
    private int imageRessource;
    private String brewName, brewDescription, brewScore, brewID;

    public BrewItem(){} //Empty constructor for Firestore

    //Constructor
    public BrewItem(int imageRessource, String brewName, String brewDescription, String brewScore, String brewID) {
        this.imageRessource = imageRessource;
        this.brewName = brewName;
        this.brewDescription = brewDescription;
        this.brewScore = brewScore;
        this.brewID = brewID;
    }

    //Getters og setters

    public String setbrewID(){
        return brewID;};

    public int getImageRessource() {
        return imageRessource;
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
