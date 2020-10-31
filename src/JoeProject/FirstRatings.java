package JoeProject;

import edu.duke.*;
import java.io.IOException;
import java.util.*;
import org.apache.commons.csv.*;

public class FirstRatings {
    private Map<String, List<Movie>> directorInform;
    private List<Rater> ratersList;
    private Map<String, Rater> id_RatersMap;
    private Map<String, List<Rater>> movie_raterMap;

    public FirstRatings() {
        this.directorInform = new HashMap<>();
        this.ratersList = new ArrayList<>();
        this.id_RatersMap = new HashMap<>();
        this.movie_raterMap = new HashMap<>();
    }

    public void loadMovie(String filename) throws IOException {
        List<Movie> movieList = new ArrayList<>();
        FileResource fileResource = new FileResource(filename);
        CSVParser fileParser = fileResource.getCSVParser();
        for (CSVRecord record : fileParser.getRecords()) {
            Movie movie = new Movie(record.get("id"), record.get("title"), record.get("year"), record.get("genre"),
                    record.get("director"), record.get("country"), record.get("poster"), Integer.parseInt(record.get("minutes")));
            movieList.add(movie);
        }
        System.out.println("There are " + genreTypeNum(movieList, "Comedy") + " Comedy movies");
        System.out.println("There are " + movieLengthFilter(movieList, 150) + " movies longer than 150 min.");
        System.out.println("There are totally " + movieList.size() + " movies in list.");
        directors_Movie_Inform(movieList);
        directorsWithMaxMovies();
    }

    private int genreTypeNum(List<Movie> movieList, String genreType) {
        int count = 0;
        for (Movie movie : movieList) {
            if (movie.getGenres().contains(genreType)) {
                count++;
            }
        }
        return count;
    }
    private int movieLengthFilter(List<Movie> movieList, int movieLength) {
        int count = 0;
        for (Movie movie : movieList) {
            if (movie.getMinutes() > movieLength) {
                count++;
            }
        }
        return count;
    }
    private void directors_Movie_Inform(List<Movie> movieList) {
        for (Movie movie : movieList) {
            String[] directors = movie.getDirector().split(",");
            for (String director : directors) {
                if (!directorInform.containsKey(director)) {
                    directorInform.put(director, new ArrayList<Movie>());
                }
                directorInform.get(director).add(movie);
            }
        }
    }
    private int maxMoviePerDirector() {
        int max = 0;
        for (String director : directorInform.keySet()) {
            if (directorInform.get(director).size() > max) {
                max = directorInform.get(director).size();
            }
        }
        return max;
    }
    private List<String> directorsWithMaxMovies() {
        int max = maxMoviePerDirector();
        System.out.println("The maximum movie number for single director is :" + max);
        List<String> directorsList = new ArrayList<>();
        for (String director : directorInform.keySet()) {
            if (directorInform.get(director).size() == max) {
                directorsList.add(director);
            }
        }
        System.out.println(directorsList.toString());
        return directorsList;
    }

    public void loadRaters(String filename) throws IOException {
        FileResource fileResource = new FileResource(filename);
        CSVParser fileParser = fileResource.getCSVParser();

        for (CSVRecord record : fileParser.getRecords()) {
            Rater rater;
            String rater_id = record.get("rater_id");
            String movie_id = record.get("movie_id");
            double rating = Double.parseDouble(record.get("rating"));
            if (!id_RatersMap.containsKey(rater_id)) {
                rater = new Rater(rater_id);
                ratersList.add(rater);
                id_RatersMap.put(rater_id, rater);
            } else {
                rater = id_RatersMap.get(rater_id);
            }
            if (!movie_raterMap.containsKey(movie_id)) {
                movie_raterMap.put(movie_id, new ArrayList<Rater>());
            }
            movie_raterMap.get(movie_id).add(rater);
            rater.addRating(movie_id, rating);
        }
        System.out.println("There are total " + ratersList.size() + " rater.");
        ratersWithMaxRating();
        movieNumRating("1798709");
    }
    private int maxRatingPerRater() {
        int max = 0;
        for (Rater rater : ratersList) {
            if (rater.numRatings() > max) {
                max = rater.numRatings();
            }
        }
        return max;
    }
    private List<Rater> ratersWithMaxRating() {
        int max = maxRatingPerRater();
        System.out.println("The maximum rating number for single rater is: " + max);
        List<Rater> list = new ArrayList<>();
        for (Rater rater : ratersList) {
            if (rater.numRatings() == max) {
                list.add(rater);
            }
        }
        System.out.println("There are total " + list.size() + " raters has maximum rating.");
        return list;
    }
    public int movieNumRating(String movie_id) {
        System.out.println("There are total " + movie_raterMap.size() + " movies that are rated.");
        System.out.printf("%s is rated by " + movie_raterMap.get(movie_id).size() + " raters\n", movie_id);
        return movie_raterMap.get(movie_id).size();
    }
}
