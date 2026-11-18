# SET09803-Population-Reporting-System

![Commit activity](https://img.shields.io/github/commit-activity/m/George-Freeman/SET09803-Population-Reporting-System-Group12)
[![Build master](https://img.shields.io/github/actions/workflow/status/George-Freeman/SET09803-Population-Reporting-System-Group12/main.yml?branch=master&label=Build%20(master))](https://github.com/George-Freeman/SET09803-Population-Reporting-System-Group12/actions/workflows/main.yml?query=branch%3Amaster)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](Licence.md)
[![Release](https://img.shields.io/github/v/release/George-Freeman/SET09803-Population-Reporting-System-Group12)](https://github.com/George-Freeman/SET09803-Population-Reporting-System-Group12/releases)
[![Develop build](https://github.com/George-Freeman/SET09803-Population-Reporting-System-Group12/actions/workflows/main.yml/badge.svg?branch=develop)](https://github.com/George-Freeman/SET09803-Population-Reporting-System-Group12/actions/workflows/main.yml?query=branch%3Adevelop)
[![Codecov](https://codecov.io/gh/George-Freeman/SET09803-Population-Reporting-System-Group12/graph/badge.svg?branch=master)](https://codecov.io/gh/George-Freeman/SET09803-Population-Reporting-System-Group12)

## About
The aim of the project is to develop a population reporting system using Java, Maven, MySQL and Docker. The system uses the MySQL World database to produce 32 population reports for countries, cities, capital cities, and spoken languages.

## Team

| Role | Name |
| :--- | :--- |
| Product Owner | George Freeman |
| Scrum Master | Rohan Phillips |
| Developer | Rhys John Paterson |
| Developer | Nekoro Spencer |
| Developer | Angela Lynn Wandera |

---
## Technologies
- Java 17
- Maven
- MySQL
- Docker & Docker Compose
- JUnit 5, Mockito & JaCoCo
- Codecov
- Git & GitHub Actions
- IntelliJ IDEA

---
## Requirements Met
All 32 requirements have been implemented, merged, and verified.

| ID | Name | Met | Screenshot |
| :--- | :--- | :---: | :---: |
| 1 | All the countries in the world organised by largest population to smallest. | Yes | <img src="https://github.com/user-attachments/assets/22ecf90c-b0cf-48ef-aec8-391da2bac60a" width="300" alt="Requirement 1 Screenshot" /> |
| 2 | All the countries in a continent organised by largest population to smallest. | Yes | <img width="300" alt="image" src="https://github.com/user-attachments/assets/5654d48b-ba5e-4185-8d43-fc9c5dbdb4ff" /> |
| 3 | All the countries in a region organised by largest population to smallest. | Yes | <img width="300" alt="Image" src="https://github.com/user-attachments/assets/aeaf9809-0ee3-44e2-b260-383085ed082a" /> |
| 4 | The top N populated countries in the world where N is provided by the user. | Yes | <img width="300" alt="image" src="https://github.com/user-attachments/assets/226d1d1e-a00e-49b4-8dd6-d7b357bfc1ac" /> |
| 5 | The top N populated countries in a continent where N is provided by the user. | Yes | <img width="300" alt="Image" src="https://github.com/user-attachments/assets/2e3b962d-21ac-4f7b-b7c8-9514030e2cca" /> |
| 6 | The top N populated countries in a region where N is provided by the user. | Yes | <img width="300" alt="image" src="https://github.com/user-attachments/assets/82e5417e-3f76-4a7c-81d3-037fe1393700" /> |
| 7 | All the cities in the world organised by largest population to smallest. | Yes | <img width="300" alt="Image" src="https://github.com/user-attachments/assets/ff7c5801-7682-441e-a11c-88a6eba9c7fb" /> |
| 8 | All the cities in a continent organised by largest population to smallest. | Yes | <img width="300" alt="Image" src="https://github.com/user-attachments/assets/ffdf1052-b7af-4f9a-8599-95ee9960085c" /> |
| 9 | All the cities in a region organised by largest population to smallest. | Yes | <img width="300" alt="Image" src="https://github.com/user-attachments/assets/4a8816d3-e061-4b1c-81cf-1f92a8a22290" /> |
| 10 | All the cities in a country organised by largest population to smallest. | Yes | <img width="300" alt="Image" src="https://github.com/user-attachments/assets/752218f7-e7f5-4699-b252-82551630c8b8" /> |
| 11 | All the cities in a district organised by largest population to smallest. | Yes | <img width="300" alt="Image" src="https://github.com/user-attachments/assets/014f0282-bfc3-4fe9-82c1-6b1406502503" /> |
| 12 | The top N populated cities in the world where N is provided by the user. | Yes | <img width="300" alt="Image" src="https://github.com/user-attachments/assets/9bbcb9da-49ab-4cf0-a946-6689b835cc58" /> |
| 13 | The top N populated cities in a continent where N is provided by the user. | Yes | <img width="300" alt="Image" src="https://github.com/user-attachments/assets/f6281bd8-c5a3-4c9a-bab2-8f4e7df91547" /> |
| 14 | The top N populated cities in a region where N is provided by the user. | Yes | <img width="300" alt="Image" src="https://github.com/user-attachments/assets/232d10d3-0ec1-431c-ab93-4a109eba3a2b" /> |
| 15 | The top N populated cities in a country where N is provided by the user. | Yes | <img width="300" alt="Image" src="https://github.com/user-attachments/assets/5840bc62-63f3-4024-86de-724b74510a40" /> |
| 16 | The top N populated cities in a district where N is provided by the user. | Yes | <img width="300" alt="Image" src="https://github.com/user-attachments/assets/acac6851-6e23-4c97-8a41-da0113e9d5cc" /> |
| 17 | All the capital cities in the world organised by largest population to smallest. | Yes | <img width="300" alt="Requirement 17 capital cities world report" src="https://github.com/user-attachments/assets/acf8dd33-6b72-4d5c-8507-398f37e694d2" /> |
| 18 | All the capital cities in a continent organised by largest population to smallest. | Yes | <img width="300" alt="Image" src="https://github.com/user-attachments/assets/1fe2934c-7b91-4f32-9644-6020cd5db37e" /> |
| 19 | All the capital cities in a region organised by largest to smallest. | Yes | <img width="300" alt="Image" src="https://github.com/user-attachments/assets/d9cc9eb5-202b-4976-bfd4-e5d62d17eae3" /> |
| 20 | The top N populated capital cities in the world where N is provided by the user. | Yes | <img width="300" alt="image" src="https://github.com/user-attachments/assets/90dffac6-5f6f-429c-9589-a79502887f85" /> |
| 21 | The top N populated capital cities in a continent where N is provided by the user. | Yes | <img width="300" alt="image" src="https://github.com/user-attachments/assets/b2d520fb-51af-40b0-aa35-bddd88917104" /> |
| 22 | The top N populated capital cities in a region where N is provided by the user. | Yes | <img width="300" alt="Screenshot 2026-10-22 at 12 12 17" src="https://github.com/user-attachments/assets/735db6eb-9098-4a7d-86d7-74f24d3bc18a" /> |
| 23 | The population of people, people living in cities, and people not living in cities in each continent. | Yes | <img width="300" alt="image" src="https://github.com/user-attachments/assets/495fd585-a1bc-47a4-9dc9-256ded449ea1" /> |
| 24 | The population of people, people living in cities, and people not living in cities in each region. | Yes | <img width="300" alt="Screenshot 2026-10-22 at 10 35 59 AM" src="https://github.com/user-attachments/assets/41a4aeac-69dd-4aaf-b7d4-8a909ea7ac59" /> |
| 25 | The population of people, people living in cities, and people not living in cities in each country. | Yes | <img width="300" alt="issue-40-country-report-clean" src="https://github.com/user-attachments/assets/1ced695b-d23e-4c0b-b510-4565a6b09092" /> |
| 26 | The population of the world. | Yes | <img width="300" alt="image" src="https://github.com/user-attachments/assets/c56c2c9d-50e4-4ea2-889e-c18f229c53d6" /> |
| 27 | The population of a continent. | Yes | <img width="300" alt="Image" src="https://github.com/user-attachments/assets/db0ecc4f-8521-4096-81a5-3e25ea868123" /> |
| 28 | The population of a region. | Yes | <img width="300" alt="Screenshot 2026-10-22 at 3 20 01 PM" src="https://github.com/user-attachments/assets/f02c62e9-3a26-40a8-bd6e-fe7059138d78" /> |
| 29 | The population of a country. | Yes | <img width="300" alt="Image" src="https://github.com/user-attachments/assets/79e33781-831a-4280-be40-6936b9480175" /> |
| 30 | The population of a district. | Yes | <img width="300" alt="Image" src="https://github.com/user-attachments/assets/bfe4e7a9-dede-4e8f-a99d-caad05008051" /> |
| 31 | The population of a city. | Yes | <img width="300" alt="Image" src="https://github.com/user-attachments/assets/abfbd198-5ebd-4938-8503-1ee46dc40b23" /> |
| 32 | The number of people who speak Chinese, English, Hindi, Spanish, and Arabic from greatest to smallest, including world population percentage. | Yes | <img width="300" alt="Image" src="https://github.com/user-attachments/assets/722ef515-5ea7-498e-8c20-487ea72375f2" /> |

---

## Building and Running

Clone the repository:

```bash
git clone https://github.com/George-Freeman/SET09803-Population-Reporting-System-Group12.git
cd SET09803-Population-Reporting-System-Group12
```

Build the application JAR using Maven:

```bash
mvn clean package -DskipTests
```

Start the database and application containers with Docker Compose:

```bash
docker compose up --build
```

Stop the application and remove containers and volumes:

```bash
docker compose down --volumes --remove-orphans
```

---

## Testing and Code Coverage

Run the unit test suite using Maven:

```bash
mvn clean test
```

Run the integration tests (requires the MySQL `world` database container running on port `33060`):



```bash
docker build -t database ./db
docker run --name world-db -d -p 33060:3306 -e MYSQL_ROOT_PASSWORD=example database
mvn -Dtest=com.napier.population.AppIntegrationTest test
docker stop world-db 
docker rm world-db
```

Open `target/site/jacoco/index.html` in a web browser to view the coverage breakdown.

---
### JaCoCo & Codecov
- **JaCoCo** is configured in `pom.xml` to measure line and branch code coverage during test execution.
- **Codecov** ingests the generated JaCoCo XML coverage report during CI builds to track coverage metrics across pull requests and branches.

To generate and view the local code coverage report:

```bash
mvn clean test jacoco:report
```

---

## Continuous Integration

The project uses GitHub Actions (`.github/workflows/main.yml`) to automate builds, testing, and releases.

When the workflow runs:
- On every `push` to any branch.
- On every `pull_request` targeting `develop`, `master`, or `release`.

What the pipeline checks:
1. **Unit Tests (`UnitTests`):** Compiles the project on JDK 17 and runs all unit test suites.
2. **Integration Tests (`IntegrationTests`):** Builds the MySQL `world` database image, starts the container, runs database integration tests, and cleans up resources.
3. **Build & Release (`build`):** Runs once both test jobs pass. Packages the application JAR, validates `docker-compose.yml`, builds and runs the application container to verify startup, and automatically publishes a tagged GitHub Release with the compiled JAR when merged into `master`.

---

## Bug Reporting

Bugs are reported using the Bug Report form under GitHub Issues (`#105`).

When fixing a reported bug:
1. Create a `bugfix/` branch from `develop` for the fix.
2. Reference the bug issue in the pull request description using `Fixes #<issue-number>` or `Closes #<issue-number>`.
3. Review and merge the pull request into `develop` once CI checks pass and the fix has been tested.
4. GitHub automatically links the pull request and closes the bug issue upon merging.

---

## Scrum

The team uses **GitHub Projects** to manage the product backlog, plan sprints, and track task progress from *To Do* through *In Progress*, *In Review*, and *Done*.

- **Scrum Board:** [Group 12 GitHub Project Board](https://github.com/users/George-Freeman/projects/1)

---

## Licence

This project is licensed under the Apache License 2.0 - see the [Licence.md](https://github.com/George-Freeman/SET09803-Population-Reporting-System-Group12?tab=License-1-ov-file) file for details.