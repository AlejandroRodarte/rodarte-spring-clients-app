INSERT INTO regions (id, name) VALUES (1, 'South America');
INSERT INTO regions (id, name) VALUES (2, 'Center America');
INSERT INTO regions (id, name) VALUES (3, 'North America');
INSERT INTO regions (id, name) VALUES (4, 'Europe');
INSERT INTO regions (id, name) VALUES (5, 'Asia');
INSERT INTO regions (id, name) VALUES (6, 'Africa');
INSERT INTO regions (id, name) VALUES (7, 'Oceania');
INSERT INTO regions (id, name) VALUES (8, 'Antarctica');

INSERT INTO clients (first_name, last_name, email, created_at, region_id) VALUES('Mr. John', 'Doe', 'john.doe@gmail.com', '2018-01-02', 1);
INSERT INTO clients (first_name, last_name, email, created_at, region_id) VALUES('Andrés', 'Guzmán', 'profesor@bolsadeideas.com', '2018-01-01', 2);
INSERT INTO clients (first_name, last_name, email, created_at, region_id) VALUES('Linus', 'Torvalds', 'linus.torvalds@gmail.com', '2018-01-03', 4);
INSERT INTO clients (first_name, last_name, email, created_at, region_id) VALUES('Rasmus', 'Lerdorf', 'rasmus.lerdorf@gmail.com', '2018-01-04', 4);
INSERT INTO clients (first_name, last_name, email, created_at, region_id) VALUES('Erich', 'Gamma', 'erich.gamma@gmail.com', '2018-02-01', 4);
INSERT INTO clients (first_name, last_name, email, created_at, region_id) VALUES('Richard', 'Helm', 'richard.helm@gmail.com', '2018-02-10', 3);
INSERT INTO clients (first_name, last_name, email, created_at, region_id) VALUES('Ralph', 'Johnson', 'ralph.johnson@gmail.com', '2018-02-18', 3);
INSERT INTO clients (first_name, last_name, email, created_at, region_id) VALUES('John', 'Vlissides', 'john.vlissides@gmail.com', '2018-02-28', 3);
INSERT INTO clients (first_name, last_name, email, created_at, region_id) VALUES('Dr. James', 'Gosling', 'james.gosling@gmail.com', '2018-03-03', 3);
INSERT INTO clients (first_name, last_name, email, created_at, region_id) VALUES('Magma', 'Lee', 'magma.lee@gmail.com', '2018-03-04', 5);
INSERT INTO clients (first_name, last_name, email, created_at, region_id) VALUES('Tornado', 'Roe', 'tornado.roe@gmail.com', '2018-03-05', 6);
INSERT INTO clients (first_name, last_name, email, created_at, region_id) VALUES('Jade', 'Doe', 'jane.doe@gmail.com', '2018-03-06', 7);

INSERT INTO users (username, password, enabled, first_name, last_name, email) VALUES ('alejandro', '$2a$10$RNP98habOiimHtYOesA7eeM2yL8b9FzKnST474NcAA98bIQcDxnhW', 1, 'Alejandro', 'Rodarte', 'alejandrorodarte1@gmail.com');
INSERT INTO users (username, password, enabled, first_name, last_name, email) VALUES ('admin', '$2a$10$qR78dK9pyfzGcQNDr6KM3esutNP2GPUiKityz9cKmr2vrOahL.WaS', 1, 'Patricia', 'Mendoza', 'paty@gmail.com');

INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);
