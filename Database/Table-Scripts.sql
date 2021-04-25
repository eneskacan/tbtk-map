CREATE EXTENSION postgis;

CREATE TABLE users (
	users_id SERIAL PRIMARY KEY,
	name VARCHAR ( 250 ) UNIQUE NOT NULL,
	pass VARCHAR ( 250 ) NOT NULL
);

CREATE TABLE geopoint (
	geopoint_id SERIAL PRIMARY KEY,
	name VARCHAR ( 250 ) NOT NULL,
	geom GEOMETRY NOT NULL
);

CREATE TABLE user_geopoint (
	users_id INTEGER NOT NULL REFERENCES users (users_id),
	geopoint_id INTEGER NOT NULL REFERENCES geopoint (geopoint_id)
);