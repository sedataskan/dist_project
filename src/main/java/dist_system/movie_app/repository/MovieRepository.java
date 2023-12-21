package dist_system.movie_app.repository;

import dist_system.movie_app.user.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, String> {
        Optional<Movie> findById(int id);
}
