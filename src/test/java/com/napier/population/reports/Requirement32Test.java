package com.napier.population.reports;

import com.napier.population.models.LanguageReport;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the language population report.
 */
class Requirement32Test {

    @Test
    void getReportReturnsEmptyForNullConnection() {
        Requirement32 requirement = new Requirement32(null);

        assertTrue(
                requirement.getLanguagePopulationReport().isEmpty()
        );
    }

    @Test
    void getReportCreatesLanguageRecords() throws SQLException {
        Connection connection = mock(Connection.class);
        PreparedStatement statement =
                mock(PreparedStatement.class);
        ResultSet results = mock(ResultSet.class);

        when(connection.prepareStatement(anyString()))
                .thenReturn(statement);
        when(statement.executeQuery()).thenReturn(results);
        when(results.next()).thenReturn(true, false);
        when(results.getString("Language"))
                .thenReturn("English");
        when(results.getLong("Speakers"))
                .thenReturn(1_000_000_000L);
        when(results.getDouble("WorldPercentage"))
                .thenReturn(16.5);

        Requirement32 requirement =
                new Requirement32(connection);

        List<LanguageReport> reports =
                requirement.getLanguagePopulationReport();

        assertEquals(1, reports.size());
        assertEquals(
                "English",
                reports.get(0).getLanguage()
        );
        assertEquals(
                1_000_000_000L,
                reports.get(0).getSpeakers()
        );
        assertEquals(
                16.5,
                reports.get(0)
                        .getWorldPopulationPercentage(),
                0.001
        );
    }

    @Test
    void getReportHandlesSqlException()
            throws SQLException {

        Connection connection = mock(Connection.class);

        when(connection.prepareStatement(anyString()))
                .thenThrow(
                        new SQLException("Test database error")
                );

        Requirement32 requirement =
                new Requirement32(connection);

        assertTrue(
                requirement.getLanguagePopulationReport().isEmpty()
        );
    }

    @Test
    void printReportHandlesNullList() {
        Requirement32 requirement = new Requirement32(null);

        assertDoesNotThrow(
                () -> requirement
                        .printLanguagePopulationReport(null)
        );
    }

    @Test
    void printReportHandlesEmptyList() {
        Requirement32 requirement = new Requirement32(null);

        assertDoesNotThrow(
                () -> requirement.printLanguagePopulationReport(
                        Collections.emptyList()
                )
        );
    }

    @Test
    void printReportDisplaysLanguageRecords() {
        Requirement32 requirement = new Requirement32(null);

        List<LanguageReport> reports = List.of(
                new LanguageReport(
                        "English",
                        1_000_000_000L,
                        16.5
                )
        );

        assertDoesNotThrow(
                () -> requirement
                        .printLanguagePopulationReport(reports)
        );
    }
}