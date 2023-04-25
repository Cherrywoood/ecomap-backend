CREATE TYPE enum_type_ecopoint AS ENUM ('Место переработки', 'Экомагазин', 'Экоорганизация');
CREATE TYPE enum_type_event AS ENUM ('Лекция', 'Мастер-класс', 'Выставка', 'Экскурсия', 'Семинар', 'Своп-вечеринка',
    'Волонтерство', 'Развлекательная встреча', 'Разное');
CREATE TYPE enum_day_week AS ENUM ('Пн', 'Вт', 'Ср', 'Чт', 'Пт', 'Сб', 'Вс');


CREATE TABLE IF NOT EXISTS type_product
(
    id      SMALLSERIAL PRIMARY KEY,
    NAME VARCHAR(25) NOT NULL UNIQUE CHECK (NAME != '')
);


CREATE TABLE IF NOT EXISTS type_waste
(
    id      SMALLSERIAL PRIMARY KEY,
    NAME VARCHAR(25) NOT NULL UNIQUE CHECK (NAME != '')
);

CREATE TABLE IF NOT EXISTS map_point
(
    ID SERIAL PRIMARY KEY,
    address text NOT NULL CHECK (address != ''),
    coordinates geometry UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS event
(
    id serial PRIMARY KEY,
    id_point INT NOT NULL UNIQUE,
    Name varchar(100) NOT NULL CHECK(name != ''),
    photo text NOT NULL, -- путь к файлу
    description text NOT NULL,
    type enum_type_event NOT NULL,
    url_source text,
    url_registration text,
    start_date DATE NOT NULL,
    start_time TIME,
    end_date DATE,
    FOREIGN KEY(id_point) REFERENCES map_point (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS ecopoint
(
    id serial PRIMARY KEY,
    id_point INT NOT NULL UNIQUE,
    NAME VARCHAR(255) NOT NULL CHECK(name != ''),
    photo text NOT NULL, -- путь к файлу
    description text NOT NULL,
    type enum_type_ecopoint NOT NULL,
    url text,
    phone_number VARCHAR(11),
    email varchar(320),
    around_the_clock BOOLEAN DEFAULT FALSE,
    FOREIGN KEY(id_point) REFERENCES map_point (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS opening_hours
(
    id_ecopoint INT PRIMARY KEY,
    day enum_day_week NOT NULL,
    open_time TIME NOT NULL,
    close_time TIME NOT NULL,
    FOREIGN KEY(id_ecopoint) REFERENCES ecopoint (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS ecopoint_type_waste -- type место переработки
(
    ecopoint_id SMALLINT REFERENCES ecopoint (id) ON DELETE CASCADE ON UPDATE CASCADE,
    type_waste_id SMALLINT REFERENCES type_waste (id) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (ecopoint_id, type_waste_id)
);

CREATE TABLE IF NOT EXISTS ecopoint_type_product -- type эко магазин
(
    ecopoint_id     INT REFERENCES ecopoint (id) ON DELETE CASCADE ON UPDATE CASCADE,
    type_product_id SMALLINT REFERENCES type_product (id) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (ecopoint_id, type_product_id)
);