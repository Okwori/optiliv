CREATE TABLE IF NOT EXISTS
    account
(
    id              SERIAL PRIMARY KEY,
    email           VARCHAR(60) NOT NULL,
    password        VARCHAR(250),
    full_name       VARCHAR(100),
    mobile          VARCHAR(60),
    token           VARCHAR(250),
    last_login      TIMESTAMP,
    active          BOOLEAN,
    account_type_id INT,

    UNIQUE(email)
);
