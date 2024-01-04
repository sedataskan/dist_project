package dist_system.movie_app.service;

import dist_system.movie_app.repository.MovieRepository;
import dist_system.movie_app.repository.UserRepository;
import dist_system.movie_app.repository.UserMovieRepository;
import dist_system.movie_app.user.Movie;
import dist_system.movie_app.user.User;
import dist_system.movie_app.user.UserMovie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.List;
import java.util.Optional;

@ComponentScan(basePackages = {"dist_system.movie_app.repository"})
@Service
public class MovieService {

    @Value("${api.key}")
    private String apiKey;

    private final UserRepository userRepository;
    private final UserMovieRepository userMovieRepository;
    private final MovieRepository movieRepository;

    public MovieService(UserRepository userRepository, UserMovieRepository userMovieRepository, MovieRepository movieRepository) {
        this.userRepository = userRepository;
        this.userMovieRepository = userMovieRepository;
        this.movieRepository = movieRepository;
    }

    public void saveMovieToDB() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.themoviedb.org/3/movie/top_rated?language=en-US&page=1"))
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject jsonObject = new JSONObject(response.body());
        JSONArray jsonArray = jsonObject.getJSONArray("results");
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject movie = jsonArray.getJSONObject(i);
                movieRepository.save(Movie
                        .builder()
                        .movieName(movie.getString("title"))
                        .rating((float) movie.getDouble("vote_average"))
                        .build());
            }
        }
    }

    public String getMovieById(int id) throws IOException, InterruptedException, JSONException {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isPresent()) {
            System.out.println("Movie found in database: " + movie.get().getMovieName());
            return movie.get().getMovieName();
        } else {
            return "Movie not found";
        }
    }

    public String saveMovie(int movieId, String username) throws IOException, InterruptedException {

        Optional<User> user = userRepository.findByUsername(username);
        String movie = getMovieById(movieId);

        System.out.println("Trying to save movie: " + movie + " - for user: " + username);

        //save movie to user_movie table in database
        if (user.isPresent() && movie != null) {
            if (!userMovieRepository.existsByUsernameAndMovieName(user.get().getUsername(), movie)) {
                UserMovie userMovie = UserMovie
                        .builder()
                        .movieId(movieId)
                        .username(user.get().getUsername())
                        .movieName(movie)
                        .build();
                userMovieRepository.save(userMovie);

                return "Movie Saved Successfully";
            }
            return "Movie already saved";
        }
        return "Movie not found";
    }

    public String deleteMovie(int movie, String username) throws IOException, InterruptedException {
        Optional<User> user = userRepository.findByUsername(username);
        String movieName = getMovieById(movie);
        if (user.isPresent()) {
            if (userMovieRepository.existsByUsernameAndMovieName(user.get().getUsername(), movieName)) {
                userMovieRepository. deleteByMovieIdAndUsername(movie, username);
                return "Movie deleted";
            }
            return "Movie not found";
        }
        return "Movie not found";
    }
    public List<UserMovie> getAllMovies(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return userMovieRepository.findAllByUsername(username);
        }
        return null;
    }

    public String addReview(int id, String review, String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            // Check userMovieRepo for movie with id
            // If exists, update rating
            if (userMovieRepository.existsByUsernameAndMovieId(username, id)) {
                UserMovie userMovie = userMovieRepository.findByUsernameAndMovieId(username, id);
                userMovie.setReview(review);
                userMovieRepository.save(userMovie);
                return "Review updated";
            } else {
                return "Movie not found";
            }
        }
        return "User not found";
    }

    public String getReview(int id, String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            UserMovie userMovie = userMovieRepository.findByUsernameAndMovieId(username, id);
            if (userMovie != null) {
                return userMovie.getReview();
            }
            return "Movie not found";
        }
        return "User not found";
    }

    public String addRating(int id, Float rating, String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            // Check userMovieRepo for movie with id
            // If exists, update rating
            if (userMovieRepository.existsByUsernameAndMovieId(username, id)) {
                UserMovie userMovie = userMovieRepository.findByUsernameAndMovieId(username, id);
                userMovie.setRating(rating);
                userMovieRepository.save(userMovie);
                return "Rating updated";
            } else {
                return "Movie not found";
            }
        }
        return "User not found";
    }

    public Object getRating(int id, String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            UserMovie userMovie = userMovieRepository.findByUsernameAndMovieId(username, id);
            if (userMovie != null) {
                return userMovie.getRating();
            }
            return "Movie not found";
        }
        return "User not found";
    }
}

