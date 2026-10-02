# Use Case 27: Get Continent Population

## Goal in Context

As an organisation user, I want to view the population of a selected continent so that I can understand its population size.

## Scope

Population Reporting System.

## Level

User goal.

## Preconditions

- The application is running.
- The world database is available and contains the data needed for the report.

## Success Condition

The system displays one population total for the selected continent, clearly identifying the area.

## Failed Condition

The system cannot produce the requested report and displays an explanatory message. No database records are changed.

## Primary Actor

Organisation user.

## Trigger

The user requests this report.

## Main Success Scenario

1. The user requests the population of a selected continent.
2. The system asks the user to specify the continent.
3. The user supplies the continent.
4. The system validates the selection and resolves any ambiguity.
5. The system sums country populations within the selected continent.
6. The system displays the population total labelled with the selected continent.

## Extensions

- **4a. The selection is blank or unrecognised:** Explain the problem and request a valid continent.
- **5a. The database is unavailable or retrieval fails:** Display an explanatory error message.
- **5b. No matching population data exists:** Display a no-data message rather than interpreting missing data as zero.

## Sub-variations

- **5. A valid recorded population is zero:** Display zero as a valid result.
- **6. Large totals:** Display the full population count without truncation or numeric overflow.

## Schedule

Use case documentation is required for Code Review 1.
Implementation and verification follow the team's sprint plan.

