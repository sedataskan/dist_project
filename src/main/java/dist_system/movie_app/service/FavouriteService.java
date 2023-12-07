package dist_system.movie_app.service;

import org.springframework.stereotype.Service;

@Service
public class FavouriteService {
    public String saveFavourite(String id, String movie, String userId) {
        try {
            System.out.println("Saved favourite: " + movie + " id: " + id + "\nFor user with id: " + userId);
            return "Favourite saved";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String deleteFavourite(String movie, String userId) {
        //add check for movie existence in fav list table in db and return error if not found

        try {
            System.out.println("Deleted favourite: " + movie + "\nFor user with id: " + userId);
            return "Favourite deleted";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getAllFavourites(String userId) {
        //add check for user existence in users table in db and return error if not found

        try {
            System.out.println("Getting all favourites for user with id: " + userId);
            return "All favourites for user with id: " + userId + " returned";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
