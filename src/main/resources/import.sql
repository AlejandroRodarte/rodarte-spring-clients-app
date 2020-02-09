INSERT INTO regions (id, name) VALUES (1, 'Sudamerica');
INSERT INTO regions (id, name) VALUES (2, 'Centroamerica');
INSERT INTO regions (id, name) VALUES (3, 'Norteamerica');
INSERT INTO regions (id, name) VALUES (4, 'Europa');
INSERT INTO regions (id, name) VALUES (5, 'Asia');
INSERT INTO regions (id, name) VALUES (6, 'Africa');
INSERT INTO regions (id, name) VALUES (7, 'Oceania');
INSERT INTO regions (id, name) VALUES (8, 'Antartida');

INSERT INTO clients (region_id, first_name, last_name, email, created_at) VALUES(1, 'Andres', 'Guzman', 'profesor@bolsadeideas.com', '2018-01-01');
INSERT INTO clients (region_id, first_name, last_name, email, created_at) VALUES(2, 'Mr. John', 'Doe', 'john.doe@gmail.com', '2018-01-02');
INSERT INTO clients (region_id, first_name, last_name, email, created_at) VALUES(4, 'Linus', 'Torvalds', 'linus.torvalds@gmail.com', '2018-01-03');
INSERT INTO clients (region_id, first_name, last_name, email, created_at) VALUES(4, 'Rasmus', 'Lerdorf', 'rasmus.lerdorf@gmail.com', '2018-01-04');
INSERT INTO clients (region_id, first_name, last_name, email, created_at) VALUES(4, 'Erich', 'Gamma', 'erich.gamma@gmail.com', '2018-02-01');
INSERT INTO clients (region_id, first_name, last_name, email, created_at) VALUES(3, 'Richard', 'Helm', 'richard.helm@gmail.com', '2018-02-10');
INSERT INTO clients (region_id, first_name, last_name, email, created_at) VALUES(3, 'Ralph', 'Johnson', 'ralph.johnson@gmail.com', '2018-02-18');
INSERT INTO clients (region_id, first_name, last_name, email, created_at) VALUES(3, 'John', 'Vlissides', 'john.vlissides@gmail.com', '2018-02-28');
INSERT INTO clients (region_id, first_name, last_name, email, created_at) VALUES(3, 'Dr. James', 'Gosling', 'james.gosling@gmail.com', '2018-03-03');
INSERT INTO clients (region_id, first_name, last_name, email, created_at) VALUES(5, 'Magma', 'Lee', 'magma.lee@gmail.com', '2018-03-04');
INSERT INTO clients (region_id, first_name, last_name, email, created_at) VALUES(6, 'Tornado', 'Roe', 'tornado.roe@gmail.com', '2018-03-05');
INSERT INTO clients (region_id, first_name, last_name, email, created_at) VALUES(7, 'Jade', 'Doe', 'jane.doe@gmail.com', '2018-03-06');

INSERT INTO `users` (username, password, enabled, first_name, last_name, email) VALUES ('andres','$2a$10$C3Uln5uqnzx/GswADURJGOIdBqYrly9731fnwKDaUdBkt/M3qvtLq',1, 'Andres', 'Guzman','profesor@bolsadeideas.com');
INSERT INTO `users` (username, password, enabled, first_name, last_name, email) VALUES ('admin','$2a$10$RmdEsvEfhI7Rcm9f/uZXPebZVCcPC7ZXZwV51efAvMAp1rIaRAfPK',1, 'John', 'Doe','jhon.doe@bolsadeideas.com');

INSERT INTO `roles` (name) VALUES ('ROLE_USER');
INSERT INTO `roles` (name) VALUES ('ROLE_ADMIN');

INSERT INTO `users_roles` (user_id, role_id) VALUES (1, 1);
INSERT INTO `users_roles` (user_id, role_id) VALUES (2, 2);
INSERT INTO `users_roles` (user_id, role_id) VALUES (2, 1);

INSERT INTO products (name, price, created_at) VALUES('Panasonic Pantalla LCD', 259990, NOW());
INSERT INTO products (name, price, created_at) VALUES('Sony Camara digital DSC-W320B', 123490, NOW());
INSERT INTO products (name, price, created_at) VALUES('Apple iPod shuffle', 1499990, NOW());
INSERT INTO products (name, price, created_at) VALUES('Sony Notebook Z110', 37990, NOW());
INSERT INTO products (name, price, created_at) VALUES('Hewlett Packard Multifuncional F2280', 69990, NOW());
INSERT INTO products (name, price, created_at) VALUES('Bianchi Bicicleta Aro 26', 69990, NOW());
INSERT INTO products (name, price, created_at) VALUES('Mica Comoda 5 Cajones', 299990, NOW());

INSERT INTO bills (description, comment, client_id, created_at) VALUES('Factura equipos de oficina', null, 1, NOW());

INSERT INTO bill_items (quantity, bill_id, product_id) VALUES(1, 1, 1);
INSERT INTO bill_items (quantity, bill_id, product_id) VALUES(2, 1, 4);
INSERT INTO bill_items (quantity, bill_id, product_id) VALUES(1, 1, 5);
INSERT INTO bill_items (quantity, bill_id, product_id) VALUES(1, 1, 7);

INSERT INTO bills (description, comment, client_id, created_at) VALUES('Factura Bicicleta', 'Alguna nota importante!', 1, NOW());
INSERT INTO bill_items (quantity, bill_id, product_id) VALUES(3, 2, 6);