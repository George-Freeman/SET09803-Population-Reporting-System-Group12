# Use Case 21: Generate Top N Capital Cities by Continent

## Goal in Context

As an organisation user, I want to view the top N populated capital cities in a selected continent ordered by population from largest to smallest so that I can compare their populations.

## Scope

Population Reporting System.

## Level

User goal.

## Preconditions

- The application is running.
- The world database is available and contains the data needed for the report.

## Success Condition

The system displays up to N matching entries (all matches if fewer than N exist) ordered by population from largest to smallest.

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

1. The user requests the top N capital cities in a selected continent.
2. The system asks for the continent and a positive whole number N.
3. The user supplies the requested values.
4. The system validates the continent and N.
5. The system retrieves cities identified as capitals by their countries within the selected continent, together with country names and city populations.
6. The system orders the results by population from largest to smallest and selects the first N entries.
7. The system displays the report labelled with the selected continent and the number of entries returned.

## Extensions

- **4a. The continent is blank or unrecognised:** Explain the problem and request a valid continent.
- **4b. N is blank, non-numeric, fractional, zero, or negative:** Request a positive whole number.
- **5a. The database is unavailable or the query fails:** Display an error message without presenting a partial report as successful.
- **5b. No matching entries exist:** Explain that no data is available for the requested report.

## Sub-variations

- **6a. Populations are equal:** Order by city name, then country name, then city ID to give consistent results before applying N.
- **6b. Fewer than N matching entries exist:** Display all matching entries and explain that fewer than N are available.

## Schedule

Use case documentation is required for Code Review 1.
Implementation and verification follow the team's sprint plan.

