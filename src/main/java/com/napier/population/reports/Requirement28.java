package com.napier.population.reports;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Retrieves the total population for a selected region.
 */
public class Requirement28 {
    private final Connection connection;

    public Requirement28(Connection connection) {
        this.connection = connection;
    }

    /**
     * Gets the total population of countries in the requested region.
     *
     * @param region region name, such as "Caribbean" or "North America"
     * @return total population, or zero if the connection or region is invalid,
     *         or no matching population is found
     */
    public long getPopulationByRegion(String region) {
        if (connection == null || region == null || region.trim().isEmpty()) {
            return 0;
        }

        String sql = "SELECT COALESCE(SUM(Population), 0) AS TotalPopulation "
                + "FROM country WHERE Region = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, region.trim());
            try (ResultSet results = statement.executeQuery()) {
                return results.next() ? results.getLong("TotalPopulation") : 0;
            }
        } catch (SQLException exception) {
            System.out.println("Failed to retrieve population for region " + region + ": " + exception.getMessage());
            return 0;
        }
    }

    /**
     * Displays the total population for the selected region.
     *
     * @param region region name
     */
    public void printPopulation(String region) {
        if (region == null || region.trim().isEmpty()) {
            System.out.println("Please specify a valid region.");
            return;
        }

        long population = getPopulationByRegion(region);
        System.out.printf("Population of %s: %,d%n", region.trim(), population);
    }
}
