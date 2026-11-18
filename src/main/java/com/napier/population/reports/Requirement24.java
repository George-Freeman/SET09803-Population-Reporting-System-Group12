package com.napier.population.reports;

import com.napier.population.models.PopulationReport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Generates population reports for every region.
 */
public class Requirement24 {
    private final Connection connection;

    public Requirement24(Connection connection) {
        this.connection = connection;
    }

    /**
     * Gets the total population, city population and non-city population
     * for every region.
     *
     * @return region population reports
     */
    public List<PopulationReport> getPopulationByRegion() {
        List<PopulationReport> reports = new ArrayList<>();

        if (connection == null) {
            return reports;
        }

        String sql =
                "SELECT countryTotals.Region AS Name, "
                        + "countryTotals.TotalPopulation, "
                        + "COALESCE(cityTotals.CityPopulation, 0) AS CityPopulation "
                        + "FROM ("
                        + "SELECT Region, SUM(Population) AS TotalPopulation "
                        + "FROM country "
                        + "GROUP BY Region"
                        + ") countryTotals "
                        + "LEFT JOIN ("
                        + "SELECT country.Region, SUM(city.Population) AS CityPopulation "
                        + "FROM country "
                        + "JOIN city ON city.CountryCode = country.Code "
                        + "GROUP BY country.Region"
                        + ") cityTotals "
                        + "ON countryTotals.Region = cityTotals.Region "
                        + "ORDER BY countryTotals.Region";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet results = statement.executeQuery()) {

            while (results.next()) {
                long totalPopulation = results.getLong("TotalPopulation");
                long cityPopulation = results.getLong("CityPopulation");
                long nonCityPopulation = totalPopulation - cityPopulation;

                reports.add(new PopulationReport(
                        results.getString("Name"),
                        totalPopulation,
                        cityPopulation,
                        nonCityPopulation
                ));
            }
        } catch (SQLException exception) {
            System.out.println(
                    "Failed to retrieve region population report: "
                            + exception.getMessage()
            );
        }

        return reports;
    }

    /**
     * Displays region population information as an aligned table.
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
                "%-25s | %-16s | %-16s | %-10s | %-18s | %-10s%n",
                "Region",
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
                    "%-25s | %,16d | %,16d | %9.2f%% | %,18d | %9.2f%%%n",
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
