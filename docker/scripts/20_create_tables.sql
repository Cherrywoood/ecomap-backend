CREATE TYPE enum_day_week AS ENUM ('MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT', 'SUN');
CREATE CAST (character varying as enum_day_week) WITH INOUT AS IMPLICIT;

CREATE TYPE enum_role AS ENUM ('USER', 'ADMIN');
CREATE CAST (character varying as enum_role) WITH INOUT AS IMPLICIT;

CREATE TABLE IF NOT EXISTS waste_type
(
    id   SMALLSERIAL PRIMARY KEY,
    name VARCHAR(25) NOT NULL UNIQUE CHECK (name != '')
);

CREATE TABLE IF NOT EXISTS shop_type
(
    id   SMALLSERIAL PRIMARY KEY,
    name VARCHAR(25) NOT NULL UNIQUE CHECK (name != '')
);

CREATE TABLE IF NOT EXISTS event_type
(
    id   SMALLSERIAL PRIMARY KEY,
    name VARCHAR(25) NOT NULL UNIQUE CHECK (name != '')
);

CREATE TABLE IF NOT EXISTS ecopoint_type
(
    id   SMALLSERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE CHECK (name != '')
);

CREATE TABLE IF NOT EXISTS ecopoint
(
    id             BIGSERIAL PRIMARY KEY,
    address        TEXT                   NOT NULL CHECK (address != ''),
    position       GEOMETRY(POINT, 3857) UNIQUE NOT NULL,
    name           VARCHAR(255)           NOT NULL CHECK (name != ''),
    description    TEXT                   NOT NULL,
    site           TEXT,
    phone_number   VARCHAR(16),
    email          VARCHAR(255),
    is_convenience BOOLEAN DEFAULT FALSE  NOT NULL
);

CREATE TABLE IF NOT EXISTS ecopoint_image
(
    id          BIGSERIAL PRIMARY KEY,
    image_path  TEXT   NOT NULL UNIQUE CHECK (image_path != ''),
    ecopoint_id BIGINT NOT NULL,
    FOREIGN KEY (ecopoint_id) REFERENCES ecopoint (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS work_schedule
(
    id          BIGSERIAL PRIMARY KEY,
    ecopoint_id BIGINT                NOT NULL,
    day         enum_day_week         NOT NULL,
    open_time   TIME,
    close_time  TIME,
    is_day_off  BOOLEAN DEFAULT FALSE NOT NULL,
    FOREIGN KEY (ecopoint_id) REFERENCES ecopoint (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS ecopoint_waste_type
(
    ecopoint_id   BIGINT REFERENCES ecopoint (id) ON DELETE CASCADE ON UPDATE CASCADE,
    waste_type_id SMALLINT REFERENCES waste_type (id) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (ecopoint_id, waste_type_id)
);

CREATE TABLE IF NOT EXISTS ecopoint_shop_type
(
    ecopoint_id  BIGINT REFERENCES ecopoint (id) ON DELETE CASCADE ON UPDATE CASCADE,
    shop_type_id SMALLINT REFERENCES shop_type (id) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (ecopoint_id, shop_type_id)
);

CREATE TABLE IF NOT EXISTS ecopoint_event_type
(
    ecopoint_id   BIGINT REFERENCES ecopoint (id) ON DELETE CASCADE ON UPDATE CASCADE,
    event_type_id SMALLINT REFERENCES event_type (id) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (ecopoint_id, event_type_id)
);

CREATE TABLE IF NOT EXISTS ecopoint_type_ecopoint
(
    ecopoint_id      BIGINT REFERENCES ecopoint (id) ON DELETE CASCADE ON UPDATE CASCADE,
    ecopoint_type_id SMALLINT REFERENCES ecopoint_type (id) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (ecopoint_id, ecopoint_type_id)
);

CREATE TABLE IF NOT EXISTS users
(
    id       bigserial PRIMARY KEY,
    username VARCHAR(30)  NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role     enum_role    NOT NULL,
    CHECK ((username != '') AND (password != ''))
);