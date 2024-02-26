CREATE TABLE IF NOT EXISTS
    account_roles
(
    account_id      INT NOT NULL REFERENCES account (id),
    account_type_id INT NOT NULL REFERENCES account_type (id),
    PRIMARY KEY (account_id, account_type_id)
);
