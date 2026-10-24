package com.napier.population.reports;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Requirement 29: The population of a country.
 * <p>
 * Looks up the population of a user-specified country in the MySQL World database
 * and prints it as a formatted report.
 * </p>
 */
public class Requirement29 {

    /**
     * Active connection to the MySQL database.
     */
    private final Connection con;

    /**
     * Constructs a new Requirement29 instance with a database connection.
     *
     * @param con The active SQL database connection
     */
    public Requirement29(Connection con) {
        this.con = con;
    }

    /**
     * Retrieves the population of a country.
     *
     * @param country The name of the country (e.g., "Japan", "Brazil")
     * @return The country's population, or -1L if the input is invalid, the country
     *         is not found, or a database error occurs
     */
    public long getPopulationOfCountry(String country) {
        if (country == null || country.trim().isEmpty()) {
            System.out.println("Error: Country parameter cannot be null or empty.");
            return -1L;
        }

        if (con == null) {
            System.out.println("Error: Database connection is not available.");
            return -1L;
        }

        String query = "SELECT Population "
                + "FROM country "
                + "WHERE Name = ?";

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, country.trim());

            try (ResultSet rset = stmt.executeQuery()) {
                if (rset.next()) {
                    return rset.getLong("Population");
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve country population: " + e.getMessage());
        }

        return -1L;
    }

    /**
     * Looks up the population of a country and prints the formatted report,
     * or a message if the country cannot be found.
     *
     * @param country The name of the country to report on
     */
    public void printCountryPopulation(String country) {
        if (country == null || country.trim().isEmpty()) {
            System.out.println("Invalid country name provided.");
            return;
        }

        long population = getPopulationOfCountry(country);

        if (population == -1L) {
            System.out.println("No population data found for country: " + country.trim());
            return;
        }

        printCountryPopulation(country, population);
    }

    /**
     * Formats and prints the country population report to standard output.
     *
     * @param country    The country name to display
     * @param population The population to display
     */
    public void printCountryPopulation(String country, long population) {
        if (country == null || country.trim().isEmpty() || population < 0L) {
            System.out.println("Invalid country or population data provided for reporting.");
            return;
        }

        System.out.println("\n-------------------------------------------------------------");
        System.out.printf("%-25s | %-30s%n", "Country", "Population");
        System.out.println("-------------------------------------------------------------");
        System.out.printf("%-25s | %,30d%n", country.trim(), population);
        System.out.println("-------------------------------------------------------------");
    }
}
