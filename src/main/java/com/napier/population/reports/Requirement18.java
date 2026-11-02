package com.napier.population.reports;

import com.napier.population.models.CapitalCity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Generates a population-ranked list of capital cities in a continent.
 */
public class Requirement18 {
    private final Connection connection;

    /**
     * Creates the report using an existing database connection.
     *
     * @param connection connection to the World database
     */
    public Requirement18(Connection connection) {
        this.connection = connection;
    }

    /**
     * Gets capital cities in the specified continent, ordered by population.
     *
     * @param continent continent containing the requested capitals
     * @return capital cities ordered from largest population to smallest
     */
    public List<CapitalCity> getCapitalCitiesByContinent(String continent) {
        List<CapitalCity> capitalCities = new ArrayList<>();

        if (connection == null || continent == null || continent.trim().isEmpty()) {
            return capitalCities;
        }

        String sql = "SELECT city.Name, country.Name AS Country, city.Population "
                + "FROM country "
                + "JOIN city ON country.Capital = city.ID "
                + "WHERE country.Continent = ? "
                + "ORDER BY city.Population DESC, city.ID ASC";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, continent.trim());

            try (ResultSet results = statement.executeQuery()) {
                while (results.next()) {
                    capitalCities.add(new CapitalCity(
                            results.getString("Name"),
                            results.getString("Country"),
                            results.getInt("Population")
                    ));
                }
            }
        } catch (SQLException exception) {
            System.out.println("Failed to retrieve capital cities: " + exception.getMessage());
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

        System.out.println("\n-------------------------------------------------------------------------------------------");
        System.out.printf("%-35s | %-35s | %-12s%n", "Name", "Country", "Population");
        System.out.println("-------------------------------------------------------------------------------------------");

        for (CapitalCity capitalCity : capitalCities) {
            System.out.printf("%-35s | %-35s | %,12d%n",
                    capitalCity.getName(), capitalCity.getCountry(), capitalCity.getPopulation());
        }

        System.out.println("-------------------------------------------------------------------------------------------");
    }
}
