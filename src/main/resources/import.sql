
INSERT INTO `scopes` (name) VALUES ('users.super'); 

INSERT INTO `scopes` (name) VALUES ('users.adm'); 

INSERT INTO `scopes` (name) VALUES ('users.update-self');
INSERT INTO `scopes` (name) VALUES ('users.read-min');

INSERT INTO `scopes` (name) VALUES ('files.excel');
INSERT INTO `scopes` (name) VALUES ('files.pdf');




INSERT INTO `roles` (name) VALUES ('SUPERADMIN'); 
INSERT INTO `roles` (name) VALUES ('ADMIN'); 
INSERT INTO `roles` (name) VALUES ('USER'); 



INSERT INTO `users` (name,lastname,username, email, password, role_id) VALUES ('Admin','Admin','Admin','admin@admin.com','$2a$10$G4eFv5UqL6dnhWYbfq9OQO8zBjv1TU3Hg6YTee76UUg8485bb14R.',1);


/*SUPERADMIN*/
INSERT INTO `roles_scopes` (role_id,scopes_id) VALUES (1,1);

/*ADMIN*/
INSERT INTO `roles_scopes` (role_id,scopes_id) VALUES (2,2);

/*USERS*/
INSERT INTO `roles_scopes` (role_id,scopes_id) VALUES (3,3);
INSERT INTO `roles_scopes` (role_id,scopes_id) VALUES (3,4);

/*FILES*/

/*Excel*/
INSERT INTO `roles_scopes` (role_id,scopes_id) VALUES (1,5);
INSERT INTO `roles_scopes` (role_id,scopes_id) VALUES (2,5);
INSERT INTO `roles_scopes` (role_id,scopes_id) VALUES (3,5);

/*Pdf*/
INSERT INTO `roles_scopes` (role_id,scopes_id) VALUES (1,6);
INSERT INTO `roles_scopes` (role_id,scopes_id) VALUES (2,6);
INSERT INTO `roles_scopes` (role_id,scopes_id) VALUES (3,6);
