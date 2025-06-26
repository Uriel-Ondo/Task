# Task Manager - Guide de démarrage

Ce guide explique comment configurer la base de données MySQL et lancer l'application de gestion des tâches.

## 1. Création de la base de données, des tables et d'un utilisateur de test

Dans votre interface MySQL (ex : phpMyAdmin, MySQL Workbench ou terminal), exécutez les commandes suivantes :

```sql
-- Créer la base de données
CREATE DATABASE task_manager;

-- Sélectionner la base de données
USE task_manager;

-- Créer la table des utilisateurs
CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(50) NOT NULL
);

-- Créer la table des tâches
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
```

## 2. Ajouter le connecteur MySQL

- Téléchargez le connecteur MySQL (JAR) depuis : [https://dev.mysql.com/downloads/connector/j/](https://dev.mysql.com/downloads/connector/j/)
- Ajoutez-le à votre projet Java :
  - Allez dans `File > Project Structure > Libraries`
  - Cliquez sur `+` puis ajoutez le JAR téléchargé

## 3. Lancer l'application

- Exécutez la classe `LoginFrame`.
- Utilisez les identifiants suivants pour vous connecter :
  - **Nom d'utilisateur** : `testuser`
  - **Mot de passe** : `passer`
- Vous accéderez à l'interface de gestion des tâches.

---
````
