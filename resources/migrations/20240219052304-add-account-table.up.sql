CREATE TABLE IF NOT EXISTS
    account
(
    id               SERIAL PRIMARY KEY,
    email            VARCHAR(60) NOT NULL UNIQUE,
    password         VARCHAR(250) NULL,
    full_name        VARCHAR(100) NULL,
    mobile           VARCHAR(60) NULL,
    token            VARCHAR(250) NULL,
    last_login       DATE NULL,
    active           BOOLEAN     NOT NULL,
    account_type_id  INT NULL,
    account_state_id INT NULL
);
