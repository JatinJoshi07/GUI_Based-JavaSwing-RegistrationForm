CREATE DATABASE registration_db;

USE registration_db;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50),
    email VARCHAR(100),
    phone VARCHAR(15),
    address TEXT,
    country VARCHAR(50),
    dob DATE,
    gender VARCHAR(10),
    security_question VARCHAR(100),
    security_answer VARCHAR(100),
    password VARCHAR(255)
);

