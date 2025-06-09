--liquibase formatted sql

--changeset fabien:create-movie-table
CREATE TABLE movie (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    director VARCHAR(255),
    genre VARCHAR(100),
    rating NUMERIC(3,1),
    release_year INT
);