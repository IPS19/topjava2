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
       ('Bahroma');

INSERT INTO menu (date, restaurant_id)
VALUES (current_date, 1),
       (current_date, 2),
       (current_date, 3);

INSERT INTO menu_items (menu_id, price, item)
VALUES (1, 100, 'burgers'),
       (1, 120, 'middle burgers'),
       (1, 150, 'big burgers'),
       (2, 101, 'sushi'),
       (2, 121, 'middle sushi'),
       (2, 151, 'big sushi'),
       (3, 102, 'pizza'),
       (3, 122, 'middle pizza'),
       (3, 152, 'big pizza');
