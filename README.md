# OrangeHRM Login Testing

## 📌 Project Overview

This project focuses on **Login Page Testing** for the OrangeHRM application. The purpose is to verify that users can successfully log in with valid credentials and that appropriate validation messages are displayed for invalid login attempts.

## 🧪 Testing Scope

The following scenarios are covered:

* Login with valid username and password
* Login with invalid username
* Login with invalid password
* Login with both username and password invalid
* Login with empty username
* Login with empty password
* Login with both fields empty
* Verification of error/validation messages
* Verification of successful login

## 🌐 Application Under Test

**OrangeHRM Open Source Demo**

URL: https://opensource-demo.orangehrmlive.com/

### Demo Credentials

```text
Username: Admin
Password: admin123
```

> These credentials are for the public OrangeHRM demo environment and may change over time.

## 🛠️ Tools & Technologies

* Selenium WebDriver
* Java
* TestNG
* Maven
* Git & GitHub
* Page Object Model (POM)

## 📂 Project Structure

```text
OrangeHRM-Login-Testing/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── pages/
│   │
│   └── test/
│       └── java/
│           └── tests/
│
├── pom.xml
├── testng.xml
└── README.md
```

## ▶️ How to Run

### 1. Clone the repository

```bash
git clone <your-github-repository-url>
```

### 2. Open the project

Open the project in IntelliJ IDEA, Eclipse, or another Java IDE.

### 3. Install dependencies

```bash
mvn clean install
```

### 4. Run the tests

```bash
mvn test
```

## 📊 Test Cases

| Test Case | Description                         | Expected Result                        |
| --------- | ----------------------------------- | -------------------------------------- |
| TC01      | Valid username + valid password     | User successfully logs in              |
| TC02      | Invalid username + valid password   | Login error is displayed               |
| TC03      | Valid username + invalid password   | Login error is displayed               |
| TC04      | Invalid username + invalid password | Login error is displayed               |
| TC05      | Empty username + valid password     | Username validation is displayed       |
| TC06      | Valid username + empty password     | Password validation is displayed       |
| TC07      | Empty username + empty password     | Required field validation is displayed |

## 🎯 Objective

The main objective of this project is to demonstrate **UI automation testing skills** by automating the OrangeHRM login functionality and validating different positive and negative login scenarios.

## 👨‍💻 Author

**Ridmi Sansala**


