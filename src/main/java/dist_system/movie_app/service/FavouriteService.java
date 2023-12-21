package dist_system.movie_app.service;

import dist_system.movie_app.repository.FavouriteRepository;
import dist_system.movie_app.repository.UserRepository;
import dist_system.movie_app.user.FavouriteMovie;
import dist_system.movie_app.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavouriteService {

    private final UserRepository userRepository;
    private final FavouriteRepository favouriteRepository;

    public FavouriteService(UserRepository userRepository, FavouriteRepository favouriteRepository) {
        this.userRepository = userRepository;
        this.favouriteRepository = favouriteRepository;
    }

    public String saveFavourite(int movieId, String movieName, String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && movieName != null) {
            if (!favouriteRepository.existsByUsernameAndMovieName(username, movieName)) {
                FavouriteMovie favouriteMovie = FavouriteMovie
                        .builder()
                        .movieId(movieId)
                        .username(username)
                        .movieName(movieName)
                        .build();
                favouriteRepository.save(favouriteMovie);
                return "Favourite saved";
            } else {
                return "Movie already saved as favourite";
            }
        }
        return "User or Movie can not found";
    }

    public String deleteFavourite(int id, String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            if (favouriteRepository.existsByMovieId(id)) {
                favouriteRepository.deleteByMovieIdAndUsername(id, username);
                return "Favourite deleted";
            }
            return "Favourite can not found";
        }
        return "User can not found";
    }

    public List<FavouriteMovie> getAllFavourites(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return favouriteRepository.findAllByUsername(username);
        } else {
            return null;
        }
    }
}
