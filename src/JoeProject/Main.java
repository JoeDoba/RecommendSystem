package JoeProject;
import edu.duke.*;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
//        FirstRatings fr = new FirstRatings();
//        fr.loadMovie("data/ratedmoviesfull.csv");
        FirstRatings fr = new FirstRatings();
        fr.loadRaters("data/ratings.csv");

    }
}
