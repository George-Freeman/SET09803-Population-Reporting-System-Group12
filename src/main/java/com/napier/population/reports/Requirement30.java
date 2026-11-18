package com.napier.population.reports;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Requirement 30: The population of a district.
 * <p>
 * The World database has no district table, so a district's population is the sum of
 * the populations of the cities recorded in that district. District names can occur in
 * more than one country, in which case the cities from all of them are added together.
 * </p>
 */
public class Requirement30 {

    /**
     * Active connection to the MySQL database.
     */
    private final Connection con;

    /**
     * Constructs a new Requirement30 instance with a database connection.
     *
     * @param con The active SQL database connection
     */
    public Requirement30(Connection con) {
        this.con = con;
    }

    /**
     * Retrieves the population of a district (the total of its cities).
     *
     * @param district The name of the district (e.g., "Texas", "England")
     * @return The district's population, or -1L if the input is invalid, no city is
     *         recorded in the district, or a database error occurs
     */
    public long getPopulationOfDistrict(String district) {
        if (district == null || district.trim().isEmpty()) {
            System.out.println("Error: District parameter cannot be null or empty.");
            return -1L;
        }

        if (con == null) {
            System.out.println("Error: Database connection is not available.");
            return -1L;
        }

        String query = "SELECT SUM(Population) AS TotalPopulation "
                + "FROM city "
                + "WHERE District = ?";

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, district.trim());

            try (ResultSet rset = stmt.executeQuery()) {
                if (rset.next()) {
                    long total = rset.getLong("TotalPopulation");
                    // SUM over no rows is NULL: the district does not exist
                    if (!rset.wasNull()) {
                        return total;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve district population: " + e.getMessage());
        }

        return -1L;
    }

    /**
     * Looks up the population of a district and prints the formatted report,
     * or a message if the district cannot be found.
     *
     * @param district The name of the district to report on
     */
    public void printDistrictPopulation(String district) {
        if (district == null || district.trim().isEmpty()) {
            System.out.println("Invalid district name provided.");
            return;
        }

        long population = getPopulationOfDistrict(district);

        if (population == -1L) {
            System.out.println("No population data found for district: " + district.trim());
            return;
        }

        printDistrictPopulation(district, population);
    }

    /**
     * Formats and prints the district population report to standard output.
     *
     * @param district   The district name to display
     * @param population The population to display
     */
    public void printDistrictPopulation(String district, long population) {
        if (district == null || district.trim().isEmpty() || population < 0L) {
            System.out.println("Invalid district or population data provided for reporting.");
            return;
        }

        System.out.println("\n-------------------------------------------------------------");
        System.out.printf("%-25s | %-30s%n", "District", "Population (sum of cities)");
        System.out.println("-------------------------------------------------------------");
        System.out.printf("%-25s | %,30d%n", district.trim(), population);
        System.out.println("-------------------------------------------------------------");
    }
}
