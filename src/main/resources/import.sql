
INSERT INTO `scopes` (name) VALUES ('users.super'); /*Puede hacer y ver todo*/

INSERT INTO `scopes` (name) VALUES ('users.adm'); /*Solo puede acceder a los datos de los normal users*/

INSERT INTO `scopes` (name) VALUES ('users.update-self');
INSERT INTO `scopes` (name) VALUES ('users.read-min');




INSERT INTO `roles` (name) VALUES ('SUPERADMIN'); /*Los scpoes criticos => read,write,delete,update*/
INSERT INTO `roles` (name) VALUES ('ADMIN'); /*los scopes adm*/
INSERT INTO `roles` (name) VALUES ('USER'); /*scopes */



INSERT INTO `users` (name,lastname,username, email, password, role_id) VALUES ('Admin','Admin','Admin','admin@admin.com','$2a$10$G4eFv5UqL6dnhWYbfq9OQO8zBjv1TU3Hg6YTee76UUg8485bb14R.',1);


/*SUPERADMIN*/
INSERT INTO `roles_scopes` (role_id,scopes_id) VALUES (1,1);

/*ADMIN*/
INSERT INTO `roles_scopes` (role_id,scopes_id) VALUES (2,2);

/*USERS*/
INSERT INTO `roles_scopes` (role_id,scopes_id) VALUES (3,3);
INSERT INTO `roles_scopes` (role_id,scopes_id) VALUES (3,4);
