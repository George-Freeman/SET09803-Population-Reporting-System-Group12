package com.napier.population.reports;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Requirement 27: Generates a population report for a user-specified continent.
 * <p>
 * This class handles database queries against the MySQL World database to aggregate
 * and report the total population across all countries residing within a given continent.
 * It encapsulates parameter validation, database retrieval, and tabular terminal formatting.
 * </p>
 *
 * @author Group 12
 * @version 1.0
 */
public class Requirement27 {

    /**
     * Active connection to the MySQL database.
     */
    private final Connection con;

    /**
     * Constructs a new Requirement27 instance with a database connection.
     *
     * @param con The active SQL database connection
     */
    public Requirement27(Connection con) {
        this.con = con;
    }

    /**
     * Retrieves the total population of a user-specified continent from the database.
     * <p>
     * Aggregates population data from the {@code country} table using SQL {@code SUM()}.
     * Since continental population numbers exceed 32-bit integer limits, the value
     * is returned as a 64-bit {@code long}.
     * </p>
     *
     * @param continent The name of the continent to look up (e.g., "Europe", "Asia")
     * @return The aggregated continent population as a long, or -1 if the input is invalid
     *         or a database error occurs
     */
    public long getPopulationOfContinent(String continent) {
        // Defensive check: verify continent argument is non-null and non-empty
        if (continent == null || continent.trim().isEmpty()) {
            System.out.println("Error: Continent parameter cannot be null or empty.");
            return -1;
        }

        // Defensive check: ensure active database connection exists before executing query
        if (this.con == null) {
            System.out.println("Error: Database connection is not available.");
            return -1;
        }

        // Parameterized SQL query to aggregate population across matching continent countries
        String query = "SELECT SUM(Population) AS TotalPopulation "
                     + "FROM country "
                     + "WHERE Continent = ?";

        // Try-with-resources safely closes PreparedStatement and ResultSet
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            // Bind trimmed continent parameter safely to prevent SQL injection vulnerabilities
            stmt.setString(1, continent.trim());

            try (ResultSet rset = stmt.executeQuery()) {
                // If a record exists, read the aggregate population as a 64-bit integer
                if (rset.next()) {
                    return rset.getLong("TotalPopulation");
                }
            }
        } catch (SQLException e) {
            // Log database communication or SQL query execution failures
            System.out.println("Failed to retrieve continent population: " + e.getMessage());
        }

        // Return error code -1 if retrieval fails
        return -1;
    }

    /**
     * Queries the database for the continent's population and prints the formatted tabular report.
     *
     * @param continent The name of the continent to report on
     */
    public void printContinentPopulation(String continent) {
        // Defensive check: guard against null or blank continent strings
        if (continent == null || continent.trim().isEmpty()) {
            System.out.println("Invalid continent name provided.");
            return;
        }

        // Query the database directly using the continent name
        long population = getPopulationOfContinent(continent);

        // Verify query executed successfully
        if (population == -1) {
            System.out.println("Failed to retrieve population data for continent: " + continent);
            return;
        }

        // Render tabular output with thousands separator formatting
        System.out.println("\n-------------------------------------------------------------");
        System.out.printf("%-25s | %-30s%n", "Continent", "Total Population");
        System.out.println("-------------------------------------------------------------");
        System.out.printf("%-25s | %,30d%n", continent.trim(), population);
        System.out.println("-------------------------------------------------------------");
    }
}
