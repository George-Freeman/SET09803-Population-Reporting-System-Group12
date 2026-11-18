package com.napier.population.reports;

import com.napier.population.models.PopulationReport;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests the population report by country.
 */
class Requirement25Test {

    /**
     * Confirms that database results are converted into a country report.
     */
    @Test
    void returnsPopulationReportForCountry() throws Exception {
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet results = mock(ResultSet.class);

        when(connection.prepareStatement(argThat(sql ->
                sql.contains("LEFT JOIN city")
                        && sql.contains("GROUP BY country.Code")
        ))).thenReturn(statement);

        when(statement.executeQuery()).thenReturn(results);
        when(results.next()).thenReturn(true, false);
        when(results.getString("Name"))
                .thenReturn("United Kingdom");
        when(results.getLong("TotalPopulation"))
                .thenReturn(59623400L);
        when(results.getLong("CityPopulation"))
                .thenReturn(30000000L);

        Requirement25 report = new Requirement25(connection);
        List<PopulationReport> populationReports =
                report.getPopulationByCountry();

        assertEquals(1, populationReports.size());

        PopulationReport country = populationReports.get(0);

        assertEquals("United Kingdom", country.getName());
        assertEquals(
                59623400L,
                country.getTotalPopulation()
        );
        assertEquals(
                30000000L,
                country.getCityPopulation()
        );
        assertEquals(
                29623400L,
                country.getNonCityPopulation()
        );
    }

    /**
     * Confirms that the population percentages are calculated correctly.
     */
    @Test
    void calculatesPopulationPercentages() {
        PopulationReport report =
                new PopulationReport("Test Country", 1000, 600, 400);

        assertEquals(
                60.0,
                report.getCityPopulationPercentage(),
                0.01
        );
        assertEquals(
                40.0,
                report.getNonCityPopulationPercentage(),
                0.01
        );
    }

    /**
     * Confirms that a missing database connection returns no reports.
     */
    @Test
    void handlesMissingConnection() {
        Requirement25 report = new Requirement25(null);

        List<PopulationReport> populationReports =
                report.getPopulationByCountry();

        assertTrue(populationReports.isEmpty());
    }

    /**
     * Confirms that a zero population does not cause division by zero.
     */
    @Test
    void handlesZeroPopulation() {
        PopulationReport report =
                new PopulationReport("Test Country", 0, 0, 0);

        assertEquals(
                0.0,
                report.getCityPopulationPercentage(),
                0.01
        );
        assertEquals(
                0.0,
                report.getNonCityPopulationPercentage(),
                0.01
        );
    }
}