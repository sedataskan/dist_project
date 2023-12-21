package dist_system.movie_app.controller;

import dist_system.movie_app.dto.RatingRequest;
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
import java.util.Objects;

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
    public BaseResponse<String> getMovieById(@PathVariable int id) throws IOException, InterruptedException, JSONException {
        String movieName = movieService.getMovieById(id);
        if (movieName == null) {
            return new BaseResponse<>(false, "Movie has not been found", null);
        }
        return new BaseResponse<>(true, "Movie has been found", movieName);
    }

    // get all movies
    @GetMapping("/all")
    public BaseResponse<List<UserMovie>> getAllMovies(@AuthenticationPrincipal User user) throws JSONException {
        List<UserMovie> response = movieService.getAllMovies(user.getUsername());
        if (response == null) {
            return new BaseResponse<>(false, "Movies has not been listed", null);
        }
        return new BaseResponse<>(true, "success", response);
    }

    @PostMapping("/{id}/rating")
    public BaseResponse<String> addRating(@PathVariable int id, @RequestBody RatingRequest request, @AuthenticationPrincipal User user) {
        String response = movieService.addRating(id, request.getRating(), user.getUsername());
        if (response == null) {
            return new BaseResponse<>(false, "An unkown error occured", null);
        }
        return new BaseResponse<>(true, "success", response);
    }

    @GetMapping("/{id}/rating")
    public BaseResponse<Float> getRating(@PathVariable int id, @AuthenticationPrincipal User user) {
        Object response = movieService.getRating(id, user.getUsername());
        if (response == null) {
            return new BaseResponse<>(false, "Movie not found", null);
        }
        return new BaseResponse<>(true, "success", (Float) response);
    }

    // for adding review
    @PostMapping("/{id}/review")
    public BaseResponse<String> addReview(@PathVariable int id, @RequestBody ReviewRequest Review, @AuthenticationPrincipal User user){
        String response = movieService.addReview(id, Review.getReview(), user.getUsername());
        if (response == null) {
            return new BaseResponse<>(false, "An unkown error occured", null);
        }
        return new BaseResponse<>(true, "success", response);
    }

    @GetMapping("/{id}/review")
    public BaseResponse<Object> getReview(@PathVariable int id, @AuthenticationPrincipal User user) {
        Object response = movieService.getReview(id, user.getUsername());
        if (response == null) {
            return new BaseResponse<>(false, "Movie not found", null);
        }
        return new BaseResponse<>(true, "success", response);
    }

    // for adding movie to user
    @PostMapping("/addMovie/{id}")
    public BaseResponse<String> addMovie(@PathVariable int id, @AuthenticationPrincipal User user) throws JSONException, IOException, InterruptedException {
        String message = movieService.saveMovie(id, user.getUsername());
        if (message == null) {
            return new BaseResponse<>(false, "Movie has not been found", null);
        }
        if (message.equals("Movie already saved")) {
            return new BaseResponse<>(false, "Movie already saved", null);
        }
        return new BaseResponse<>(true, "Process was successful", message);
    }

    // for deleting movie
    @DeleteMapping("/{id}")
    public BaseResponse<String> deleteMovie(@PathVariable int id, @AuthenticationPrincipal User user) throws IOException, InterruptedException {
        String message = movieService.deleteMovie(id, user.getUsername());
        if (Objects.equals(message, "Movie not found")) {
            return new BaseResponse<>(false, "Movie has not been found", null);
        }
        return new BaseResponse<>(true, "Movie has been deleted", message);
    }

}
