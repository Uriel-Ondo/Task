1- Créer une base de données, des tables et un utilisateur test:

CREATE DATABASE task_manager;

USE task_manager;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL
);

CREATE TABLE task (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    due_date DATE,
    status VARCHAR(20) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);


-- Insérer un utilisateur de test
INSERT INTO users (username, password) VALUES ('testuser', 'passer');


2- Ajouter le connecteur MySQL :

Télécharge le JAR depuis https://dev.mysql.com/downloads/connector/j/.
Ajoute-le dans File > Project Structure > Libraries.


3-Lancer l'application :

Exécute la classe LoginFrame.
Entre les identifiants (testuser et passer) pour accéder à l'interface de gestion des tâches.
