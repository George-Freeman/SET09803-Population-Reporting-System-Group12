package com.napier.population.reports;

import com.napier.population.models.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Requirement 9: All the cities in a region organised by largest population to smallest.
 */
public class Requirement9 {

    private final Connection con;

    public Requirement9(Connection con) {
        this.con = con;
    }

    /**
     * Retrieves all cities in a specified region ordered by population descending.
     *
     * @param region The name of the region (e.g. "Caribbean", "Western Europe").
     * @return List of City objects, or an empty list if invalid or error occurs.
     */
    public List<City> getCitiesInRegion(String region) {
        List<City> cities = new ArrayList<>();

        if (con == null || region == null || region.trim().isEmpty()) {
            System.out.println("Invalid database connection or region specified.");
            return cities;
        }

        String query = "SELECT city.Name, country.Name AS Country, city.District, city.Population " +
                "FROM city " +
                "JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Region = ? " +
                "ORDER BY city.Population DESC";

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, region);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    City city = new City();
                    city.setName(rs.getString("Name"));
                    city.setCountry(rs.getString("Country"));
                    city.setDistrict(rs.getString("District"));
                    city.setPopulation(rs.getInt("Population"));
                    cities.add(city);
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to get city details: " + e.getMessage());
        }

        return cities;
    }

    /**
     * Prints a formatted table of cities to the console.
     *
     * @param cities The list of cities to display.
     */
    public void printCities(List<City> cities) {
        if (cities == null || cities.isEmpty()) {
            System.out.println("No cities to display.");
            return;
        }

        System.out.println("--------------------------------------------------------------------------------------");
        System.out.printf("%-35s | %-30s | %-20s | %-12s%n", "Name", "Country", "District", "Population");
        System.out.println("--------------------------------------------------------------------------------------");

        for (City city : cities) {
            if (city == null) continue;
            System.out.printf("%-35s | %-30s | %-20s | %,12d%n",
                    city.getName(),
                    city.getCountry(),
                    city.getDistrict(),
                    city.getPopulation());
        }

        System.out.println("--------------------------------------------------------------------------------------");
    }
}