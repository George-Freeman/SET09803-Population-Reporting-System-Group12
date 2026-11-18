package com.napier.population.reports;

import com.napier.population.models.LanguageReport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Generates the worldwide population report for the five required languages.
 */
public class Requirement32 {
    private final Connection connection;

    /**
     * Creates the report using an existing database connection.
     *
     * @param connection connection to the World database
     */
    public Requirement32(Connection connection) {
        this.connection = connection;
    }

    /**
     * Gets the estimated number and world-population percentage of people who
     * speak Chinese, English, Hindi, Spanish and Arabic.
     *
     * @return language records ordered by greatest number of speakers
     */
    public List<LanguageReport> getLanguagePopulationReport() {
        List<LanguageReport> reports = new ArrayList<>();

        if (connection == null) {
            return reports;
        }

        String sql =
                "SELECT countrylanguage.Language, "
                        + "ROUND(SUM(country.Population "
                        + "* countrylanguage.Percentage / 100)) AS Speakers, "
                        + "SUM(country.Population "
                        + "* countrylanguage.Percentage / 100) "
                        + "/ (SELECT SUM(Population) FROM country) "
                        + "* 100 AS WorldPercentage "
                        + "FROM countrylanguage "
                        + "JOIN country "
                        + "ON country.Code = countrylanguage.CountryCode "
                        + "WHERE countrylanguage.Language "
                        + "IN (?, ?, ?, ?, ?) "
                        + "GROUP BY countrylanguage.Language "
                        + "ORDER BY Speakers DESC";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, "Chinese");
            statement.setString(2, "English");
            statement.setString(3, "Hindi");
            statement.setString(4, "Spanish");
            statement.setString(5, "Arabic");

            try (ResultSet results = statement.executeQuery()) {
                while (results.next()) {
                    LanguageReport report = new LanguageReport(
                            results.getString("Language"),
                            results.getLong("Speakers"),
                            results.getDouble("WorldPercentage")
                    );

                    reports.add(report);
                }
            }
        } catch (SQLException exception) {
            System.out.println(
                    "Failed to retrieve language population report: "
                            + exception.getMessage()
            );
        }

        return reports;
    }

    /**
     * Displays the language population report as an aligned table.
     *
     * @param reports language records to display
     */
    public void printLanguagePopulationReport(
            List<LanguageReport> reports) {

        if (reports == null || reports.isEmpty()) {
            System.out.println(
                    "No language population information to display."
            );
            return;
        }

        System.out.println(
                "\n-------------------------------------------------------------------"
        );
        System.out.printf(
                "%-15s | %-22s | %-20s%n",
                "Language",
                "Number of Speakers",
                "World Population %"
        );
        System.out.println(
                "-------------------------------------------------------------------"
        );

        for (LanguageReport report : reports) {
            System.out.printf(
                    "%-15s | %,22d | %19.2f%%%n",
                    report.getLanguage(),
                    report.getSpeakers(),
                    report.getWorldPopulationPercentage()
            );
        }

        System.out.println(
                "-------------------------------------------------------------------"
        );
    }
}