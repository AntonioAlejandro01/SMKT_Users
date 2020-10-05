INSERT INTO `roles` (name) VALUES ('ADMIN');
INSERT INTO `roles` (name) VALUES ('USER');

INSERT INTO `users` (name,lastname,username, email, password, photo, role_id) VALUES ('Antonio Alejandro','Serrano Ramírez','Antonio Alejandro','antonioalejandro@gmail.com','$2a$10$G4eFv5UqL6dnhWYbfq9OQO8zBjv1TU3Hg6YTee76UUg8485bb14R.','perfil',1);

INSERT INTO `users` (name,lastname, username, email, password, photo, role_id) VALUES ('test','test2','test','test@test.com','$2a$10$G4eFv5UqL6dnhWYbfq9OQO8zBjv1TU3Hg6YTee76UUg8485bb14R.','test',2);
