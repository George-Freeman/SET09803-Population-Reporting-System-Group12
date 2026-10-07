package com.napier.population.reports;

import com.napier.population.models.City;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Requirement 7: All cities in the world,
 * ordered by population from largest to smallest.
 */
public class Requirement7 {
    private final Connection con;

    public Requirement7(Connection con) {
        this.con = con;
    }

    /**
     * Retrieves cities with their country names.
     */
    public List<City> getAllCitiesWorld() {
        List<City> cities = new ArrayList<>();

        String sql =
                "SELECT ci.Name, c.Name AS Country, "
                        + "ci.District, ci.Population "
                        + "FROM city ci "
                        + "JOIN country c ON ci.CountryCode = c.Code "
                        + "ORDER BY ci.Population DESC, ci.ID ASC";

        try (Statement stmt = con.createStatement();
             ResultSet rset = stmt.executeQuery(sql)) {

            while (rset.next()) {
                City city = new City();
                city.name = rset.getString("Name");
                city.country = rset.getString("Country");
                city.district = rset.getString("District");
                city.population = rset.getInt("Population");
                cities.add(city);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Failed to fetch city details", e);
        }

        return cities;
    }

    /**
     * Prints city names, countries, districts and populations.
     */
    public void printCities(List<City> cities) {
        if (cities == null || cities.isEmpty()) {
            System.out.println("No cities to display.");
            return;
        }

        String separator =
                "------------------------------------------------------------"
                        + "------------------------------------------------------------";

        System.out.println();
        System.out.println(separator);
        System.out.printf(
                "%-35s | %-45s | %-20s | %12s%n",
                "Name", "Country", "District", "Population");
        System.out.println(separator);

        for (City city : cities) {
            System.out.printf(
                    "%-35s | %-45s | %-20s | %,12d%n",
                    city.name,
                    city.country,
                    city.district,
                    city.population);
        }

        System.out.println(separator);
    }
}