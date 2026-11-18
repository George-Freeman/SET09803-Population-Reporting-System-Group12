package com.napier.population.reports;

import com.napier.population.models.City;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Requirement 10: All the cities in a country organised by largest population to smallest.
 *
 * Provides methods to query the world database for cities ordered in descending
 * order of population, and to display the formatted results.
 */
public class Requirement10 {
    private final Connection con;

    public Requirement10(Connection con) {
        this.con = con;
    }

    /**
     * Retrieves all cities in a specified country ordered by population descending.
     *
     * @param country The name of the country (e.g., "Japan", "Brazil").
     * @return List of City objects.
     */
    public List<City> getCitiesInCountry(String country) {
        List<City> cities = new ArrayList<>();

        if (con == null || country == null || country.trim().isEmpty()) {
            System.out.println("Invalid database connection or country specified.");
            return cities;
        }

        String sql = "SELECT ci.Name, c.Name AS Country, ci.District, ci.Population "
                + "FROM city ci "
                + "JOIN country c ON ci.CountryCode = c.Code "
                + "WHERE c.Name = ? "
                + "ORDER BY ci.Population DESC";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, country);

            try (ResultSet rset = pstmt.executeQuery()) {
                while (rset.next()) {
                    City city = new City();
                    city.setName(rset.getString("Name"));
                    city.setCountry(rset.getString("Country"));
                    city.setDistrict(rset.getString("District"));
                    city.setPopulation(rset.getInt("Population"));

                    cities.add(city);
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to fetch cities for country: " + country);
            System.out.println(e.getMessage());
        }
        return cities;
    }

    /**
     * Prints an aligned table of city records.
     *
     * @param cities List of cities to print.
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
                    city.getName(), city.getCountry(), city.getDistrict(), city.getPopulation());
        }
        System.out.println("---------------------------------------------------------------------------------------------------------");
    }
}
