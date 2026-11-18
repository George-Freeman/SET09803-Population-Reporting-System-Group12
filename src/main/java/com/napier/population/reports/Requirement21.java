package com.napier.population.reports;

import com.napier.population.models.CapitalCity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Generates tje top N populated capital cities in a continent.
 */

public class Requirement21 {
    private final Connection con;

    public Requirement21(Connection con){
        this.con = con;
    }

    /**
     * Gets the top N populated capital cities in a specified continent.
     *
     * @param continent the continent to search
     * @param n the maximum number of capital cities to return
     * @return capital cities ordered from largest to smallest population
     */

    public List<CapitalCity> getTopNCapitalCitiesByContinent(String continent, int n) {
        List<CapitalCity> capitalCities = new ArrayList<>();

        if (con ==null || continent == null || continent.trim().isEmpty() || n <= 0) {
            System.out.println("Invalid connection, continent, or N specified.");
            return capitalCities;
        }

        String sql = "SELECT ci.Name, c.Name AS Country, ci.Population "
                + "FROM country c "
                + "JOIN city ci ON c.Capital = ci.ID "
                + "WHERE c.Continent = ? "
                + "ORDER BY ci.Population DESC "
                + "LIMIT ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, continent);
            pstmt.setInt(2, n);

            try (ResultSet rset = pstmt.executeQuery()) {
                while (rset.next()) {
                    CapitalCity capitalCity = new CapitalCity(
                    rset.getString("Name"),
                    rset.getString("Country"),
                    rset.getInt("Population")
                    );

                    capitalCities.add(capitalCity);
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to fetch capital cities for continent: " + continent);
            System.out.println(e.getMessage());
        }
        return capitalCities;
    }

    /**
     * Print table of capital city records
     */

    public void printCapitalCities(List<CapitalCity> capitalCities){
        if (capitalCities == null || capitalCities.isEmpty()){
            System.out.println("No capital cities to display.");
            return;
        }
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.printf("%-30s | %-30s | %-12s%n", "Capital City", "Country", "Population");
        System.out.println("--------------------------------------------------------------------------------");

        for (CapitalCity c : capitalCities) {
            System.out.printf("%-30s | %-30s | %,12d%n", c.getName(), c.getCountry(), c.getPopulation());
        }

        System.out.println("--------------------------------------------------------------------------------");
    }
}

