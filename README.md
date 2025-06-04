# GUI_Based-JavaSwing-RegistrationForm
User registration form built with Java Swing, featuring strong password validation and secure MySQL storage.
---
# 📝 Registration Form Application - Java Swing
A **Java Swing** desktop application for user registration with **form validation**, **password strength checking**, and **MySQL database storage**. Collects user details such as username, email, phone, DOB, security questions, and more — all wrapped in a clean GUI.

---

## 🚀 Features

✅ Modern, responsive Java Swing UI
✅ Input validation for all fields (email, phone, password strength, etc.)
✅ Password visibility toggle for easy entry
✅ Security question & answer for account recovery
✅ Terms & conditions acceptance before registration
✅ Saves user data securely into MySQL database
✅ Reset button to clear form anytime

---

## 🛠 Installation & Setup

### 1️⃣ Database Setup

Create the database and users table by running or use the Provided SQL File:

```sql
CREATE DATABASE registration_db;

USE registration_db;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    address TEXT,
    country VARCHAR(50) NOT NULL,
    dob DATE NOT NULL,
    gender VARCHAR(10) NOT NULL,
    security_question VARCHAR(255) NOT NULL,
    security_answer VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### 2️⃣ JDBC Driver

* Download **MySQL Connector/J** from [MySQL official](https://dev.mysql.com/downloads/connector/j/)
* Extract and add the `.jar` file to your project’s classpath

### 3️⃣ Configure Database Credentials

Update the following constants in `RegistrationForm.java`:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/registration_db";
private static final String DB_USER = "your_mysql_username";
private static final String DB_PASSWORD = "your_mysql_password";
```

### 4️⃣ Compile & Run

**Compile:**

javac RegistrationForm.java

**Run (include MySQL driver in classpath):**

```bash
# Windows
java -cp ".;path\to\mysql-connector-j-8.0.xx.jar" RegistrationForm

# Linux/macOS
java -cp ".:path/to/mysql-connector-j-8.0.xx.jar" RegistrationForm
```

---

## 🎯 How to Use

1. Fill in all required fields (\*marked with an asterisk)
2. Make sure password meets complexity rules:

   * Minimum 8 chars
   * At least one uppercase & lowercase letter
   * At least one digit & special character
3. Select a security question and provide an answer
4. Agree to terms and conditions checkbox
5. Click **Register** to submit your information
6. Use **Reset** to clear the form anytime

---

## 📌 Validation Rules Summary

| Field              | Requirement                                                |
| ------------------ | ---------------------------------------------------------- |
| Username           | Required                                                   |
| Email              | Valid email format                                         |
| Phone              | Exactly 10 digits                                          |
| Country            | Must select                                                |
| Date of Birth      | Complete & valid date                                      |
| Gender             | Must select                                                |
| Security Question  | Must select                                                |
| Security Answer    | Required                                                   |
| Password           | Minimum 8 chars, uppercase, lowercase, digit, special char |
| Confirm Password   | Must match password                                        |
| Terms & Conditions | Must be checked                                            |

---

## ⚠️ Known Issues & Troubleshooting

* **Database connection failure?**
  Check MySQL server status and credentials in the code.

* **ClassNotFoundException?**
  Confirm the MySQL Connector/J `.jar` is added to your classpath.

* **Form validation errors?**
  Follow the error messages carefully and correct input fields accordingly.

---

## 🤝 Contributing

Want to improve this project? Fork the repo, add features or fixes, and submit a pull request!

---

## 👨‍💻 Developed By

Er. Jatin Joshi ✨

---


