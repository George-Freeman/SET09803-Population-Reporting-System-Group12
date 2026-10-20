package com.napier.population.reports;

import com.napier.population.models.City;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Requirement 14: The top N populated cities in a region where N is provided by the user.
 *
 * Provides methods to query the world database for cities ordered in descending
 * order of population, and to display the formatted results.
 */
public class Requirement14 {
    private final Connection con;

    public Requirement14(Connection con) {
        this.con = con;
    }

    /**
     * Retrieves the top N most populated cities in a specified region.
     *
     * @param region The name of the region (e.g., "Caribbean", "Western Europe").
     * @param numberOfCities Maximum number of cities to return (N > 0).
     * @return List of City objects.
     */
    public List<City> getTopNCitiesInRegion(String region, int numberOfCities) {
        List<City> cities = new ArrayList<>();

        if (con == null || region == null || region.trim().isEmpty() || numberOfCities <= 0) {
            System.out.println("Invalid database connection, region or number of cities specified.");
            return cities;
        }

        String sql = "SELECT ci.Name, c.Name AS Country, ci.District, ci.Population "
                + "FROM city ci "
                + "JOIN country c ON ci.CountryCode = c.Code "
                + "WHERE c.Region = ? "
                + "ORDER BY ci.Population DESC "
                + "LIMIT ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, region);
            pstmt.setInt(2, numberOfCities);

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
            System.out.println("Failed to fetch top cities for region: " + region);
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
