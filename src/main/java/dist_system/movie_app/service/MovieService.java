package dist_system.movie_app.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class MovieService {

    @Value("${api.key}")
    private String apiKey;

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
        JSONArray resultsArray = jsonResponse.getJSONArray("results");
        if (resultsArray.length() > 0) {
            JSONObject firstResult = resultsArray.getJSONObject(0);
            String movieTitle = firstResult.getString("original_title");
            String movieInfo = firstResult.getString("overview");
            System.out.println("Got movie by this id: " + id);
            return movieTitle + "\n" + movieInfo;
        } else {
            return "Movie not found";
        }
    }
}

