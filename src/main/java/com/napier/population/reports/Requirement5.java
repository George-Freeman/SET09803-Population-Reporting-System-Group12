package com.napier.population.reports;

import com.napier.population.models.Country;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Requirement5 {
    private final Connection con;

    public Requirement5(Connection con) {
        this.con = con;
    }

    /**
     * Gets the top N populated countries in a selected continent.
     *
     * @param continent the continent to search
     * @param n the maximum number of countries to retrieve
     * @return countries ordered by population, largest first
     */
    public List<Country> getTopNCountriesByContinent(String continent, int n) {
        List<Country> countries = new ArrayList<>();

        if (con == null || continent == null
                || continent.trim().isEmpty() || n <= 0) {
            System.out.println(
                    "A database connection, continent and positive N are required.");
            return countries;
        }

        String sql =
                "SELECT c.Code, c.Name, c.Continent, c.Region, "
                        + "c.Population, ci.Name AS Capital "
                        + "FROM country c "
                        + "LEFT JOIN city ci ON c.Capital = ci.ID "
                        + "WHERE c.Continent = ? "
                        + "ORDER BY c.Population DESC, c.Code ASC "
                        + "LIMIT ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, continent.trim());
            pstmt.setInt(2, n);

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
            throw new IllegalStateException(
                    "Failed to fetch top N countries by continent.", e);
        }

        return countries;
    }

    /**
     * Prints an aligned table of country records.
     *
     * @param countries the countries to display
     */
    public void printCountries(List<Country> countries) {
        if (countries == null || countries.isEmpty()) {
            System.out.println("No countries to display.");
            return;
        }

        String separator = "-".repeat(137);

        System.out.println("\n" + separator);
        System.out.printf(
                "%-5s | %-45s | %-15s | %-25s | %-12s | %-20s%n",
                "Code", "Name", "Continent", "Region", "Population", "Capital");
        System.out.println(separator);

        for (Country country : countries) {
            System.out.printf(
                    "%-5s | %-45s | %-15s | %-25s | %,12d | %-20s%n",
                    country.code,
                    country.name,
                    country.continent,
                    country.region,
                    country.population,
                    country.capital);
        }

        System.out.println(separator);
    }
}