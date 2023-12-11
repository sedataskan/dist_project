CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_movie (
    id INTEGER PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    movie_name VARCHAR(255) NOT NULL,
    FOREIGN KEY (username) REFERENCES public.users (username),
    PRIMARY KEY (username, movie_name)
);
