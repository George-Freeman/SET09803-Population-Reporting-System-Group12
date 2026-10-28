# Sprint 5 - Sprint Planning

**Date:** 26 October 2026

**Attendees:** George Freeman, Rhys John Paterson, Nekoro Spencer, Angela Lynn Wandera and Rohan Phillips.

**Sprint start date:** 26 October 2026

**Sprint end date:** Pending team confirmation

## Sprint Goal
Complete the remaining population reports and improve the application's unit tests, integration testing and database configuration.

## Sprint 4 Issues Carried Over
No carry-over items have been identified because the Sprint 4 backlog was completed.

| Issue | Description |
|-------|-------------|
| None currently | All Sprint 4 work was reviewed, merged and completed |

## Product Backlog Items Added to the Sprint Backlog
The following seven items have been selected for Sprint 5. Five owners are confirmed and the remaining owners are pending team agreement.

| Issue | Description | Owner |
|-------|-------------|-------|
| #44 | Get country population | Angela Lynn Wandera |
| #45 | Get district population | Angela Lynn Wandera |
| #46 | Get city population | Angela Lynn Wandera |
| #47 | Generate language population report | Rohan Phillips |
| #98 | Improve unit test quality | Nekoro Spencer |
| #100 | Make the database connection configurable | Rhys John Paterson |
| #101 | Add integration tests | To be assigned |

## Sprint Backlog
The Sprint 5 backlog combines the remaining population requirements with technical improvements to unit-test quality, integration testing and configurable database connections.

Confirmed assignments:
- #44: Angela Lynn Wandera.
- #45: Angela Lynn Wandera.
- #46: Angela Lynn Wandera.
- #47: Rohan Phillips.
- #98: Nekoro Spencer.
- #100: Rhys John Paterson.

Assignments still to be confirmed:
- #101: Add integration tests.

### Backlog Correction
Issue #99 was originally selected because it was still listed in the product backlog. A review of the develop branch confirmed that the model encapsulation work had already been completed and merged through PR #81 before Sprint 5 began. Issue #99 has therefore been removed from Sprint 5 and replaced with #101.

## Planning Poker
All five members submitted their planning-poker scores. The Estimated Cost uses the most common score. Where the votes were tied, the middle score was used.

| Issue | Description | George | Rhys | Nekoro | Angela | Rohan | Estimated Cost |
|-------|-------------|-------:|-----:|-------:|-------:|------:|---------------:|
| #44 | Get country population | 1 | 1 | 2 | 2 | 1 | 1 |
| #45 | Get district population | 1 | 2 | 2 | 2 | 2 | 2 |
| #46 | Get city population | 1 | 1 | 1 | 2 | 2 | 1 |
| #47 | Generate language population report | 4 | 4 | 4 | 4 | 5 | 4 |
| #98 | Improve unit test quality | 3 | 3 | 3 | 3 | 4 | 3 |
| #100 | Make the database connection configurable | 2 | 2 | 3 | 3 | 4 | 3 |
| #101 | Add integration tests | 4 | 3 | 4 | 4 | 4 | 4 |

**Total estimated cost:** 18 points.

### Estimate Reasoning

| Issue | Estimated Cost | Reason |
|-------|---------------:|--------|
| #44 | 1 | A straightforward lookup for one selected country, with one input and one population value to return. |
| #45 | 2 | The district population must be calculated by adding the populations of its cities, so it needs a little more query and validation work. |
| #46 | 1 | A direct lookup for one selected city, with simple input checking and one value to display. |
| #47 | 4 | It calculates totals and world percentages for five languages, orders the results and needs several calculations to be tested. |
| #98 | 3 | The team must review the existing tests, find important gaps and add useful normal, null, empty and error cases. |
| #100 | 3 | The connection code must be refactored so its location and delay work both locally and in Docker without breaking current behaviour. |
| #101 | 4 | These tests must use the real Docker database, manage setup and connection details, and remain separate from the normal unit tests. |

Rohan Phillips is preparing the documentation as Scrum Master. The remaining task owner will be added when confirmed by the team.
