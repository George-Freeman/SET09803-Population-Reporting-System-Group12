# Use Case 17: Generate Capital City Report for the World

## Goal in Context

As an organisation user, I want to view all capital cities in the world ordered by population from largest to smallest so that I can compare their populations.

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

- Capital city name
- Country name
- Population

## Failed Condition

The system cannot produce the requested report and displays an explanatory message. No database records are changed.

## Primary Actor

Organisation user.

## Trigger

The user requests this report.

## Main Success Scenario

1. The user requests all capital cities in the world.
2. The system retrieves cities identified as capitals by their countries, together with country names and city populations.
3. The system orders the results by population from largest to smallest.
4. The system displays the report and the number of entries returned.

## Extensions

- **2a. The database is unavailable or the query fails:** Display an error message without presenting a partial report as successful.
- **2b. No matching entries exist:** Explain that no data is available for the requested report.

## Sub-variations

- **3a. Populations are equal:** Order by city name, then country name, then city ID to give consistent results.

## Schedule

Use case documentation is required for Code Review 1.
Implementation and verification follow the team's sprint plan.

