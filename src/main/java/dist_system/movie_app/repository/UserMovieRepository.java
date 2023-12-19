package dist_system.movie_app.repository;

import dist_system.movie_app.user.UserMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserMovieRepository extends JpaRepository<UserMovie, String> {


    boolean existsByUsernameAndMovieName(String username, String movieName);



    // Find all by username
    List<UserMovie> findAllByUsername(String username);

}
