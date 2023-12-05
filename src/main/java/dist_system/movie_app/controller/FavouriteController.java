package dist_system.movie_app.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favourites")
public class FavouriteController {

    // adding movie to favs
    @PostMapping("/add")
    public String addFavourite() {
        return "Favourite added";
    }

    // deleting movie from favs
    @DeleteMapping("/delete")
    public String deleteFavourite() {
        return "Favourite deleted";
    }

    // get all favs
    @GetMapping("/all")
    public String getAllFavourites() {
        return "All favourites";
    }


}
