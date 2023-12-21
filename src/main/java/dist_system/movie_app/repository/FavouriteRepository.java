package dist_system.movie_app.repository;

import dist_system.movie_app.user.FavouriteMovie;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouriteRepository extends JpaRepository<FavouriteMovie, String> {

    boolean existsByUsernameAndMovieName(String username, String movieName);

    //  find all by username
   List<FavouriteMovie> findAllByUsername(String username);

    boolean existsByMovieId(int movieId);

    @Transactional
    void deleteByMovieIdAndUsername(int movieId, String username);

}
