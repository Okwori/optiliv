CREATE TABLE IF NOT EXISTS
    account
(
    id               SERIAL PRIMARY KEY,
    email            VARCHAR(60) NOT NULL UNIQUE,
    password         VARCHAR(250),
    full_name        VARCHAR(100),
    mobile           VARCHAR(60),
    token            VARCHAR(250),
    last_login       TIMESTAMP,
    active           BOOLEAN     NOT NULL,
    account_type_id  INT,
    account_state_id INT
);
