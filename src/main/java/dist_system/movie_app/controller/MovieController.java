package dist_system.movie_app.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie")
public class MovieController {

        // get movie by id with param
        @GetMapping("/{id}")
        public String getMovieById(@PathVariable String id) {
            return "Movie with id: " + id;
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
