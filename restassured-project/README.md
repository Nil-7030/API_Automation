# REST Assured API Testing Project

A comprehensive API automation testing framework using REST Assured, TestNG, and Maven for testing the Petstore Swagger API and User management endpoints.

---

## Table of Contents

1. [Project Overview](#project-overview)
2. [Project Structure](#project-structure)
3. [Prerequisites](#prerequisites)
4. [Setup & Installation](#setup--installation)
5. [Configuration](#configuration)
6. [Running Tests](#running-tests)
7. [Test Classes](#test-classes)
8. [API Endpoints](#api-endpoints)
9. [Technologies & Dependencies](#technologies--dependencies)
10. [Best Practices](#best-practices)

---

## Project Overview

This project provides a robust framework for API testing with:
- **Data-driven testing** using CSV files
- **Negative scenario testing** with invalid users
- **POJO-based serialization/deserialization** with Jackson
- **Environment-based configuration** (QA, Prod)
- **User authentication** with token-based login
- **Response validation** using JSON schema
- **Allure reporting** for test results

### Tested API
- **Base URL**: `https://petstore.swagger.io/v2`
- **Authentication**: Token-based Bearer token in headers
- **Endpoints**: User management (CREATE, READ, UPDATE, DELETE)

---

## Project Structure

```
restassured-project/
├── src/
│   ├── main/
│   │   └── java/com/myproject/
│   │       └── pojo/
│   │           └── User.java                 # POJO for User serialization
│   │
│   └── test/
│       ├── java/com/myproject/
│       │   ├── base/                         # Base setup
│       │   │   └── BaseClass.java            # Setup/teardown and common methods
│       │   │
│       │   ├── endpoints/                    # API endpoints
│       │   │   └── Endpoints.java            # Centralized endpoint definitions
│       │   │
│       │   ├── tests/                        # Test classes
│       │   │   ├── DataDrivenTest.java       # Data-driven tests with CSV
│       │   │   ├── DataproviderTest.java     # TestNG DataProvider tests
│       │   │   ├── NegativeScenarioTest.java # Negative & edge case tests
│       │   │   ├── PojoTest.java             # POJO-based serialization tests
│       │   │   ├── PetStoreTest.java         # Pet management CRUD tests
│       │   │   ├── UserLogin.java            # Login & token generation
│       │   │   └── UserOperation.java        # User CRUD operations
│       │   │
│       │   └── utils/                        # Utility classes
│       │       ├── ConfigReader.java         # Config property loader
│       │       ├── CsvReader.java            # CSV data parser
│       │       ├── DataProviderSetup.java    # TestNG data providers
│       │       ├── EnvManager.java           # Environment file manager
│       │       ├── JsonReader.java           # JSON data reader
│       │       ├── ResponseValidator.java    # Response validation helpers
│       │       └── DataProviders.java        # Additional data providers
│       │
│       └── resources/
│           ├── config/                       # Configuration files
│           │   └── qa.properties             # QA environment config
│           │
│           ├── users.csv                     # Test data for users
│           ├── testdata.json                 # JSON test data
│           ├── userResponseSchema.json       # User response JSON schema
│           └── userSchema.json               # User JSON schema
│
├── env/                                      # Environment configuration
│   ├── .env.qa                               # QA environment variables
│   └── .env.prod                             # Production environment variables
│
├── allure-results/                           # Allure test reports
├── target/                                   # Maven build output
├── testng.xml                                # TestNG suite configuration
├── pom.xml                                   # Maven dependencies & plugins
└── README.md                                 # This file
```

---

## Prerequisites

- **Java**: JDK 18 or higher
- **Maven**: 3.8.0 or higher
- **Git**: For version control
- **IDE**: IntelliJ IDEA or Eclipse (optional)

### Verify Installation
```bash
java -version
mvn -version
```

---

## Setup & Installation

### 1. Clone the Repository
```bash
git clone <repository-url>
cd restassured-project
```

### 2. Install Dependencies
```bash
mvn clean install
```

### 3. Verify Structure
```bash
mvn test-compile
```

---

## Configuration

### Environment Files

**Location**: `env/` directory

#### `.env.qa` (QA Environment)
```properties
BASE_URL=https://petstore.swagger.io/v2
USERNAME=tommydog
PASSWORD=Tommy123
status=available
initial.name=pet-one
updated.name=pet-two
updated.status=sold
patched.name=pet-three
patched.status=pending
```

#### `.env.prod` (Production Environment)
```properties
BASE_URL=https://petstore.swagger.io/v2
USERNAME=<prod-username>
PASSWORD=<prod-password>
```

### Properties Configuration
**Location**: `src/test/resources/config/qa.properties`
- Serves as fallback when environment variables are not set
- Used by `ConfigReader` utility

### Switching Environments
```bash
# Run tests in QA environment (default)
mvn clean test

# Run tests in Production environment
mvn clean test -Denv=prod
```

---

## Running Tests

### Run All Tests
```bash
mvn clean test
```

### Run Specific Test Class
```bash
mvn test -Dtest=DataDrivenTest
mvn test -Dtest=NegativeScenarioTest
mvn test -Dtest=PojoTest
mvn test -Dtest=UserOperation
```

### Run Specific Test Method
```bash
mvn test -Dtest=DataDrivenTest#Postlist
mvn test -Dtest=NegativeScenarioTest#Postlist
```

### Run Tests Silently (Minimal Output)
```bash
mvn test -q
```

### Run Tests with Specific Environment
```bash
mvn clean test -Denv=qa
mvn clean test -Denv=prod
```

### Generate Allure Report
```bash
mvn allure:report
# Open: target/site/allure-maven-plugin/index.html
```

---

## Test Classes

### 1. **DataDrivenTest.java**
- **Purpose**: Tests using CSV-driven data
- **Data Source**: `src/test/resources/users.csv`
- **Test Methods**:
  - `Postlist()`: Creates users via CSV data, retrieves, and validates
  - `updateUser()`: Updates and deletes a user

### 2. **DataproviderTest.java**
- **Purpose**: Tests using TestNG `@DataProvider` annotation
- **Data Source**: CSV file with `@DataProvider` annotation
- **Test Methods**: Similar to DataDrivenTest but using DataProvider pattern

### 3. **NegativeScenarioTest.java**
- **Purpose**: Tests negative scenarios and edge cases
- **Data Source**: CSV rows 3-6 with invalid usernames
- **Test Methods**:
  - `Postlist()`: POST 3 valid users, then GET 3 invalid users (expects 404)
  - `updateUser()`: Updates and validates user operations

### 4. **PojoTest.java**
- **Purpose**: Tests POJO serialization/deserialization with Jackson
- **Data Source**: CSV file
- **Test Methods**:
  - `Postlist()`: Creates users using POJO, deserializes GET response to POJO
  - `updateUser()`: Updates user and validates POJO model

### 5. **UserOperation.java**
- **Purpose**: Complete user CRUD operations with JSON data
- **Data Source**: `src/test/resources/testdata.json`
- **Test Methods**: POST, GET, UPDATE, DELETE operations

### 6. **PetStoreTest.java**
- **Purpose**: Pet management CRUD operations
- **Data Source**: Environment configuration
- **Test Methods**: Full CRUD lifecycle for pets

### 7. **UserLogin.java**
- **Purpose**: User authentication and token generation
- **Method**: `userLogin()` - Logs in and extracts bearer token

---

## API Endpoints

| Method | Endpoint | Purpose |
|--------|----------|---------|
| POST | `/user/createWithArray` | Create multiple users |
| GET | `/user/{username}` | Retrieve user by username |
| PUT | `/user/{username}` | Update user details |
| DELETE | `/user/{username}` | Delete user |
| GET | `/user/login` | Login and get token |
| GET | `/pet` | List pets |
| POST | `/pet` | Create pet |
| PUT | `/pet` | Update pet |
| DELETE | `/pet/{petId}` | Delete pet |

---

## Technologies & Dependencies

### Build & Test Frameworks
- **Maven**: 3.15.0
- **TestNG**: 7.10.2 (Test framework)
- **REST Assured**: 4.6.1 (HTTP testing library)

### Serialization & Validation
- **Jackson**: 2.17.0 (JSON serialization)
- **JSON Schema Validator**: 4.6.1

### Reporting & Logging
- **Allure**: 2.13.10 (Test reporting)
- **SLF4J & Logback**: Logging

### Key Dependencies (pom.xml)
```xml
<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>rest-assured</artifactId>
    <version>4.6.1</version>
</dependency>
<dependency>
    <groupId>org.testng</groupId>
    <artifactId>testng</artifactId>
    <version>7.10.2</version>
</dependency>
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.17.0</version>
</dependency>
```

---

## Best Practices

### 1. **Use BaseClass for Setup**
- All test classes inherit from `BaseClass`
- Common setup/teardown handled in `@BeforeMethod`
- Request spec with auth headers and base URI configured automatically

### 2. **Centralize Endpoints**
- Store all API endpoints in `Endpoints.java`
- Avoids hardcoding URLs in tests

### 3. **Data-Driven Testing**
- Use CSV files for test data
- Supports multiple test scenarios per test method
- Easy to add new test cases without code changes

### 4. **Environment Configuration**
- Use `EnvManager` or `ConfigReader` for environment-specific values
- Never hardcode credentials or URLs
- Support multiple environments (QA, Prod)

### 5. **POJO Validation**
- Use Jackson annotations for serialization/deserialization
- Apply `@JsonIgnoreProperties(ignoreUnknown = true)` for flexible response handling
- Validate POJO fields directly in assertions

### 6. **Error Handling**
- Validate HTTP status codes
- Check response body with JSON schema validation
- Include meaningful error messages in assertions

### 7. **Logging & Reporting**
- Use `.log().all()` in test chains for debugging
- Generate Allure reports for detailed test results
- Include assertions with clear failure messages

### 8. **CSV Data Format**
- **Header Row**: Column names matching POJO/Map keys
- **Data Rows**: Actual test data
- **Keys**: Use camelCase (e.g., `firstName`, `lastName`)

---

## Example CSV Data (users.csv)

```csv
id,username,firstName,lastName,email,password,userStatus,expectedStatus,expectedMessage
34,Random123,Random,Test,random123@gamil.com,Random@123,23,200,Valid
35,Test456,Test,Data,test456@gmail.com,Test@456,56,200,Valid
36,New984,New,Dummy,new984@gmail.com,New@984,84,200,Valid
37,113888,Random,Test,random123@gamil.com,Random@123,23,404,Invalid
38,838339,Test,Data,test456@gmail.com,Test@456,56,404,Invalid
39,248844,New,Dummy,new984@gmail.com,New@984,84,404,Invalid
```

---

## Troubleshooting

### Issue: Tests not finding `EnvManager` or `ConfigReader`
**Solution**: Run `mvn clean test-compile` to recompile

### Issue: Base URL is null
**Solution**: Verify `env/.env.qa` exists and `BASE_URL` is set

### Issue: Authentication failures
**Solution**: Check credentials in `.env.qa` match the actual API

### Issue: JSON schema validation fails
**Solution**: Verify schema files exist in `src/test/resources/`

### Issue: CSV data not loading
**Solution**: Ensure CSV file path is correct in test class

---

## Contributing

1. Create a new branch for your feature
2. Add tests for new functionality
3. Update CSV data if needed
4. Run `mvn clean test` before committing
5. Submit a pull request

---

## Contact & Support

For issues or questions, please refer to the project repository or contact the development team.

---

## License

This project is proprietary and confidential.

---

**Last Updated**: May 2026
