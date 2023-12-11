package dist_system.movie_app.controller;

import dist_system.movie_app.dto.ReviewRequest;
import dist_system.movie_app.service.MovieService;
import dist_system.movie_app.user.User;
import org.json.JSONException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequestMapping("app/v1/movie")
public class MovieController {

    //created movieService object to use its methods in controller with constructor injection
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    // get movie by id with param
    @GetMapping("/{id}")
    public String getMovieById(@PathVariable String id) throws IOException, InterruptedException, JSONException {
        return movieService.getMovieById(id);
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
    @PostMapping("/addRating")
    public String addRating() {
        return "Rating added";
    }

    // for adding movie to user
    @PostMapping("/addMovie/{id}")
    public String addMovie(@PathVariable String id, @AuthenticationPrincipal User user) throws JSONException, IOException, InterruptedException {
        return movieService.saveMovie(id, user.getUsername());
    }

    // for adding review
    @PostMapping("/addReview/{id}")
    public String addReview(@PathVariable String id, @RequestBody ReviewRequest Review){
        return movieService.addReview(id, Review.getReview());
    }

    // for deleting movie
    @DeleteMapping("/deleteMovie")
    public String deleteMovie() {
        return "Movie deleted";
    }

}
