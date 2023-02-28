CREATE SCHEMA IF NOT EXISTS userroles;

CREATE TABLE userroles.users_table (
                                       id BIGSERIAL PRIMARY KEY,
                                       first_name VARCHAR(50),
                                       last_name VARCHAR(50),
                                       email VARCHAR(255) UNIQUE,
                                       password VARCHAR(255),
                                       is_enabled BOOLEAN,
                                       date_created TIMESTAMP WITH TIME ZONE,
                                       date_modified TIMESTAMP WITH TIME ZONE,
                                       role VARCHAR(50)
);
