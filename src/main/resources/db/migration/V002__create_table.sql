DROP TABLE IF EXISTS GAMES;

DROP TABLE IF EXISTS CATEGORIES;

DROP TABLE IF EXISTS PLAYERS;

CREATE TABLE GAMES
(
    id              serial,
    playerFirstName varchar(200) not null,
    playerLastName  varchar(50)  not null,
    gameDate        varchar(100),
    gameName        varchar(250),
    category        varchar(50),
    dice            varchar(100),
    score           varchar(250)
);

CREATE TABLE CATEGORIES
(
    id   serial,
    name varchar(200) not null

);

INSERT INTO CATEGORIES (name)
VALUES ('ONES'),
       ('TWOS'),
       ('THREES'),
       ('FOURS'),
       ('FIVES'),
       ('SIXES'),
       ('ONE PAIR'),
       ('TWO PAIRS'),
       ('THREE OF A KIND'),
       ('FOUR OF A KIND'),
       ('SMALL STRAIGHT'),
       ('LARGE STRAIGHT'),
       ('FULL HOUSE'),
       ('CHANCE'),
       ('YATZY');


CREATE TABLE PLAYERS
(
    id        serial,
    firstName varchar(200) not null,
    lastName  varchar(200) not null

);
