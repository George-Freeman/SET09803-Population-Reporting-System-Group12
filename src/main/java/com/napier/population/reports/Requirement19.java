package com.napier.population.reports;

import com.napier.population.models.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Requirement 19: All the capital cities in a region organised by largest population to smallest.
 *
 * @author Group 12
 * @version 1.0
 */
public class Requirement19 {

    /**
     * Active connection to the MySQL database.
     */
    private final Connection con;

    /**
     * Constructs a new Requirement19 instance with a database connection.
     *
     * @param con The active SQL database connection
     */
    public Requirement19(Connection con) {
        this.con = con;
    }

    /**
     * Retrieves all capital cities within a specified region ordered by population descending.
     *
     * @param region The name of the region (e.g., "Western Europe", "Middle East")
     * @return An ArrayList of City objects, or null if the connection is missing,
     *         input is invalid, or a SQL error occurs
     */
    public ArrayList<City> getCapitalCitiesByRegion(String region) {
        if (region == null || region.trim().isEmpty()) {
            System.out.println("Error: Region parameter cannot be null or empty.");
            return null;
        }

        if (this.con == null) {
            System.out.println("Error: Database connection is not available.");
            return null;
        }

        ArrayList<City> capitalCities = new ArrayList<>();

        String query = "SELECT city.Name, country.Name AS Country, city.District, city.Population "
                + "FROM country "
                + "JOIN city ON country.Capital = city.ID "
                + "WHERE country.Region = ? "
                + "ORDER BY city.Population DESC";

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, region.trim());

            try (ResultSet rset = stmt.executeQuery()) {
                while (rset.next()) {
                    City city = new City(
                            rset.getString("Name"),
                            rset.getString("Country"),
                            rset.getString("District"),
                            rset.getInt("Population")
                    );
                    capitalCities.add(city);
                }
            }
            return capitalCities;

        } catch (SQLException e) {
            System.out.println("Failed to get capital cities for region: " + e.getMessage());
            return null;
        }
    }

    /**
     * Prints a formatted table of capital cities to standard output.
     *
     * @param cities The list of capital City objects to display
     */
    public void printCapitalCities(ArrayList<City> cities) {
        if (cities == null || cities.isEmpty()) {
            System.out.println("No capital cities to display.");
            return;
        }

        System.out.println("\n-------------------------------------------------------------------------------------------------");
        System.out.printf("%-35s | %-35s | %15s%n", "Capital City", "Country", "Population");
        System.out.println("-------------------------------------------------------------------------------------------------");

        for (City city : cities) {
            if (city == null) {
                continue;
            }
            System.out.printf("%-35s | %-35s | %,15d%n",
                    city.getName(),
                    city.getCountry(),
                    city.getPopulation()
            );
        }

        System.out.println("-------------------------------------------------------------------------------------------------");
    }
}