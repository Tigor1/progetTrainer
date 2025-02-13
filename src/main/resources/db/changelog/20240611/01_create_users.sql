CREATE TABLE IF NOT EXISTS users
(
    id              BIGSERIAL PRIMARY KEY,
    username        VARCHAR                  NOT NULL,
    password        VARCHAR                  NOT NULL,
    email           VARCHAR                  NOT NULL,
    role            VARCHAR                  NOT NULL,
    create_datetime TIMESTAMP WITH TIME ZONE NOT NULL,
    update_datetime TIMESTAMP WITH TIME ZONE NOT NULL
);

COMMENT ON TABLE users IS 'таблица пользователей';
COMMENT ON COLUMN users.id IS 'идентификатор записи';
COMMENT ON COLUMN users.username IS 'имя юзера';
COMMENT ON COLUMN users.email IS 'электронная почта';

CREATE INDEX IF NOT EXISTS users_users_idx ON users (username);
CREATE INDEX IF NOT EXISTS users_email_idx ON users (email);

CREATE TABLE IF NOT EXISTS token
(
    id      BIGSERIAL PRIMARY KEY,
    token   VARCHAR UNIQUE,
    revoked BOOLEAN NOT NULL DEFAULT false,
    expired BOOLEAN NOT NULL DEFAULT false,
    user_id BIGINT  NOT NULL,
    CONSTRAINT token_user_id FOREIGN KEY (user_id) REFERENCES users (id) DEFERRABLE INITIALLY DEFERRED
);