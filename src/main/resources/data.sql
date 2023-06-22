INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO restaurant (name)
VALUES ('Tokio'),
       ('Ginza'),
       ('Bahroma'),
       ('Without Menu');

INSERT INTO menu (date, restaurant_id)
VALUES (current_date, 1),   /*1*/
       (current_date, 2),   /*2*/
       (current_date, 3),   /*3*/
       ('2021-05-05', 1),    /*4*/
       ('2021-05-05', 2),    /*5*/
       ('2021-05-05', 3);    /*6*/

INSERT INTO menu_items (menu_id, price, item)
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
       (6, 152, 'restaurant_3 old menu item3');