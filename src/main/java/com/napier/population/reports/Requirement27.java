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
     *
     * @param continent The name of the continent to look up (e.g., "Europe", "Asia")
     * @return The aggregated continent population as a 64-bit long, or -1L if the input is invalid
     *         or a database error occurs
     */
    public long getPopulationOfContinent(String continent) {
        if (continent == null || continent.trim().isEmpty()) {
            System.out.println("Error: Continent parameter cannot be null or empty.");
            return -1L;
        }

        if (this.con == null) {
            System.out.println("Error: Database connection is not available.");
            return -1L;
        }

        String query = "SELECT SUM(Population) AS TotalPopulation "
                     + "FROM country "
                     + "WHERE Continent = ?";

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, continent.trim());

            try (ResultSet rset = stmt.executeQuery()) {
                if (rset.next()) {
                    return rset.getLong("TotalPopulation");
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve continent population: " + e.getMessage());
        }

        return -1L;
    }

    /**
     * Queries the database for the continent's population and prints the formatted tabular report.
     *
     * @param continent The name of the continent to report on
     */
    public void printContinentPopulation(String continent) {
        if (continent == null || continent.trim().isEmpty()) {
            System.out.println("Invalid continent name provided.");
            return;
        }

        long population = getPopulationOfContinent(continent);

        if (population == -1L) {
            System.out.println("Failed to retrieve population data for continent: " + continent);
            return;
        }

        System.out.println("\n-------------------------------------------------------------");
        System.out.printf("%-25s | %-30s%n", "Continent", "Total Population");
        System.out.println("-------------------------------------------------------------");
        System.out.printf("%-25s | %,30d%n", continent.trim(), population);
        System.out.println("-------------------------------------------------------------");
    }
}
