package com.napier.population.reports;

import com.napier.population.models.PopulationReport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Generates population reports for every continent.
 */
public class Requirement23 {
    private final Connection connection;

    /**
     * Creates the report using an existing database connection.
     *
     * @param connection connection to the World database
     */
    public Requirement23(Connection connection) {
        this.connection = connection;
    }

    /**
     * Gets the total population, city population and non-city population
     * for every continent.
     *
     * @return continent population reports
     */
    public List<PopulationReport> getPopulationByContinent() {
        List<PopulationReport> reports = new ArrayList<>();

        if (connection == null) {
            return reports;
        }

        String sql =
                "SELECT countryTotals.Continent AS Name, "
                        + "countryTotals.TotalPopulation, "
                        + "COALESCE(cityTotals.CityPopulation, 0) "
                        + "AS CityPopulation "
                        + "FROM ("
                        + "SELECT Continent, SUM(Population) "
                        + "AS TotalPopulation "
                        + "FROM country "
                        + "GROUP BY Continent"
                        + ") countryTotals "
                        + "LEFT JOIN ("
                        + "SELECT country.Continent, SUM(city.Population) "
                        + "AS CityPopulation "
                        + "FROM country "
                        + "JOIN city ON city.CountryCode = country.Code "
                        + "GROUP BY country.Continent"
                        + ") cityTotals "
                        + "ON countryTotals.Continent = cityTotals.Continent "
                        + "ORDER BY countryTotals.Continent";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet results = statement.executeQuery()) {

            while (results.next()) {
                long totalPopulation =
                        results.getLong("TotalPopulation");
                long cityPopulation =
                        results.getLong("CityPopulation");
                long nonCityPopulation =
                        totalPopulation - cityPopulation;

                PopulationReport report = new PopulationReport(
                        results.getString("Name"),
                        totalPopulation,
                        cityPopulation,
                        nonCityPopulation
                );

                reports.add(report);
            }
        } catch (SQLException exception) {
            System.out.println(
                    "Failed to retrieve continent population report: "
                            + exception.getMessage()
            );
        }

        return reports;
    }

    /**
     * Displays continent population information as an aligned table.
     *
     * @param reports population reports to display
     */
    public void printPopulationReports(List<PopulationReport> reports) {
        if (reports == null || reports.isEmpty()) {
            System.out.println("No population reports to display.");
            return;
        }

        System.out.println(
                "\n---------------------------------------------------------------------------------------------------------------"
        );
        System.out.printf(
                "%-15s | %-16s | %-16s | %-10s | %-18s | %-10s%n",
                "Continent",
                "Total Population",
                "City Population",
                "City %",
                "Non-City Population",
                "Non-City %"
        );
        System.out.println(
                "---------------------------------------------------------------------------------------------------------------"
        );

        for (PopulationReport report : reports) {
            System.out.printf(
                    "%-15s | %,16d | %,16d | %9.2f%% | %,18d | %9.2f%%%n",
                    report.getName(),
                    report.getTotalPopulation(),
                    report.getCityPopulation(),
                    report.getCityPopulationPercentage(),
                    report.getNonCityPopulation(),
                    report.getNonCityPopulationPercentage()
            );
        }

        System.out.println(
                "---------------------------------------------------------------------------------------------------------------"
        );
    }
}