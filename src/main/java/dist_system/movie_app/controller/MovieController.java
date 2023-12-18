package dist_system.movie_app.controller;

import dist_system.movie_app.dto.ReviewRequest;
import dist_system.movie_app.model.BaseResponse;
import dist_system.movie_app.service.MovieService;
import dist_system.movie_app.user.User;
import dist_system.movie_app.user.UserMovie;
import org.json.JSONException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

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
    public BaseResponse<String> getMovieById(@PathVariable String id) throws IOException, InterruptedException, JSONException {
        String movieName = movieService.getMovieById(id);
        if (movieName == null) {
            return new BaseResponse<>(false, "Movie has not been found", null);
        }
        return new BaseResponse<>(true, "Movie has been found", movieName);
    }

    // get all movies
    @GetMapping("/all")
    public BaseResponse<List<UserMovie>> getAllMovies(@AuthenticationPrincipal User user) throws IOException, InterruptedException, JSONException {
        return new BaseResponse<>(true, "Movies has been listed", movieService.getAllMovies(user.getUsername()));
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
    public BaseResponse<String> addMovie(@PathVariable String id, @AuthenticationPrincipal User user) throws JSONException, IOException, InterruptedException {
        String message = movieService.saveMovie(id, user.getUsername());
        if (message == null) {
            return new BaseResponse<>(false, "Movie has not been found", null);
        }
        return new BaseResponse<>(true, "Process was successful", message);
    }

    // for adding review
    @PostMapping("/addReview/{id}")
    public String addReview(@PathVariable String id, @RequestBody ReviewRequest Review){
        return movieService.addReview(id, Review.getReview());
    }

    // for deleting movie
    @DeleteMapping("/delete/{id}")
    public BaseResponse<String> deleteMovie(@PathVariable String id, @AuthenticationPrincipal User user) {
        String message = movieService.deleteMovie(id, user.getUsername());
        if (message == null) {
            return new BaseResponse<>(false, "Movie has not been found", null);
        }
        return new BaseResponse<>(true, "Movie has been deleted", message);
    }

}
