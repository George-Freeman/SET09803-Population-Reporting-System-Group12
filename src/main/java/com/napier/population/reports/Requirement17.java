package com.napier.population.reports;

import com.napier.population.models.CapitalCity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Generates a report of every capital city in the world, ordered from the
 * largest population to the smallest.
 */
public class Requirement17 {
    private final Connection connection;

    /**
     * Creates the report using an existing database connection.
     *
     * @param connection connection to the World database
     */
    public Requirement17(Connection connection) {
        this.connection = connection;
    }

    /**
     * Retrieves all capital cities in the world in descending population order.
     *
     * @return capital cities ordered from largest population to smallest
     */
    public List<CapitalCity> getCapitalCitiesWorld() {
        List<CapitalCity> capitalCities = new ArrayList<>();

        if (connection == null) {
            return capitalCities;
        }

        String sql =
                "SELECT city.Name, country.Name AS Country, city.Population "
                        + "FROM country "
                        + "JOIN city ON country.Capital = city.ID "
                        + "ORDER BY city.Population DESC";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet results = statement.executeQuery()) {

            while (results.next()) {
                CapitalCity capitalCity = new CapitalCity(
                        results.getString("Name"),
                        results.getString("Country"),
                        results.getInt("Population")
                );
                capitalCities.add(capitalCity);
            }
        } catch (SQLException exception) {
            System.out.println(
                    "Failed to retrieve capital cities: "
                            + exception.getMessage()
            );
        }

        return capitalCities;
    }

    /**
     * Displays capital cities as an aligned report.
     *
     * @param capitalCities capital cities to display
     */
    public void printCapitalCities(List<CapitalCity> capitalCities) {
        if (capitalCities == null || capitalCities.isEmpty()) {
            System.out.println("No capital cities to display.");
            return;
        }

        System.out.println(
                "\n-------------------------------------------------------------------------------------------"
        );
        System.out.printf(
                "%-35s | %-35s | %-12s%n",
                "Name", "Country", "Population"
        );
        System.out.println(
                "-------------------------------------------------------------------------------------------"
        );

        for (CapitalCity capitalCity : capitalCities) {
            System.out.printf(
                    "%-35s | %-35s | %,12d%n",
                    capitalCity.getName(),
                    capitalCity.getCountry(),
                    capitalCity.getPopulation()
            );
        }

        System.out.println(
                "-------------------------------------------------------------------------------------------"
        );
    }
}