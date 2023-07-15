CREATE TABLE IF NOT EXISTS messages_table (
    id SERIAL PRIMARY KEY,
    type VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    message VARCHAR(255) NOT NULL,
    timestamp BIGINT NOT NULL
    );