# Use Case 25: Generate Population Breakdown by Country

## Goal in Context

As an organisation user, I want to compare total population, population living in cities, and population not living in cities for each country so that I can understand population distribution.

## Scope

Population Reporting System.

## Level

User goal.

## Preconditions

- The application is running.
- The world database is available and contains the data needed for the report.

## Success Condition

The report includes every country represented in the country data and displays:

- Country name
- Total population
- Population living in cities and its percentage of the total
- Population not living in cities and its percentage of the total

## Failed Condition

The system cannot produce the requested report and displays an explanatory message. No database records are changed.

## Primary Actor

Organisation user.

## Trigger

The user requests this report.

## Main Success Scenario

1. The user requests a population breakdown for each country.
2. The system retrieves country populations and recorded city populations grouped by country.
3. The system totals country populations once per country and sums recorded city populations separately, avoiding duplicate counting.
4. The system calculates population outside recorded cities as total population minus population in recorded cities.
5. The system calculates each share as its population divided by total population, multiplied by 100.
6. The system displays the breakdown for each country, with clearly labelled counts and percentages.

## Extensions

- **2a. Data retrieval fails:** Display an error message without presenting a partial report as successful.
- **2b. No country data exists:** Inform the user that no population data is available.
- **4. Recorded city population exceeds total population:** Identify the affected entry as inconsistent instead of reporting a negative outside-city population.
- **5. Total population is zero:** Display both percentages as "N/A" because division by zero is undefined.

## Sub-variations

- **3. A country has no recorded cities:** Use zero for its recorded city population while retaining its country population.
- **6. Percentage presentation:** Round percentages to two decimal places for display; calculate using unrounded values.
- **6. Data interpretation:** Label the breakdown as based on cities recorded in the supplied world database; it is not a complete contemporary urban/rural census.

## Schedule

Use case documentation is required for Code Review 1.
Implementation and verification follow the team's sprint plan.

