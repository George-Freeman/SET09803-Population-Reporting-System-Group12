# Use Case 6: Generate Top N Countries by Region

## Goal in Context

As an organisation user, I want to view the top N populated countries in a selected region ordered by population from largest to smallest so that I can compare their populations.

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

- Country code
- Country name
- Continent
- Region
- Population
- Capital city name

## Failed Condition

The system cannot produce the requested report and displays an explanatory message. No database records are changed.

## Primary Actor

Organisation user.

## Trigger

The user requests this report.

## Main Success Scenario

1. The user requests the top N countries in a selected region.
2. The system asks for the region and a positive whole number N.
3. The user supplies the requested values.
4. The system validates the region and N.
5. The system retrieves countries within the selected region, including capital city names.
6. The system orders the results by population from largest to smallest and selects the first N entries.
7. The system displays the report labelled with the selected region and the number of entries returned.

## Extensions

- **4a. The region is blank or unrecognised:** Explain the problem and request a valid region.
- **4b. N is blank, non-numeric, fractional, zero, or negative:** Request a positive whole number.
- **5a. The database is unavailable or the query fails:** Display an error message without presenting a partial report as successful.
- **5b. No matching entries exist:** Explain that no data is available for the requested report.

## Sub-variations

- **5. A country has no recorded capital:** Include the country and display "N/A" for its capital.
- **6a. Populations are equal:** Order by country name, then country code to give consistent results before applying N.
- **6b. Fewer than N matching entries exist:** Display all matching entries and explain that fewer than N are available.

## Schedule

Use case documentation is required for Code Review 1.
Implementation and verification follow the team's sprint plan.

