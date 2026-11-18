package com.napier.population.reports;

import com.napier.population.models.CapitalCity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Generates reports containing the top populated capital cities in the world.
 */
public class Requirement20 {
    private final Connection connection;

    /**
     * Creates the report using an existing database connection.
     *
     * @param connection connection to the World database
     */
    public Requirement20(Connection connection) {
        this.connection = connection;
    }

    /**
     * Gets the top N populated capital cities in the world.
     *
     * @param numberOfCities maximum number of capital cities to return
     * @return capital cities ordered from largest population to smallest
     */
    public List<CapitalCity> getTopCapitalCitiesWorld(int numberOfCities) {
        List<CapitalCity> capitalCities = new ArrayList<>();

        if (connection == null || numberOfCities <= 0) {
            return capitalCities;
        }

        String sql =
                "SELECT city.Name, country.Name AS Country, city.Population "
                        + "FROM country "
                        + "JOIN city ON country.Capital = city.ID "
                        + "ORDER BY city.Population DESC "
                        + "LIMIT ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, numberOfCities);

            try (ResultSet results = statement.executeQuery()) {
                while (results.next()) {
                    CapitalCity capitalCity = new CapitalCity(
                            results.getString("Name"),
                            results.getString("Country"),
                            results.getInt("Population")
                    );

                    capitalCities.add(capitalCity);
                }
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