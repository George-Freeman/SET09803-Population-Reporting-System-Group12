# Sprint 7 - Sprint Planning

**Planning date:** 9th November 2026

**Team members:** George Freeman, Rhys John Paterson, Nekoro Spencer and Rohan Phillips.

**Planning meeting attendees:** Pending confirmation

**Sprint start date:** Pending team confirmation

**Sprint end date:** Pending team confirmation

## Draft Sprint Goal
Improve the project's quality checks and reporting by adding coverage reporting, bug-reporting support, required badges and a PMD code-quality review.

The team must review and agree on the final sprint goal before the planning document is finalised.

## Sprint 6 Closure
All five Sprint 6 issues are closed. The remaining closure actions are to finalise and merge the Sprint 6 planning documentation and verify the automatic GitHub release on `master`.

## Product Backlog Items Added to the Sprint Backlog

| Issue | Description | Owner |
|-------|-------------|-------|
| #103 | Add JaCoCo and Codecov | Pending |
| #105 | Set up bug reporting | Pending |
| #106 | Add the required badges | Pending |
| #107 | Review code using PMD | Pending |

## Sprint Backlog
Sprint 7 focuses on test coverage, repository reporting and Java code quality.

Assignments are pending team confirmation.

## Planning Poker
Each participating team member estimated every selected issue using the agreed planning-poker scale. The Estimated Cost is the average of the four submitted scores.

| Issue | Description | George | Rhys | Nekoro | Rohan | Estimated Cost |
|-------|-------------|-------:|-----:|-------:|------:|---------------:|
| #103 | Add JaCoCo and Codecov | 3 | 4 | 3 | 4 | 3.50 |
| #105 | Set up bug reporting | 2 | 4 | 2 | 2 | 2.50 |
| #106 | Add the required badges | 2 | 2 | 1 | 2 | 1.75 |
| #107 | Review code using PMD | 4 | 2 | 3 | 3 | 3.00 |

**Total estimated cost:** 10.75 points.

### Rohan's Estimates
- #103: 4 - This includes Maven configuration, GitHub Actions, an external Codecov setup, the README badge and checking the coverage results.
- #105: 2 - This mainly requires a clear GitHub issue template and checking that it appears correctly when a new bug is reported.
- #106: 2 - Adding badges is straightforward, but every badge link must be checked and the coverage badge depends on #103.
- #107: 3 - PMD must be run across the project, useful warnings need to be reviewed and fixed carefully, and the full test suite must still pass.

### Estimate Reasoning
The final shared reasons will be added after the team agrees on the estimated costs.

## Planning Actions Still Required
- Confirm the Sprint 7 start and end dates.
- Confirm the planning meeting attendees.
- Agree on the final sprint goal.
- Assign an owner to each issue.
- Agree on the final estimates and record the shared reasons.
- Complete the remaining Sprint 6 closure actions.
