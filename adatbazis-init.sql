-- Ha mar leteznek a tablak toroljuk oket
DROP TABLE IF EXISTS package_delivered;
DROP TABLE IF EXISTS package_sent;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS jobs;
DROP TABLE IF EXISTS routes;

----- User -----
CREATE TABLE users
(
    user_id         bigint PRIMARY KEY,
    email_address   varchar(50) UNIQUE NOT NULL,
    password        varchar(60)        NOT NULL,
    can_deliver     boolean            NOT NULL,
    user_rating     real,
    cargo_free_size int,
    cargo_max_size  int,
    CHECK (user_rating BETWEEN 1 AND 5),
    CHECK (cargo_free_size <= cargo_max_size)
);

----- Routes -----
CREATE TABLE routes
(
    route_id       bigint PRIMARY KEY,
    actual_time    varchar(50),
    optimal_time   varchar(50),
    start_location varchar(50) NOT NULL,
    destination    varchar(50) NOT NULL
);

----- Jobs -----

DROP TYPE IF EXISTS status;
CREATE TYPE status AS ENUM ('pending', 'accepted', 'pickedUp', 'delivered','expired');

DROP TYPE IF EXISTS package_size;
CREATE TYPE package_size AS ENUM ('small', 'medium', 'large');

CREATE TABLE jobs
(
    job_id          bigint PRIMARY KEY,
    status          status       NOT NULL,
    sender_rating   int,
    size            package_size NOT NULL,
    payment         int          NOT NULL,
    job_issued_date varchar(50)  NOT NULL,
    deadline        varchar(50)  NOT NULL,
    delivery_date   varchar(50),
    delivery_route  bigint,
    CONSTRAINT fk_route
        FOREIGN KEY (delivery_route)
            REFERENCES routes (route_id),
    CHECK (sender_rating BETWEEN 1 AND 5)
);

----- Join tablak -----
CREATE TABLE package_sent
(
    id      SERIAL PRIMARY KEY,
    user_id bigint NOT NULL,
    job_id  bigint NOT NULL,
    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
            REFERENCES users (user_id),
    CONSTRAINT fk_job
        FOREIGN KEY (job_id)
            REFERENCES jobs (job_id)
);

CREATE TABLE package_delivered
(
    id      SERIAL PRIMARY KEY,
    user_id bigint NOT NULL,
    job_id  bigint NOT NULL,
    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
            REFERENCES users (user_id),
    CONSTRAINT fk_job
        FOREIGN KEY (job_id)
            REFERENCES jobs (job_id)
);