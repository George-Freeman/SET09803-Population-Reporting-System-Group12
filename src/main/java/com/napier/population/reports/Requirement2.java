package com.napier.population.reports;

import com.napier.population.models.Country;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Requirement2 {
    private final Connection con;

    public Requirement2(Connection con) {
        this.con = con;
    }

    /**
     * Requirement 2: All the countries in a continent organised by largest population to smallest.
     *
     * @param continent The continent to filter by (e.g., "Europe", "Asia", "Africa").
     * @return List of Country objects in that continent.
     */
    public List<Country> getCountriesInContinent(String continent) {
        List<Country> countries = new ArrayList<>();

        if (con == null || continent == null || continent.trim().isEmpty()) {
            System.out.println("Invalid connection or continent specified.");
            return countries;
        }

        String sql = "SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS Capital "
                + "FROM country c "
                + "LEFT JOIN city ci ON c.Capital = ci.ID "
                + "WHERE c.Continent = ? "
                + "ORDER BY c.Population DESC";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, continent);

            try (ResultSet rset = pstmt.executeQuery()) {
                while (rset.next()) {
                    Country country = new Country();
                    country.code = rset.getString("Code");
                    country.name = rset.getString("Name");
                    country.continent = rset.getString("Continent");
                    country.region = rset.getString("Region");
                    country.population = rset.getInt("Population");
                    country.capital = rset.getString("Capital");

                    if (country.capital == null) {
                        country.capital = "N/A";
                    }

                    countries.add(country);
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to fetch country details for continent: " + continent);
            System.out.println(e.getMessage());
        }
        return countries;
    }

    /**
     * Prints an aligned table of country records.
     */
    public void printCountries(List<Country> countries) {
        if (countries == null || countries.isEmpty()) {
            System.out.println("No countries to display.");
            return;
        }

        System.out.println("\n--------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-5s | %-45s | %-15s | %-25s | %-12s | %-20s%n",
                "Code", "Name", "Continent", "Region", "Population", "Capital");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------");

        for (Country c : countries) {
            System.out.printf("%-5s | %-45s | %-15s | %-25s | %,12d | %-20s%n",
                    c.code, c.name, c.continent, c.region, c.population, c.capital);
        }
        System.out.println("--------------------------------------------------------------------------------------------------------------------------");
    }
}