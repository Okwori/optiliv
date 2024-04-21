CREATE TABLE IF NOT EXISTS
    property
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(100),
    price FLOAT,
    address VARCHAR(250),
    area INT,
    adjusted_by INT,
    user_id INT,
    description VARCHAR(500),
    image_url VARCHAR(250),
    city_area_id INT,
    property_type_id INT,
    structure_id INT,
    furniture_id INT
);
