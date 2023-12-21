CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_movie (
    id INTEGER PRIMARY KEY,
    movieId INTEGER NOT NULL,
    username VARCHAR(255) NOT NULL,
    movie_name VARCHAR(255) NOT NULL,
    FOREIGN KEY (movieId) REFERENCES public.movies (id),
    FOREIGN KEY (username) REFERENCES public.users (username),
    PRIMARY KEY (username, movie_name)
);

CREATE TABLE IF NOT EXISTS user_favorite (
    id INTEGER PRIMARY KEY,
    movieId INTEGER NOT NULL,
    username VARCHAR(255) NOT NULL,
    movie_name VARCHAR(255) NOT NULL,
    FOREIGN KEY (movieId) REFERENCES public.movies (id),
    FOREIGN KEY (username) REFERENCES public.users (username),
    PRIMARY KEY (username, movieId)
);

CREATE TABLE IF NOT EXISTS movies (
    id INTEGER PRIMARY KEY,
    movieName VARCHAR(255) NOT NULL,
    rating DOUBLE NOT NULL,
);
