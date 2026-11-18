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
 * Tests Requirement 29: the population of a country.
 */
class Requirement29Test {

    /**
     * Confirms the population is read from the result and the country is bound to the query.
     */
    @Test
    void returnsCountryPopulation() throws Exception {
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet results = mock(ResultSet.class);

        when(connection.prepareStatement(argThat(sql ->
                sql.contains("FROM country") && sql.contains("WHERE Name = ?")
        ))).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(results);
        when(results.next()).thenReturn(true);
        when(results.getLong("Population")).thenReturn(126714000L);

        Requirement29 report = new Requirement29(connection);

        assertEquals(126714000L, report.getPopulationOfCountry("Japan"));
        verify(statement).setString(1, "Japan");
    }

    /**
     * Confirms an unknown country (no row) returns -1.
     */
    @Test
    void returnsMinusOneWhenCountryNotFound() throws Exception {
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet results = mock(ResultSet.class);

        when(connection.prepareStatement(org.mockito.ArgumentMatchers.anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(results);
        when(results.next()).thenReturn(false);

        assertEquals(-1L, new Requirement29(connection).getPopulationOfCountry("Atlantis"));
    }

    /**
     * Confirms blank and null names are rejected without touching the database.
     */
    @Test
    void rejectsBlankCountry() {
        Connection connection = mock(Connection.class);
        Requirement29 report = new Requirement29(connection);

        assertEquals(-1L, report.getPopulationOfCountry("   "));
        assertEquals(-1L, report.getPopulationOfCountry(null));
        verifyNoInteractions(connection);
    }

    /**
     * Confirms a missing connection returns -1.
     */
    @Test
    void handlesMissingConnection() {
        assertEquals(-1L, new Requirement29(null).getPopulationOfCountry("Japan"));
    }

    /**
     * Confirms printing never throws, including for bad input.
     */
    @Test
    void printHandlesAllInputs() {
        Requirement29 report = new Requirement29(null);

        assertDoesNotThrow(() -> report.printCountryPopulation(null));
        assertDoesNotThrow(() -> report.printCountryPopulation("Japan"));
        assertDoesNotThrow(() -> report.printCountryPopulation("Japan", 126714000L));
        assertDoesNotThrow(() -> report.printCountryPopulation("Japan", -1L));
    }
}
