# Use Case 9: Generate City Report by Region

## Goal in Context

As an organisation user, I want to view all cities in a selected region ordered by population from largest to smallest so that I can compare their populations.

## Scope

Population Reporting System.

## Level

User goal.

## Preconditions

- The application is running.
- The world database is available and contains the data needed for the report.

## Success Condition

The system displays all matching entries ordered by population from largest to smallest.

Each row includes:

- City name
- Country name
- District
- Population

## Failed Condition

The system cannot produce the requested report and displays an explanatory message. No database records are changed.

## Primary Actor

Organisation user.

## Trigger

The user requests this report.

## Main Success Scenario

1. The user requests all cities in a selected region.
2. The system asks for the region.
3. The user supplies the requested values.
4. The system validates the region.
5. The system retrieves cities within the selected region, including country names.
6. The system orders the results by population from largest to smallest.
7. The system displays the report labelled with the selected region and the number of entries returned.

## Extensions

- **4a. The region is blank or unrecognised:** Explain the problem and request a valid region.
- **5a. The database is unavailable or the query fails:** Display an error message without presenting a partial report as successful.
- **5b. No matching entries exist:** Explain that no data is available for the requested report.

## Sub-variations

- **6a. Populations are equal:** Order by city name, then country name, then city ID to give consistent results.

## Schedule

Use case documentation is required for Code Review 1.
Implementation and verification follow the team's sprint plan.

