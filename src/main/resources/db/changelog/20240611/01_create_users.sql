CREATE TABLE users
(
    id              BIGSERIAL PRIMARY KEY,
    username        VARCHAR                  NOT NULL,
    email           VARCHAR                  NOT NULL,
    create_datetime TIMESTAMP WITH TIME ZONE NOT NULL,
    update_datetime TIMESTAMP WITH TIME ZONE NOT NULL
);

COMMENT ON TABLE users IS 'таблица пользователей';
COMMENT ON COLUMN users.id IS 'идентификатор записи';
COMMENT ON COLUMN users.username IS 'имя юзера';
COMMENT ON COLUMN users.email IS 'электронная почта';

CREATE INDEX IF NOT EXISTS users_users_idx ON users (username);
CREATE INDEX IF NOT EXISTS users_email_idx ON users (email);