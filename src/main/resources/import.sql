INSERT INTO `scopes` (name) VALUES ('users.read');
INSERT INTO `scopes` (name) VALUES ('users.write');
INSERT INTO `scopes` (name) VALUES ('user.delete');
INSERT INTO `scopes` (name) VALUES ('user.update');
INSERT INTO `scopes` (name) VALUES ('user.update-self');

INSERT INTO `roles` (name) VALUES ('ADMIN');
INSERT INTO `roles` (name) VALUES ('USER');



INSERT INTO `users` (name,lastname,username, email, password, role_id) VALUES ('Admin','Admin','admin','admin@admin.com','$2a$10$G4eFv5UqL6dnhWYbfq9OQO8zBjv1TU3Hg6YTee76UUg8485bb14R.',1);



INSERT INTO `roles_scopes` (role_id,scopes_id) VALUES (1,1);
INSERT INTO `roles_scopes` (role_id,scopes_id) VALUES (1,2);
INSERT INTO `roles_scopes` (role_id,scopes_id) VALUES (1,3);
INSERT INTO `roles_scopes` (role_id,scopes_id) VALUES (1,4);
INSERT INTO `roles_scopes` (role_id,scopes_id) VALUES (2,5);