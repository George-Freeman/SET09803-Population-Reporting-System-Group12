# Use Case 2: Generate Country Report by Continent

## Goal in Context
As an organisation user, I want to view countries in a selected
continent ordered by population from largest to smallest so that
I can compare country populations within that continent.

## Scope
Population Reporting System.

## Level
User goal.

## Preconditions
- The application is running.
- The world database is available and contains country and city data.

## Success Condition
The system displays all countries in the selected continent,
ordered by population from largest to smallest. Each row includes:
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
The user requests a country population report for a continent.

## Main Success Scenario
1. The user requests the country report by continent.
2. The system asks the user to specify a continent.
3. The user supplies a continent.
4. The system validates the continent.
5. The system retrieves the countries belonging to that continent,
   including their capital city names.
6. The system orders the countries by population from largest to smallest.
7. The system displays the selected continent and the report.

## Extensions
- **4a. The continent is blank or unrecognised:**
  The system explains the problem and asks for a valid continent.
- **5a. The database is unavailable or the query fails:**
  The system displays an error message and does not present an
  incomplete report as successful.
- **5b. No countries are returned:**
  The system informs the user that no country data is available
  for the selected continent.

## Sub-variations
- **5. A country has no recorded capital:**
  Include the country in the report and display "N/A" for its capital.
- **6. Countries have equal populations:**
  Order those countries alphabetically by country name.

## Schedule
Use case documentation is required for Code Review 1.
Implementation and verification follow the team's sprint plan.