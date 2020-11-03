INSERT INTO `roles` (name) VALUES ('ADMIN');
INSERT INTO `roles` (name) VALUES ('USER');

INSERT INTO `users` (name,lastname,username, email, password, role_id) VALUES ('Admin','Admin','admin','admin@admin.com','$2a$10$G4eFv5UqL6dnhWYbfq9OQO8zBjv1TU3Hg6YTee76UUg8485bb14R.',1);
