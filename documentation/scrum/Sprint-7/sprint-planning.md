# Sprint 7 - Sprint Planning

**Planning date:** 9th November 2026

**Team members:** George Freeman, Rhys John Paterson, Nekoro Spencer, Rohan Phillips and Angela Lynn Wandera.

**Planning meeting attendees:** George Freeman, Rhys John Paterson, Nekoro Spencer, Rohan Phillips and Angela Lynn Wandera.

**Sprint start date:** 9th November 2026

**Sprint end date:** 18th November 2026

## Sprint Goal
Improve the project's quality checks and reporting by adding coverage reporting, bug-reporting support, required badges and a PMD code-quality review.

## Sprint 7 Closure
Sprint 7 was completed on 18th November 2026. All five planned issues (#103, #105, #106, #107 and #140) were completed and merged into `develop`. The sprint goal was achieved.

## Sprint 6 Closure
Sprint 6 was completed, and its planned issues and documentation were merged.

## Product Backlog Items Added to the Sprint Backlog

| Issue | Description | Owner |
|-------|-------------|-------|
| #103 | Add JaCoCo and Codecov | Rohan Phillips |
| #105 | Set up bug reporting | George Freeman |
| #106 | Add the required badges | Nekoro Spencer |
| #107 | Review code using PMD | Angela Lynn Wandera |
| #140 | Update README with final project documentation | Rhys John Paterson |


## Sprint Backlog
Sprint 7 focuses on test coverage, repository reporting and Java code quality.

Confirmed assignments:
- #103: Rohan Phillips.
- #105: George Freeman.
- #106: Nekoro Spencer.
- #107: Angela Lynn Wandera.
- #140: Rhys John Paterson.

## Planning Poker
Each participating team member estimated every selected issue using the agreed planning-poker scale. The Estimated Cost is the average of the five submitted scores.

| Issue | Description | George | Rhys | Nekoro | Rohan | Angela | Estimated Cost |
|-------|-------------|-------:|-----:|-------:|------:|-------:|---------------:|
| #103 | Add JaCoCo and Codecov | 3 | 4 | 3 | 4 | 5 | 3.80 |
| #105 | Set up bug reporting | 2 | 4 | 2 | 2 | 1 | 2.20 |
| #106 | Add the required badges | 2 | 2 | 1 | 2 | 2 | 1.80 |
| #107 | Review code using PMD | 4 | 2 | 3 | 3 | 3 | 3.00 |
| #140 | Update README with final project documentation | 3 | 3 | 4 | 3 | 3 | 3.20 |

**Total estimated cost:** 14.00 points.

### Estimate Reasoning
- **#103:** This needed Maven and GitHub Actions changes, Codecov setup, a README badge, and checks to make sure coverage was being reported correctly.
- **#105:** The bug-reporting setup was fairly straightforward, but the template and the link between bugs, fixes and pull requests still needed to be checked.
- **#106:** Adding badges was a small README change, but every badge had to point to the correct branch, workflow, release, coverage report or licence.
- **#107:** PMD had to scan the whole project, useful warnings had to be selected and fixed carefully, and the tests and build had to be checked again afterward.
- **#140:** The work is mainly documentation, but it covers the whole project. The requirements, screenshots, build instructions, tests, coverage, CI, bug reporting, Scrum process and licence all need to be checked and explained clearly.
