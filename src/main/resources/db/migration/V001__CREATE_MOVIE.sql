CREATE TABLE movie
(
    id           BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name         VARCHAR(255) NOT NULL,
    directorName VARCHAR(255) NOT NULL,
    genre        VARCHAR(255) NOT NULL,
    releaseDate  TIMESTAMP    NOT NULL
);
