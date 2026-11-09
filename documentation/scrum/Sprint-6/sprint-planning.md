# Sprint 6 - Sprint Planning

**Planning date:** 30th October 2026

**Team members:** George Freeman, Rhys John Paterson, Nekoro Spencer and Rohan Phillips.

**Planning meeting attendees:** George Freeman, Rhys John Paterson, Nekoro Spencer and Rohan Phillips.

**Sprint start date:** 30th October 2026

**Sprint end date:** 9th November 2026

## Sprint Goal
Improve the delivery pipeline and code quality while completing the remaining capital-city reports.

## Sprint 5 Issues Carried Over
No carry-over items have been identified. All seven Sprint 5 issues are closed.

| Issue | Description |
|-------|-------------|
| None currently | All Sprint 5 issues were completed and closed |

## Product Backlog Items Added to the Sprint Backlog
The following five issues have been selected for Sprint 6, and all owners are confirmed.

| Issue | Description | Owner |
|-------|-------------|-------|
| #102 | Update the GitHub Actions workflow | George Freeman |
| #104 | Automate GitHub Releases | Rhys John Paterson |
| #121 | All capital cities in the world | Rohan Phillips |
| #122 | All capital cities in a continent | Nekoro Spencer |
| #123 | All capital cities in a region | Rhys John Paterson |

## Sprint Backlog
Sprint 6 combines delivery-pipeline improvements with the three remaining capital-city reports.

Confirmed assignments:
- #102: George Freeman.
- #104: Rhys John Paterson.
- #121: Rohan Phillips.
- #122: Nekoro Spencer.
- #123: Rhys John Paterson.

## Planning Poker
Each participating team member estimated every selected issue using the agreed planning-poker scale. The Estimated Cost is the average of the four submitted scores.

| Issue | Description | George | Rhys | Nekoro | Rohan | Estimated Cost |
|-------|-------------|-------:|-----:|-------:|------:|---------------:|
| #102 | Update the GitHub Actions workflow | 4 | 3 | 3 | 3 | 3.25 |
| #104 | Automate GitHub Releases | 4 | 5 | 3 | 4 | 4.00 |
| #121 | All capital cities in the world | 2 | 2 | 2 | 2 | 2.00 |
| #122 | All capital cities in a continent | 3 | 3 | 2 | 3 | 2.75 |
| #123 | All capital cities in a region | 3 | 3 | 3 | 3 | 3.00 |

**Total estimated cost:** 15.00 points.

### Estimate Reasoning

| Issue | Estimated Cost | Reason |
|-------|---------------:|--------|
| #102 | 3.25 | The workflow had to separate unit tests, integration tests and the build while keeping the existing Docker checks working in the correct order. |
| #104 | 4.00 | This required release permissions, a successful master build and uploading the packaged JAR automatically without creating releases from feature branches. |
| #121 | 2.00 | This was a straightforward report using the existing capital-city model, with one query to retrieve and order every world capital by population. |
| #122 | 2.75 | This added continent input checking and filtering to the capital-city report while keeping the results ordered correctly. |
| #123 | 3.00 | This required region input handling, database filtering and checks that the correct capital cities were returned in population order. |
