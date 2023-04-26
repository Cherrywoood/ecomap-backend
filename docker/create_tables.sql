CREATE TYPE enum_day_week AS ENUM ('Пн', 'Вт', 'Ср', 'Чт', 'Пт', 'Сб', 'Вс');

CREATE TABLE IF NOT EXISTS waste_type
(
    id   SMALLSERIAL PRIMARY KEY,
    name VARCHAR(25) NOT NULL UNIQUE CHECK (name != '')
);

CREATE TABLE IF NOT EXISTS ecopoint_type_name
(
    id   SMALLSERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE CHECK (name != '')
);

CREATE TABLE IF NOT EXISTS ecopoint
(
    id           SERIAL PRIMARY KEY,
    address      TEXT                   NOT NULL CHECK (address != ''),
    geometry     GEOMETRY(POINT) UNIQUE NOT NULL,
    name         VARCHAR(255)           NOT NULL CHECK (name != ''),
    photo        VARCHAR(50), -- путь к файлу
    description  TEXT                   NOT NULL,
    site         TEXT,
    phone_number VARCHAR(11),
    email        VARCHAR(320),
    convenience  BOOLEAN DEFAULT FALSE  NOT NULL
);

CREATE TABLE IF NOT EXISTS opening_hours
(
    id          SERIAL PRIMARY KEY,
    ecopoint_id INT                   NOT NULL,
    day         enum_day_week         NOT NULL,
    open_time   TIME,
    close_time  TIME,
    is_day_off  BOOLEAN DEFAULT FALSE NOT NULL,
    FOREIGN KEY (ecopoint_id) REFERENCES ecopoint (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS ecopoint_waste_type -- type место переработки
(
    ecopoint_id   SMALLINT REFERENCES ecopoint (id) ON DELETE CASCADE ON UPDATE CASCADE,
    waste_type_id SMALLINT REFERENCES waste_type (id) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (ecopoint_id, waste_type_id)
);

CREATE TABLE IF NOT EXISTS ecopoint_type_rel -- type место переработки
(
    ecopoint_id           SMALLINT REFERENCES ecopoint (id) ON DELETE CASCADE ON UPDATE CASCADE,
    ecopoint_type_name_id SMALLINT REFERENCES ecopoint_type_name (id) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (ecopoint_id, ecopoint_type_name_id)
);

CREATE TABLE IF NOT EXISTS map_provider
(
    id          SMALLSERIAL PRIMARY KEY,
    name        VARCHAR(25)           NOT NULL UNIQUE CHECK (name != ''),
    url         TEXT                  NOT NULL CHECK (url != ''),
    attribution TEXT                  NOT NULL CHECK (attribution != ''),
    auth_token  TEXT CHECK (auth_token != ''),
    is_default  BOOLEAN DEFAULT FALSE NOT NULL
)