package dist_system.movie_app.controller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("/movie")
public class MovieController {
    //get apikey from application.properties
    @Value("${api.key}")
    private String apiKey;

    // get movie by id with param
    @GetMapping("/{id}")
    public String getMovieById(@PathVariable String id) throws IOException, InterruptedException, JSONException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.themoviedb.org/3/keyword/"+ id +"/movies"))
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        // Parse JSON response
        JSONObject jsonResponse = new JSONObject(response.body());

        // Extract movie information
        JSONArray resultsArray = jsonResponse.getJSONArray("results");
        if (resultsArray.length() > 0) {
            JSONObject firstResult = resultsArray.getJSONObject(0);
            String movieTitle = firstResult.getString("original_title");
            String movieInfo = firstResult.getString("overview");
            System.out.println("Got movie by this id: " + id);
            return movieTitle + "\n" + movieInfo;
        } else {
            return "Movie not found";
        }
    }

    // get all movies
    @GetMapping("/all")
    public String getAllMovies() {
        return "All movies";
    }

    // get ratings for movie with id
    @GetMapping("/{id}/ratings")
    public String getRatingsForMovie(@PathVariable String id) {
        return "Ratings for movie with id: " + id;
    }

    // for adding movie rating
    @PostMapping("/add/rating")
    public String addRating() {
        return "Rating added";
    }

    // for adding movie
    @PostMapping("/add")
    public String addMovie() {
        return "Movie added";
    }

    // for adding review
    @PostMapping("/add/review")
    public String addReview() {
        return "Review added";
    }

    // for deleting movie
    @DeleteMapping("/delete")
    public String deleteMovie() {
        return "Movie deleted";
    }

}
