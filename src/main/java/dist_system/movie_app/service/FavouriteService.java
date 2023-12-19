package dist_system.movie_app.service;

import dist_system.movie_app.repository.FavouriteRepository;
import dist_system.movie_app.repository.UserMovieRepository;
import dist_system.movie_app.repository.UserRepository;
import dist_system.movie_app.user.FavouriteMovie;
import dist_system.movie_app.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavouriteService {

    private final UserRepository userRepository;
    private final UserMovieRepository userMovieRepository;
    private final FavouriteRepository favouriteRepository;

    public FavouriteService(UserRepository userRepository, UserMovieRepository userMovieRepository, FavouriteRepository favouriteRepository) {
        this.userRepository = userRepository;
        this.userMovieRepository = userMovieRepository;
        this.favouriteRepository = favouriteRepository;
    }

    public String saveFavourite(String movieName, String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            FavouriteMovie favouriteMovie = FavouriteMovie.builder()
                    .username(username)
                    .movieName(movieName)
                    .build();
            favouriteRepository.save(favouriteMovie);
            return "Favourite saved";
        } else {
            return null;
        }
    }

    public String deleteFavourite(String id, String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            favouriteRepository.deleteById(id);
            return "Favourite deleted";
        } else {
            return null;
        }
    }

    public List<FavouriteMovie> getAllFavourites(User user) {
        return favouriteRepository.findAllByUsername(user.getUsername());
    }
}
