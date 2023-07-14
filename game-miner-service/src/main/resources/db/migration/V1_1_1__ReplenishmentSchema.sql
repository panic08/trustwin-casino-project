CREATE TABLE IF NOT EXISTS replenishments_table (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    wallet_id VARCHAR(255) NOT NULL,
    amount DOUBLE PRECISION NOT NULL,
    payment_method VARCHAR(255) NOT NULL,
    timestamp BIGINT
);