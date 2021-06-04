CREATE EXTENSION postgis;

DROP TABLE IF EXISTS user_location;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS location;

CREATE TABLE users (
    id   SERIAL PRIMARY KEY,
    name VARCHAR(250) UNIQUE NOT NULL,
    pass VARCHAR(250) NOT NULL
);

CREATE TABLE location (
    id        SERIAL PRIMARY KEY,
    name      VARCHAR(250) NOT NULL,
    latitude  DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL
);

CREATE TABLE user_location (
    user_id     INTEGER NOT NULL REFERENCES users (id),
    location_id INTEGER NOT NULL REFERENCES location (id)
);