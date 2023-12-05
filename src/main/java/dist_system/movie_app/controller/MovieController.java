package dist_system.movie_app.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie")
public class MovieController {

        @GetMapping("/{id}")
        public String getMovieById(@PathVariable String id) {
            return "Movie with id: " + id;
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

        // for deleting movie
        @DeleteMapping("/delete")
        public String deleteMovie() {
            return "Movie deleted";
        }

        // for adding review
        @PostMapping("/add/review")
        public String addReview() {
            return "Review added";
        }

        // get all movies
        @GetMapping("/all")
        public String getAllMovies() {
            return "All movies";
        }

        // get movie by id with param

}
