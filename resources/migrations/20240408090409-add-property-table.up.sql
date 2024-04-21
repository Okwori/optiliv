CREATE TABLE IF NOT EXISTS
    property
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(100),
    price FLOAT,
    address VARCHAR(250),
    area FLOAT,
    adjusted_by INT,
    user_id INT,
    description VARCHAR(500),
    city_area_id INT,
    property_type_id INT
);
