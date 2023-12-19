package dist_system.movie_app.repository;

import dist_system.movie_app.user.FavouriteMovie;
import dist_system.movie_app.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavouriteRepository extends JpaRepository<FavouriteMovie, String> {

    Optional<FavouriteMovie> findByUsername(String username);
    //  find all by username
   List<FavouriteMovie> findAllByUsername(String username);


    boolean existsByUsername(String username);
}
