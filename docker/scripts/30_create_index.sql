CREATE INDEX ecopoint_geometry_idx ON ecopoint USING GIST (position);

CREATE OR REPLACE FUNCTION generate_random_number(min_value FLOAT, max_value FLOAT)
    RETURNS FLOAT AS
$$
BEGIN
    RETURN min_value + random() * (max_value - min_value);
END;
$$
    LANGUAGE plpgsql;