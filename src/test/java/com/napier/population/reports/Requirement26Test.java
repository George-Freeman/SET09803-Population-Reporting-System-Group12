package com.napier.population.reports;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class Requirement26Test {

    @Test
    void returnsWorldPopulationFromDatabase() throws Exception {
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet results = mock(ResultSet.class);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(results);
        when(results.next()).thenReturn(true);
        when(results.getLong("population")).thenReturn(6078749450L);

        assertEquals(6078749450L, new Requirement26(connection).getWorldPopulation());
    }

    @Test
    void returnsZeroForMissingConnectionOrNoRows() throws Exception {
        assertEquals(0L, new Requirement26(null).getWorldPopulation());

        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet results = mock(ResultSet.class);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(results);
        when(results.next()).thenReturn(false);

        assertEquals(0L, new Requirement26(connection).getWorldPopulation());
    }

    @Test
    void returnsZeroWhenTheDatabaseQueryFails() throws Exception {
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("offline"));

        assertEquals(0L, new Requirement26(connection).getWorldPopulation());
    }
}
