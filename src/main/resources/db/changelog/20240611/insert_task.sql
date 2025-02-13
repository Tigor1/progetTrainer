DO $$
    DECLARE user_id1 BIGINT;
    DECLARE user_id2 BIGINT;
    BEGIN
    SELECT id INTO user_id1 FROM proger_trainer.users WHERE email = 'email_1';
    SELECT id INTO user_id2 FROM proger_trainer.users WHERE email = 'email_2';
INSERT INTO proger_trainer.task(title, difficulty, description_of_task, solution, number_of_solutions, user_id, create_datetime, update_datetime)
VALUES('tittle_1', 'EASY', 'description_of_task_1', 'solution_1', 3, user_id1, now()::timestamp, now()::timestamp),
      ('tittle_2', 'MEDIUM', 'description_of_task_2', 'solution_2', 6, user_id2, now()::timestamp, now()::timestamp),
      ('tittle_3', 'HARD', 'description_of_task_3', 'solution_3', 0, user_id1, now()::timestamp, now()::timestamp),
      ('tittle_4', 'EASY', 'description_of_task_4', 'solution_4', 3, user_id2, now()::timestamp, now()::timestamp),
      ('tittle_5', 'MEDIUM', 'description_of_task_5', 'solution_5', 4, user_id2, now()::timestamp, now()::timestamp),
      ('tittle_6', 'HARD', 'description_of_task_6', 'solution_6', 1, user_id2, now()::timestamp, now()::timestamp),
      ('tittle_7', 'EASY', 'description_of_task_7', 'solution_7', 192, user_id2, now()::timestamp, now()::timestamp),
      ('tittle_8', 'MEDIUM', 'description_of_task_8', 'solution_8', 99, user_id2, now()::timestamp, now()::timestamp),
      ('tittle_9', 'EASY', 'description_of_task_9', 'solution_9', 12, user_id2, now()::timestamp, now()::timestamp),
      ('tittle_10', 'EASY', 'description_of_task_10', 'solution_10', 1002, user_id2, now()::timestamp, now()::timestamp),
      ('tittle_11', 'HARD', 'description_of_task_11', 'solution_11', 1, user_id2, now()::timestamp, now()::timestamp),
      ('tittle_12', 'EASY', 'description_of_task_12', 'solution_12', 78, user_id2, now()::timestamp, now()::timestamp),
      ('tittle_13', 'HARD', 'description_of_task_13', 'solution_13', 4, user_id2, now()::timestamp, now()::timestamp),
      ('tittle_14', 'EASY', 'description_of_task_14', 'solution_14', 1092, user_id1,  now()::timestamp, now()::timestamp),
      ('tittle_15', 'EASY', 'description_of_task_15', 'solution_15', 1245, user_id1,  now()::timestamp, now()::timestamp),
      ('tittle_16', 'MEDIUM', 'description_of_task_16', 'solution_16', 346, user_id1,  now()::timestamp, now()::timestamp),
      ('tittle_17', 'EASY', 'description_of_task_17', 'solution_17', 3314, user_id1,  now()::timestamp, now()::timestamp),
      ('tittle_18', 'MEDIUM', 'description_of_task_18', 'solution_18', 323, user_id1,  now()::timestamp, now()::timestamp),
      ('tittle_19', 'EASY', 'description_of_task_19', 'solution_19', 3231, user_id1,  now()::timestamp, now()::timestamp),
      ('tittle_20', 'HARD', 'description_of_task_20', 'solution_20', 32, user_id1,  now()::timestamp, now()::timestamp),
      ('tittle_21', 'EASY', 'description_of_task_21', 'solution_21', 31241, user_id1,  now()::timestamp, now()::timestamp),
      ('tittle_22', 'HARD', 'description_of_task_22', 'solution_22', 31, user_id1,  now()::timestamp, now()::timestamp),
      ('tittle_23', 'HARD', 'description_of_task_23', 'solution_23', 97, user_id1,  now()::timestamp, now()::timestamp),
      ('tittle_24', 'MEDIUM', 'description_of_task_24', 'solution_24', 1242, user_id1,  now()::timestamp, now()::timestamp),
      ('tittle_25', 'HARD', 'description_of_task_25', 'solution_25', 643, user_id1,  now()::timestamp, now()::timestamp),
      ('tittle_26', 'EASY', 'description_of_task_26', 'solution_26', 124123, user_id1,  now()::timestamp, now()::timestamp);

END $$