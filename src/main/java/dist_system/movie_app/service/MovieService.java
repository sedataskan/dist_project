package dist_system.movie_app.service;

import dist_system.movie_app.model.BaseResponse;
import dist_system.movie_app.repository.UserRepository;
import dist_system.movie_app.repository.UserMovieRepository;
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

import java.sql.Array;
import java.util.List;
import java.util.Optional;

@ComponentScan(basePackages = {"dist_system.movie_app.repository"})
@Service
public class MovieService {

    @Value("${api.key}")
    private String apiKey;

    private final UserRepository userRepository;
    private final UserMovieRepository userMovieRepository;

    public MovieService(UserRepository userRepository, UserMovieRepository userMovieRepository) {
        this.userRepository = userRepository;
        this.userMovieRepository = userMovieRepository;
    }

    public String getMovieById(String id) throws IOException, InterruptedException, JSONException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.themoviedb.org/3/keyword/" + id + "/movies"))
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        // Parse JSON response
        JSONObject jsonResponse = new JSONObject(response.body());

        // Extract movie information
        if (jsonResponse.has("results")) {
            JSONArray resultsArray = jsonResponse.getJSONArray("results");
            JSONObject firstResult = resultsArray.getJSONObject(0);
            String movieTitle = firstResult.getString("original_title");
            System.out.println("Got movie by this id: " + id + movieTitle);
            return movieTitle;
        }
        return null;
    }

    public String saveMovie(String movieId, String username) throws IOException, InterruptedException {

        Optional<User> user = userRepository.findByUsername(username);
        String movie = getMovieById(movieId);

        System.out.println("Trying to save movie: " + movie + " - for user: " + username);

        //save movie to user_movie table in database
        if (user.isPresent() && movie != null) {
            if (!userMovieRepository.existsByUsernameAndMovieName(user.get().getUsername(), movie)) {
                UserMovie userMovie = UserMovie.builder()
                        .username(user.get().getUsername())
                        .movieName(movie)
                        .build();
                userMovieRepository.save(userMovie);

                return "Movie Saved Successfully";
            }
            return "Movie already saved";
        }
        return null;
    }

    public String deleteMovie(String movie, String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            userMovieRepository.deleteById(movie);
            return "Movie deleted";
        } else {
            return null;
        }
    }
    public List<UserMovie> getAllMovies(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return userMovieRepository.findAllByUsername(username);
        } else {
            return null;
        }
    }

    public String addReview(String id, String review) {
        System.out.println("Added review for movie with id: " + id + "\nReview: " + review);
        return "Review added";
    }

    public String addRating(String id, Float rating, String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            // Check userMovieRepo for movie with id
            // If exists, update rating
            if (userMovieRepository.existsById(id)) {
                UserMovie userMovie = userMovieRepository.findById(id).get();
                userMovie.setRating(rating);
                userMovieRepository.save(userMovie);
                return "Rating updated";
            } else {
                return "Movie not found";
            }
        } else {
            return null;
        }
    }

    public Object getRating(String id) {
        Optional<UserMovie> userMovie = userMovieRepository.findById(id);
        if (userMovie.isPresent()) {
            return userMovie.get().getRating();
        } else {
            return null;
        }
    }
}

