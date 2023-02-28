CREATE SCHEMA IF NOT EXISTS locations;

CREATE TABLE locations.location (
                                    id BIGSERIAL PRIMARY KEY,
                                    location VARCHAR(255),
                                    longitude NUMERIC(10, 6),
                                    latitude NUMERIC(10, 6),
                                    description TEXT,
                                    is_recommended BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE locations.visited_locations (
                                         id BIGSERIAL PRIMARY KEY,
                                         user_id BIGINT REFERENCES userroles.users_table(id),
                                         location_id BIGINT REFERENCES locations.location(id)
);

CREATE TABLE locations.unvisited_locations (
                                             id BIGSERIAL PRIMARY KEY,
                                             user_id BIGINT REFERENCES userroles.users_table(id),
                                             location_id BIGINT REFERENCES locations.location(id)
);