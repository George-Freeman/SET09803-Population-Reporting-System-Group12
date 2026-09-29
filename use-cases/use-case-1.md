# Use Case 1: Generate Country Report for the World

## Goal in Context
As an organisation user, I want to view all countries in the world
ordered by population from largest to smallest so that I can compare
country populations.

## Scope
Population Reporting System.

## Level
User goal.

## Preconditions
- The application is running.
- The world database is available and contains country and city data.

## Success Condition
The system displays all countries ordered by population from largest
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
The user requests the world country population report.

## Main Success Scenario
1. The user requests a report of all countries in the world.
2. The system retrieves each country's code, name, continent, region,
   population, and capital city name from the world database.
3. The system orders the countries by population from largest to smallest.
4. The system displays the report to the user.

## Extensions
- **2a. The database is unavailable or the query fails:**
  The system displays an error message and does not present an
  incomplete report as successful.
- **2b. No countries are returned:**
  The system informs the user that no country data is available.

## Sub-variations
- **2. A country has no recorded capital:**
  Include the country in the report and display "N/A" for its capital.
- **3. Countries have equal populations:**
  Order those countries alphabetically by country name.

## Schedule
Use case documentation is required for Code Review 1.
Implementation and verification follow the team's sprint plan.