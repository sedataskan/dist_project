package dist_system.movie_app.controller;

import dist_system.movie_app.model.BaseResponse;
import dist_system.movie_app.service.FavouriteService;
import dist_system.movie_app.service.MovieService;
import dist_system.movie_app.user.FavouriteMovie;
import dist_system.movie_app.user.User;
import org.json.JSONException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("app/v1/favourites")
public class FavouriteController {

    private final FavouriteService favouriteService;
    private final MovieService movieService;
    public FavouriteController(FavouriteService favouriteService, MovieService movieService) {
        this.favouriteService = favouriteService;
        this.movieService = movieService;
    }

    // adding movie to fav list
    @PostMapping("/{movieID}")
    public BaseResponse<String> addFavourite(@PathVariable int movieID, @AuthenticationPrincipal User user){
        try {
            String movie = movieService.getMovieById(movieID);
            String response = favouriteService.saveFavourite(movieID, movie, user.getUsername());
            if (response == null) {
                return new BaseResponse<>(false, "An unkown error occured", null);
            } else {
                return new BaseResponse<>(true, "success", response);
            }
        } catch (IOException | InterruptedException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

    // deleting movie from fav list
    @DeleteMapping("/{id}")
    public BaseResponse<String> deleteFavourite(@PathVariable int id, @AuthenticationPrincipal User user){
        String response = favouriteService.deleteFavourite(id, user.getUsername());
        if (response == null) {
            return new BaseResponse<>(false, "An unkown error occured", null);
        }
        return new BaseResponse<>(true, "success", response);
    }

    // get all fav list
    @GetMapping("/all")
    public BaseResponse<List<FavouriteMovie>> getAllFavourites(@AuthenticationPrincipal User user) {
        List<FavouriteMovie> response = favouriteService.getAllFavourites(user.getUsername());
        if (response == null) {
            return new BaseResponse<>(false, "Favourites has not been listed", null);
        }
        return new BaseResponse<>(true, "success", response);
    }
}
