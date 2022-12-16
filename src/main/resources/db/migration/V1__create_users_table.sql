CREATE TABLE users
(
    id           INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name         VARCHAR(255) not NULL,
    surname      VARCHAR(255) not NULL,
    patronymic   VARCHAR(255),
    phone_number VARCHAR(50),
    email        VARCHAR(320)

);