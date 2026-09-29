# Use Case 4: Generate Top N Countries in the World

## Goal in Context
As an organisation user, I want to view the top N populated countries
in the world so that I can compare the countries with the largest
populations.

## Scope
Population Reporting System.

## Level
User goal.

## Preconditions
- The application is running.
- The world database is available and contains country and city data.

## Success Condition
The system displays the requested number of countries, or all available
countries if fewer than N exist, ordered by population from largest
to smallest. Each row includes:
- Country code
- Country name
- Continent
- Region
- Population
- Capital city name

## Failed Condition
The system cannot produce the report and displays an explanatory
message. No database records are changed.

## Primary Actor
Organisation user.

## Trigger
The user requests a report of the top N populated countries in the world.

## Main Success Scenario
1. The user requests the top N countries report for the world.
2. The system asks how many countries to include.
3. The user supplies N.
4. The system validates that N is a positive whole number.
5. The system retrieves country information, including capital city names.
6. The system orders the countries by population from largest to smallest
   and selects the first N countries.
7. The system displays the report and the number of countries returned.

## Extensions
- **4a. N is blank, non-numeric, fractional, zero, or negative:**
  The system explains that a positive whole number is required
  and asks the user to enter N again.
- **5a. The database is unavailable or the query fails:**
  The system displays an error message and does not present an
  incomplete report as successful.
- **5b. No countries are returned:**
  The system informs the user that no country data is available.

## Sub-variations
- **5. A country has no recorded capital:**
  Include the country in the report and display "N/A" for its capital.
- **6a. Countries have equal populations:**
  Order those countries alphabetically by country name before
  selecting the first N countries.
- **6b. N exceeds the number of available countries:**
  Display all available countries and explain that fewer than N exist.

## Schedule
Use case documentation is required for Code Review 1.
Implementation and verification follow the team's sprint plan.