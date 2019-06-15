USE seed;
INSERT INTO users (user_name, user_pass) VALUES ('user', '$2a$10$u8vhHCd7VwXZJO3NbO1PY.9IAe.JySOhnCkpliI0FcMOXC2yGJwwK');
INSERT INTO users (user_name, user_pass) VALUES ('admin', '$2a$10$R/Mx1d8pXYde/6JQbgPiouYsqsSvrP0pLnzz3iDGScSri8RUk/Hd.');
INSERT INTO roles (role_name) VALUES ('user');
INSERT INTO roles (role_name) VALUES ('admin');
INSERT INTO user_roles (user_name, role_name) VALUES ('user', 'user');
INSERT INTO user_roles (user_name, role_name) VALUES ('admin', 'admin');