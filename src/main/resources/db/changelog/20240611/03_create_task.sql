CREATE TABLE task
(
    id                    BIGSERIAL PRIMARY KEY,
    tittle                VARCHAR                  NOT NULL,
    task_difficulty_level VARCHAR                  NOT NULL,
    description_of_task   TEXT                     NOT NULL DEFAULT 'default description_of_task',
    solution              TEXT                     NOT NULL DEFAULT 'default solution',
    number_of_solutions   INT                               DEFAULT 0,
    user_id               INT REFERENCES users (id),
    create_datetime       TIMESTAMP WITH TIME ZONE NOT NULL,
    update_datetime       TIMESTAMP WITH TIME ZONE NOT NULL
);

COMMENT ON TABLE task IS 'таблица задач';
COMMENT ON COLUMN task.id IS 'идентификатор записи';
COMMENT ON COLUMN task.tittle IS 'заголовок задачи';
COMMENT ON COLUMN task.task_difficulty_level IS 'сложность задачи';
COMMENT ON COLUMN task.description_of_task IS 'описание задания';
COMMENT ON COLUMN task.solution IS 'решение задания';
COMMENT ON COLUMN task.number_of_solutions IS 'количество решений задания';

CREATE INDEX IF NOT EXISTS task_tittle_idx ON task (tittle);