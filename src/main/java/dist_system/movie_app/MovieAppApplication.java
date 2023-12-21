package dist_system.movie_app;

import dist_system.movie_app.repository.MovieRepository;
import dist_system.movie_app.service.MovieService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MovieAppApplication {

    public final MovieRepository movieRepository;

    public MovieAppApplication(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(MovieAppApplication.class, args);
        System.out.println("\n Let's Go! \n");
    }

    @Bean
    public CommandLineRunner demo(MovieService movieService) {
        return args -> {
            //if database has no movies, save movies to database
            if (movieRepository.count() == 0) {
                movieService.saveMovieToDB();
            }
            else {
                System.out.println("Database has movies");
            }
        };
    }
}
