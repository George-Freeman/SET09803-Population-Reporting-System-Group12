# Sprint 5 - Sprint Planning

**Date:** 26th October 2026

**Attendees:** George Freeman, Rhys John Paterson, Nekoro Spencer, Angela Lynn Wandera and Rohan Phillips.

**Sprint start date:** 26th October 2026

**Sprint end date:** 30th October 2026

## Sprint Goal
Complete the remaining population reports and improve the application's unit tests, integration testing and database configuration.

## Sprint 4 Issues Carried Over
No carry-over items have been identified because the Sprint 4 backlog was completed.

| Issue | Description |
|-------|-------------|
| None currently | All Sprint 4 work was reviewed, merged and completed |

## Product Backlog Items Added to the Sprint Backlog
The following seven items have been selected for Sprint 5. All owners have been confirmed by the team.

| Issue | Description | Owner |
|-------|-------------|-------|
| #44 | Get country population | Angela Lynn Wandera |
| #45 | Get district population | Angela Lynn Wandera |
| #46 | Get city population | Angela Lynn Wandera |
| #47 | Generate language population report | Rohan Phillips |
| #98 | Improve unit test quality | Nekoro Spencer |
| #100 | Make the database connection configurable | Rhys John Paterson |
| #101 | Add integration tests | George Freeman |

## Sprint Backlog
The Sprint 5 backlog combines the remaining population requirements with technical improvements to unit-test quality, integration testing and configurable database connections.

Confirmed assignments:
- #44: Angela Lynn Wandera.
- #45: Angela Lynn Wandera.
- #46: Angela Lynn Wandera.
- #47: Rohan Phillips.
- #98: Nekoro Spencer.
- #100: Rhys John Paterson.
- #101: George Freeman.

### Backlog Correction
Issue #99 was originally selected because it was still listed in the product backlog. A review of the develop branch confirmed that the model encapsulation work had already been completed and merged through PR #81 before Sprint 5 began. Issue #99 has therefore been removed from Sprint 5 and replaced with #101.

## Planning Poker
All five members submitted planning-poker scores. The Estimated Cost is the average of the five scores, rounded to one decimal place.

| Issue | Description | George | Rhys | Nekoro | Angela | Rohan | Estimated Cost |
|-------|-------------|-------:|-----:|-------:|-------:|------:|---------------:|
| #44 | Get country population | 1 | 1 | 2 | 2 | 1 | 1.2 |
| #45 | Get district population | 1 | 2 | 2 | 2 | 2 | 1.8 |
| #46 | Get city population | 1 | 1 | 1 | 2 | 2 | 1.4 |
| #47 | Generate language population report | 4 | 4 | 4 | 4 | 5 | 4.2 |
| #98 | Improve unit test quality | 3 | 3 | 3 | 3 | 4 | 3.2 |
| #100 | Make the database connection configurable | 2 | 2 | 3 | 3 | 4 | 2.8 |
| #101 | Add integration tests | 4 | 3 | 4 | 4 | 4 | 3.8 |

**Total estimated cost:** 18.4 points.

### Estimate Reasoning

| Issue | Estimated Cost | Reason |
|-------|---------------:|--------|
| #44 | 1.2 | Most members considered this a simple country lookup with one input and one population value to return. |
| #45 | 1.8 | This is still fairly simple, but the population of a district must be calculated from the cities within it. |
| #46 | 1.4 | This is a straightforward city lookup, with some input checking required. |
| #47 | 4.2 | This report needs language totals, world percentages, sorting and several calculations to be tested. |
| #98 | 3.2 | The existing tests must be reviewed so missing normal, null, empty and error cases can be added. |
| #100 | 2.8 | The database connection must be changed to work correctly both locally and in Docker without breaking existing features. |
| #101 | 3.8 | Integration tests must connect to the real Docker database and remain separate from the normal unit tests. |

Rohan Phillips prepared the documentation as Scrum Master. All Sprint 5 issues now have confirmed owners.
