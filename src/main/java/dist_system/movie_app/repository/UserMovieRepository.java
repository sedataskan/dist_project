package dist_system.movie_app.repository;

import dist_system.movie_app.user.UserMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserMovieRepository extends JpaRepository<UserMovie, String> {

    boolean existsByUsernameAndMovieName(String username, String movieName);
}
