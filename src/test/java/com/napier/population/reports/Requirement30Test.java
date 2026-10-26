package com.napier.population.reports;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

/**
 * Tests Requirement 30: the population of a district.
 */
class Requirement30Test {

    /**
     * Confirms the district total is read and the district is bound to the query.
     */
    @Test
    void returnsDistrictPopulation() throws Exception {
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet results = mock(ResultSet.class);

        when(connection.prepareStatement(argThat(sql ->
                sql.contains("SUM(Population)")
                        && sql.contains("FROM city")
                        && sql.contains("WHERE District = ?")
        ))).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(results);
        when(results.next()).thenReturn(true);
        when(results.getLong("TotalPopulation")).thenReturn(4000000L);
        when(results.wasNull()).thenReturn(false);

        Requirement30 report = new Requirement30(connection);

        assertEquals(4000000L, report.getPopulationOfDistrict("Texas"));
        verify(statement).setString(1, "Texas");
    }

    /**
     * Confirms a district with no cities (SUM is NULL) returns -1.
     */
    @Test
    void returnsMinusOneWhenDistrictNotFound() throws Exception {
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet results = mock(ResultSet.class);

        when(connection.prepareStatement(org.mockito.ArgumentMatchers.anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(results);
        when(results.next()).thenReturn(true);
        when(results.getLong("TotalPopulation")).thenReturn(0L);
        when(results.wasNull()).thenReturn(true);

        assertEquals(-1L, new Requirement30(connection).getPopulationOfDistrict("Nowhere"));
    }

    /**
     * Confirms blank and null names are rejected without touching the database.
     */
    @Test
    void rejectsBlankDistrict() {
        Connection connection = mock(Connection.class);
        Requirement30 report = new Requirement30(connection);

        assertEquals(-1L, report.getPopulationOfDistrict("   "));
        assertEquals(-1L, report.getPopulationOfDistrict(null));
        verifyNoInteractions(connection);
    }

    /**
     * Confirms a missing connection returns -1.
     */
    @Test
    void handlesMissingConnection() {
        assertEquals(-1L, new Requirement30(null).getPopulationOfDistrict("Texas"));
    }

    /**
     * Confirms printing never throws, including for bad input.
     */
    @Test
    void printHandlesAllInputs() {
        Requirement30 report = new Requirement30(null);

        assertDoesNotThrow(() -> report.printDistrictPopulation(null));
        assertDoesNotThrow(() -> report.printDistrictPopulation("Texas"));
        assertDoesNotThrow(() -> report.printDistrictPopulation("Texas", 4000000L));
        assertDoesNotThrow(() -> report.printDistrictPopulation("Texas", -1L));
    }
}
