INSERT INTO proger_trainer.users(username, password, email, role, create_datetime, update_datetime)
VALUES ('username_1', '$2a$10$fBHkJRJvb0SVHwd4iCDFEe3j45Es5QamDhaBcwJU8CR1bUsmPMbiy', 'email_1', 'USER',
        now()::timestamp, now()::timestamp),
       ('username_2', '$2a$10$6UxouCI9K4DvbzmiWV12euOQrn/9hPntiky3qSRyaGjnPRDmIE.Eq', 'email_2', 'USER',
        now()::timestamp, now()::timestamp);

INSERT INTO proger_trainer.token(token, revoked, expired, user_id)
VALUES ('eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbF8xIiwiaWF0IjoxNzM4NzAyNTQxLCJleHAiOjE3Mzg3ODg5NDF9.JZapmuCFaloANYvZRLjRmejSbgosxiauCeDRWVaGhhw',
        false, false, (SELECT id FROM proger_trainer.users u WHERE email = 'email_1')),
       ('eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbF8yIiwiaWF0IjoxNzM4NzAyNTQxLCJleHAiOjE3Mzg3ODg5NDF9.2M7AmxUr22WlJ6ttSEQ7jj9BzsvbGV2ML-rY9PiphQM',
        false, false, (SELECT id FROM proger_trainer.users WHERE email = 'email_2'))