package com.napier.population.reports;

import com.napier.population.models.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Requirement 31: The population of a city.
 * <p>
 * Several cities can share a name (for example in different countries), so every
 * match is returned with its country and district so the user can tell them apart.
 * </p>
 */
public class Requirement31 {

    /**
     * Active connection to the MySQL database.
     */
    private final Connection con;

    /**
     * Constructs a new Requirement31 instance with a database connection.
     *
     * @param con The active SQL database connection
     */
    public Requirement31(Connection con) {
        this.con = con;
    }

    /**
     * Retrieves every city with the given name, largest population first.
     *
     * @param cityName The name of the city (e.g., "Tokyo", "Valencia")
     * @return List of matching City objects; empty if the input is invalid, nothing
     *         matches, or a database error occurs
     */
    public List<City> getCitiesByName(String cityName) {
        List<City> cities = new ArrayList<>();

        if (con == null || cityName == null || cityName.trim().isEmpty()) {
            System.out.println("Invalid database connection or city name specified.");
            return cities;
        }

        String query = "SELECT ci.Name, c.Name AS Country, ci.District, ci.Population "
                + "FROM city ci "
                + "JOIN country c ON ci.CountryCode = c.Code "
                + "WHERE ci.Name = ? "
                + "ORDER BY ci.Population DESC";

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, cityName.trim());

            try (ResultSet rset = stmt.executeQuery()) {
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
            System.out.println("Failed to retrieve city population: " + e.getMessage());
        }

        return cities;
    }

    /**
     * Prints the matching cities as an aligned table.
     *
     * @param cities The cities to print
     */
    public void printCityPopulation(List<City> cities) {
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
