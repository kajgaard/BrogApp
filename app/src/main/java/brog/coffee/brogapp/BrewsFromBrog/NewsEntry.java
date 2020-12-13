package brog.coffee.brogapp.BrewsFromBrog;

public class NewsEntry {

        private int imageRessource;

        public NewsEntry(){} //Empty constructor for Firestore

        public NewsEntry(int imageRessource) {
            this.imageRessource = imageRessource;
        }

        public int getImageRessource() {
            return imageRessource;
        }

    }


