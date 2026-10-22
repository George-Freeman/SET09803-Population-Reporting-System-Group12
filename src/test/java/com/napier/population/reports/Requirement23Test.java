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
 * Tests the population report by continent.
 */
class Requirement23Test {

    /**
     * Confirms that database results are converted into a continent report.
     */
    @Test
    void returnsPopulationReportForContinent() throws Exception {
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet results = mock(ResultSet.class);

        when(connection.prepareStatement(argThat(sql ->
                sql.contains("GROUP BY Continent")
                        && sql.contains("GROUP BY country.Continent")
        ))).thenReturn(statement);

        when(statement.executeQuery()).thenReturn(results);
        when(results.next()).thenReturn(true, false);
        when(results.getString("Name")).thenReturn("Europe");
        when(results.getLong("TotalPopulation"))
                .thenReturn(730074600L);
        when(results.getLong("CityPopulation"))
                .thenReturn(241942813L);

        Requirement23 report = new Requirement23(connection);
        List<PopulationReport> populationReports =
                report.getPopulationByContinent();

        assertEquals(1, populationReports.size());

        PopulationReport europe = populationReports.get(0);

        assertEquals("Europe", europe.getName());
        assertEquals(730074600L, europe.getTotalPopulation());
        assertEquals(241942813L, europe.getCityPopulation());
        assertEquals(
                488131787L,
                europe.getNonCityPopulation()
        );
    }

    /**
     * Confirms that both population percentages are calculated correctly.
     */
    @Test
    void calculatesPopulationPercentages() {
        PopulationReport report =
                new PopulationReport("Test", 1000, 600, 400);

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
        Requirement23 report = new Requirement23(null);

        List<PopulationReport> populationReports =
                report.getPopulationByContinent();

        assertTrue(populationReports.isEmpty());
    }

    /**
     * Confirms that a zero population does not cause division by zero.
     */
    @Test
    void handlesZeroPopulation() {
        PopulationReport report =
                new PopulationReport("Test", 0, 0, 0);

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