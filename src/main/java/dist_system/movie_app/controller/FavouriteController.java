package dist_system.movie_app.controller;

import dist_system.movie_app.dto.FavouriteRequest;
import dist_system.movie_app.service.FavouriteService;
import dist_system.movie_app.service.MovieService;
import org.json.JSONException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/favourites")
public class FavouriteController {

    private final FavouriteService favouriteService;
    private final MovieService movieService;
    public FavouriteController(FavouriteService favouriteService, MovieService movieService) {
        this.favouriteService = favouriteService;
        this.movieService = movieService;
    }

    // adding movie to fav list
    @PostMapping("/add")
    public String addFavourite(@RequestBody FavouriteRequest request){
        try {
            String movie = movieService.getMovieById(request.getId());
            return favouriteService.saveFavourite(request.getId(), movie, request.getUserId());
        } catch (IOException | InterruptedException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

    // deleting movie from fav list
    @DeleteMapping("/delete")
    public String deleteFavourite(@RequestBody FavouriteRequest request){
        try {
            String movie = movieService.getMovieById(request.getId());
            return favouriteService.deleteFavourite(movie, request.getUserId());
        } catch (IOException | InterruptedException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

    // get all fav list
    @GetMapping("/{userId}/allFavourites")
    public String getAllFavourites(@PathVariable String userId) {
        return favouriteService.getAllFavourites(userId);
    }
}
