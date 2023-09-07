INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest'),
       ('User4', 'user4@yandex.ru', '{noop}password4'),/*4*/
       ('User5', 'user5@yandex.ru', '{noop}password5'),/*5*/
       ('User6', 'user6@yandex.ru', '{noop}password6');/*6*/

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2),
       ('USER', 4),
       ('USER', 5),
       ('USER', 6);

INSERT INTO restaurant (name)
VALUES ('Tokio'),
       ('Ginza'),
       ('Bahroma'),
       ('Without Today Menu'),
       ('Without Menu');

INSERT INTO menu (menu_date, restaurant_id)
VALUES (current_date, 1), /*1*/
       (current_date, 2), /*2*/
       (current_date, 3), /*3*/
       ('2021-05-05', 1), /*4*/
       ('2021-05-05', 2), /*5*/
       ('2021-05-05', 3), /*6*/
       ('2021-05-05', 4), /*7*/
       ('2024-02-28', 4); /*8*/

INSERT INTO dish_item (menu_id, price, dish_name)
VALUES (1, 100, 'burgers'),
       (1, 120, 'middle burgers'),
       (1, 150, 'big burgers'),
       (2, 101, 'sushi'),
       (2, 121, 'middle sushi'),
       (2, 151, 'big sushi'),
       (3, 102, 'pizza'),
       (3, 122, 'middle pizza'),
       (3, 152, 'big pizza'),
       (4, 152, 'restaurant_1 old menu item'),
       (4, 152, 'restaurant_1 old menu item2'),
       (4, 152, 'restaurant_1 old menu item3'),
       (5, 152, 'restaurant_2 old menu item'),
       (5, 152, 'restaurant_2 old menu item2'),
       (5, 152, 'restaurant_2 old menu item3'),
       (6, 152, 'restaurant_3 old menu item'),
       (6, 152, 'restaurant_3 old menu item2'),
       (6, 152, 'restaurant_3 old menu item3'),
       (7, 152, 'rest_without_today old menu item'),
       (7, 152, 'rest_without_today old menu item2'),
       (7, 152, 'rest_without_today old menu item3'),
       (8, 152, 'rest_without_today future menu item'),
       (8, 152, 'rest_without_today future menu item2'),
       (8, 152, 'rest_without_today future menu item3');

INSERT INTO vote (vote_date, user_id, restaurant_id)
VALUES (current_date, 4, 1), /*1*/
       (current_date, 5, 2), /*2*/
       (current_date, 6, 2), /*3*/
       ('2021-05-05', 4, 3), /*4*/
       ('2021-05-05', 5, 3), /*5*/
       ('2021-05-05', 6, 3); /*6*/