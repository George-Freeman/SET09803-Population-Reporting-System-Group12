# Use Case 32: Generate Language Population Report

## Goal in Context

As an organisation user, I want to view the estimated numbers of Chinese, English, Hindi, Spanish, and Arabic speakers and their shares of world population so that I can compare the reach of these languages.

## Scope

Population Reporting System.

## Level

User goal.

## Preconditions

- The application is running.
- The world database is available and contains the data needed for the report.

## Success Condition

The system displays all five requested languages, ordered by estimated speaker count from greatest to smallest, with:

- Language name
- Estimated number of speakers
- Percentage of world population

## Failed Condition

The system cannot produce the requested report and displays an explanatory message. No database records are changed.

## Primary Actor

Organisation user.

## Trigger

The user requests this report.

## Main Success Scenario

1. The user requests the language population report.
2. The system retrieves country populations and the recorded percentages for Chinese, English, Hindi, Spanish, and Arabic in each country.
3. The system estimates speakers per country and language as country population multiplied by the recorded language percentage divided by 100, then sums by language.
4. The system calculates world population by summing each country's population once.
5. The system calculates each language's world population percentage as its estimated speaker count divided by world population, multiplied by 100.
6. The system orders the five languages by unrounded estimated speaker count from greatest to smallest.
7. The system displays the language names, estimated speaker counts, and world population percentages.

## Extensions

- **2a. Retrieval fails:** Display an error message without presenting a partial report as successful.
- **2b. Required country population data is unavailable:** Explain that the estimates cannot be calculated.
- **5. World population is zero:** Display world population percentages as "N/A" because division by zero is undefined.

## Sub-variations

- **2. A language has no recorded entry for a country:** That country contributes zero recorded speakers for that language. If a language has no entries anywhere, retain its row and mark the zero estimate as having no recorded language data.
- **3. Official and non-official languages:** Include both; the request does not restrict languages to official status.
- **6. Speaker estimates are equal:** Order tied languages alphabetically.
- **7. Presentation:** Round speaker estimates to whole people and percentages to two decimal places only for display; retain unrounded values for calculation and sorting.
- **7. Interpretation:** State that estimates use the supplied database's percentages and populations. Language shares are not required to total 100%, and the data does not establish mutually exclusive speaker groups.

## Schedule

Use case documentation is required for Code Review 1.
Implementation and verification follow the team's sprint plan.

