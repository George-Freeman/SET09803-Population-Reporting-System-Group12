package com.napier.population.reports;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Requirement27 {

    private final Connection con;

    public Requirement27(Connection con) {
        this.con = con;
    }

    /**
     * Retrieves the total population of a specified continent.
     *
     * @param continent The name of the continent
     * @return Total population as a long, or -1 if invalid/database error
     */
    public long getPopulationOfContinent(String continent) {
        if (continent == null || continent.trim().isEmpty() || con == null) {
            return -1;
        }

        String query = "SELECT SUM(Population) AS TotalPopulation FROM country WHERE Continent = ?";

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, continent.trim());
            try (ResultSet rset = stmt.executeQuery()) {
                if (rset.next()) {
                    return rset.getLong("TotalPopulation");
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to get continent population: " + e.getMessage());
        }

        return -1;
    }

    /**
     * Formats and prints the population result for the specified continent.
     *
     * @param continent  The continent name
     * @param population The total population count
     */
    public void printContinentPopulation(String continent, long population) {
        if (continent == null || continent.trim().isEmpty() || population < 0) {
            System.out.println("Invalid continent or population data.");
            return;
        }

        System.out.println("\n-------------------------------------------------------------");
        System.out.printf("%-25s | %-30s%n", "Continent", "Total Population");
        System.out.println("-------------------------------------------------------------");
        System.out.printf("%-25s | %,30d%n", continent, population);
        System.out.println("-------------------------------------------------------------");
    }
}