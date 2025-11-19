CREATE TABLE human (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INTEGER NOT NULL,
    has_license BOOLEAN
);

CREATE TYPE car_brand AS ENUM (
    'Toyota',
    'BMW',
    'Mercedes'
);

CREATE TABLE car (
    id SERIAL PRIMARY KEY,
    brand car_brand NOT NULL,
    model VARCHAR(255) NOT NULL,
    price NUMERIC(12, 2) NOT NULL CHECK (price >= 0)
);

CREATE TABLE human_car (
    human_id INTEGER NOT NULL,
    car_id INTEGER NOT NULL,
    PRIMARY KEY (human_id, car_id),
    FOREIGN KEY (human_id) REFERENCES human(id) ON DELETE CASCADE,
    FOREIGN KEY (car_id) REFERENCES car(id) ON DELETE CASCADE
);

# В котексте задачи руководствовался рядом допущений для упрощения схемы базы:
# - что список марок машин небольшой, например, для прокатной компании
# - что цены только в одной валюте
# - что даты создания и обновления записей не требуются
