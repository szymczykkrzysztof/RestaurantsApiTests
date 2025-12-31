# RestaurantsApiTests

Automated REST API test framework for the **Restaurants API**.  
The project focuses on **reliable, maintainable, and scalable API test automation**, implemented with industry-standard tools and practices.

This repository is part of my professional QA Automation portfolio. 

---

## 📌 Project Overview

This project contains automated tests for the Restaurants REST API.  
The main goal is to verify correctness, stability, and contract consistency of API endpoints by covering:

- API availability and basic health checks
- CRUD operations
- Positive and negative scenarios
- Request and response validation
- Error handling and edge cases

The framework is designed to be **easy to extend**, **CI/CD ready**, and suitable for real-world enterprise API testing. Tests are executed automatically via GitHub Actions pipeline using Maven and JUnit on every commit.

---

## 🧰 Technology Stack

- **Java**
- **REST Assured** – fluent API for REST testing
- **JUnit 5** – test framework
- **Maven** – build and dependency management
- **JSON** – request/response payloads
- **GitHub Actions / Jenkins** – CI/CD support (optional)

---

## ✅ What Is Being Tested

The test suite validates:
- HTTP status codes
- Response body content
- Mandatory and optional fields
- Error responses for invalid input
- Data consistency across endpoints
- Tests are written in a clear and readable manner, using REST Assured’s fluent syntax.
- Authentication and authorization scenarios

## 📊 Test Reporting

By default, results are printed in the console after execution.
If reporting tools (e.g. Allure) are configured, reports can be generated using:
```bash
mvn clean test
mvn allure:serve
```

This provides a detailed HTML report including:

## Test execution summary
Passed / failed scenarios
## 🔁 CI/CD Integration
The project can be easily integrated with CI/CD pipelines.
GitHub Actions
Tests can be executed automatically on push or pull request
Sample workflow can be found in .github/workflows

## Jenkins

The project is compatible with Jenkins pipelines
Tests can be triggered using standard Maven commands

## 🧠 Design Principles

This framework follows several best practices:
- Clear separation of test logic and configuration
- Reusable API clients and helpers
- Readable, maintainable test code
- CI/CD-friendly execution
- Easy extensibility for new endpoints

## 🧩 Future Improvements

Possible extensions:
- Contract testing with JSON schema validation
- Environment-specific configuration
- Extended reporting
- Performance-oriented API checks

## 📜 License

This project is provided for educational and portfolio purposes.
Feel free to reuse or adapt the structure in your own projects.
