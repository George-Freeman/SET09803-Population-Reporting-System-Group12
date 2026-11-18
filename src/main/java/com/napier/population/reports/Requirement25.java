package com.napier.population.reports;

import com.napier.population.models.PopulationReport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Generates population reports for every country.
 */
public class Requirement25 {
    private final Connection connection;

    /**
     * Creates the report using an existing database connection.
     *
     * @param connection connection to the World database
     */
    public Requirement25(Connection connection) {
        this.connection = connection;
    }

    /**
     * Gets the total population, city population and non-city population
     * for every country.
     *
     * @return country population reports
     */
    public List<PopulationReport> getPopulationByCountry() {
        List<PopulationReport> reports = new ArrayList<>();

        if (connection == null) {
            return reports;
        }

        String sql =
                "SELECT country.Name, "
                        + "country.Population AS TotalPopulation, "
                        + "COALESCE(SUM(city.Population), 0) AS CityPopulation "
                        + "FROM country "
                        + "LEFT JOIN city ON city.CountryCode = country.Code "
                        + "GROUP BY country.Code, country.Name, country.Population "
                        + "ORDER BY country.Name";

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
                    "Failed to retrieve country population report: "
                            + exception.getMessage()
            );
        }

        return reports;
    }

    /**
     * Displays country population information as an aligned table.
     *
     * @param reports population reports to display
     */
    public void printPopulationReports(List<PopulationReport> reports) {
        if (reports == null || reports.isEmpty()) {
            System.out.println("No population reports to display.");
            return;
        }

        System.out.println(
                "\n----------------------------------------------------------------------------------------------------------------"
        );
        System.out.printf(
                "%-35s | %-16s | %-16s | %-10s | %-18s | %-10s%n",
                "Country",
                "Total Population",
                "City Population",
                "City %",
                "Non-City Population",
                "Non-City %"
        );
        System.out.println(
                "----------------------------------------------------------------------------------------------------------------"
        );

        for (PopulationReport report : reports) {
            System.out.printf(
                    "%-35s | %,16d | %,16d | %9.2f%% | %,18d | %9.2f%%%n",
                    report.getName(),
                    report.getTotalPopulation(),
                    report.getCityPopulation(),
                    report.getCityPopulationPercentage(),
                    report.getNonCityPopulation(),
                    report.getNonCityPopulationPercentage()
            );
        }

        System.out.println(
                "----------------------------------------------------------------------------------------------------------------"
        );
    }
}