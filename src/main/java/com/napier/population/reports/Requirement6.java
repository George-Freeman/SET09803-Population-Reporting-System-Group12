package com.napier.population.reports;

import com.napier.population.models.Country;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Requirement6 {
    private final Connection con;

    public Requirement6(Connection con) {
        this.con = con;
    }

    /**
     * Requirement 6: Top N populated countries in a selected region.
     *
     * @param region The region name (e.g., "Caribbean", "Western Europe").
     * @param n The number of top countries to retrieve.
     * @return List of Country objects.
     */
    public List<Country> getTopNCountriesRegion(String region, int n) {
        List<Country> countries = new ArrayList<>();

        if (con == null || region == null || region.trim().isEmpty() || n <= 0) {
            System.out.println("Invalid connection, region, or N value specified.");
            return countries;
        }

        String sql = "SELECT c.Code, c.Name, c.Continent, c.Region, c.Population, ci.Name AS Capital "
                + "FROM country c "
                + "LEFT JOIN city ci ON c.Capital = ci.ID "
                + "WHERE c.Region = ? "
                + "ORDER BY c.Population DESC "
                + "LIMIT ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, region);
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
            System.out.println("Failed to fetch top N countries for region: " + region);
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