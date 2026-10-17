package com.napier.population.reports;

import com.napier.population.models.City;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Requirement8 {
    private final Connection con;

    public Requirement8(Connection con) {
        this.con = con;
    }

    /**
     * Requirement 8: All the cities in a continent organised by largest population to smallest.
     *
     * @param continent The name of the continent (e.g., "Europe", "Asia").
     * @return List of City objects matching existing City model fields.
     */
    public List<City> getCitiesInContinent(String continent) {
        List<City> cities = new ArrayList<>();

        if (con == null || continent == null || continent.trim().isEmpty()) {
            System.out.println("Invalid database connection or continent specified.");
            return cities;
        }

        String sql = "SELECT ci.Name, c.Name AS Country, ci.District, ci.Population "
                + "FROM city ci "
                + "JOIN country c ON ci.CountryCode = c.Code "
                + "WHERE c.Continent = ? "
                + "ORDER BY ci.Population DESC";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, continent);

            try (ResultSet rset = pstmt.executeQuery()) {
                while (rset.next()) {
                    City city = new City();
                    city.name = rset.getString("Name");
                    city.country = rset.getString("Country");
                    city.district = rset.getString("District");
                    city.population = rset.getInt("Population");

                    cities.add(city);
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to fetch cities for continent: " + continent);
            System.out.println(e.getMessage());
        }
        return cities;
    }

    /**
     * Prints an aligned table of city records.
     */
    public void printCities(List<City> cities) {
        if (cities == null || cities.isEmpty()) {
            System.out.println("No cities to display.");
            return;
        }

        System.out.println("\n---------------------------------------------------------------------------------------------------------");
        System.out.printf("%-35s | %-35s | %-25s | %-12s%n", "Name", "Country", "District", "Population");
        System.out.println("---------------------------------------------------------------------------------------------------------");

        for (City city : cities) {
            System.out.printf("%-35s | %-35s | %-25s | %,12d%n",
                    city.name, city.country, city.district, city.population);
        }
        System.out.println("---------------------------------------------------------------------------------------------------------");
    }
}