package dist_system.movie_app.repository;

import dist_system.movie_app.user.UserMovie;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMovieRepository extends JpaRepository<UserMovie, String> {

    boolean existsByUsernameAndMovieName(String username, String movieName);

    boolean existsByUsernameAndMovieId(String username, int movieId);

    // Find all by username
    List<UserMovie> findAllByUsername(String username);

    @Transactional
    void deleteByMovieIdAndUsername(int movieId, String username);

    UserMovie findByUsernameAndMovieId(String username, int id);
}
