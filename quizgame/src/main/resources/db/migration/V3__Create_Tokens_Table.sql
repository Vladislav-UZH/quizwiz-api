CREATE TABLE tokens (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    token_value TEXT NOT NULL UNIQUE,
    token_type VARCHAR(50) NOT NULL,
    expiration_date TIMESTAMP NOT NULL
);