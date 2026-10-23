package com.napier.population.reports;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class Requirement28Test {

    @Test
    void returnsPopulationForSelectedRegion() throws Exception {
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet results = mock(ResultSet.class);

        when(connection.prepareStatement(argThat(sql ->
                sql.contains("SUM(Population)") && sql.contains("WHERE Region = ?")
        ))).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(results);
        when(results.next()).thenReturn(true);
        when(results.getLong("TotalPopulation")).thenReturn(309632000L);

        Requirement28 report = new Requirement28(connection);
        long population = report.getPopulationByRegion("North America");

        assertEquals(309632000L, population);
        verify(statement).setString(1, "North America");
    }

    @Test
    void trimsRegionBeforeQuerying() throws Exception {
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet results = mock(ResultSet.class);

        when(connection.prepareStatement(argThat(sql -> sql.contains("WHERE Region = ?"))))
                .thenReturn(statement);
        when(statement.executeQuery()).thenReturn(results);
        when(results.next()).thenReturn(true);
        when(results.getLong("TotalPopulation")).thenReturn(38140000L);

        new Requirement28(connection).getPopulationByRegion(" Caribbean ");

        verify(statement).setString(1, "Caribbean");
    }

    @Test
    void handlesMissingConnectionAndInvalidRegion() {
        assertEquals(0, new Requirement28(null).getPopulationByRegion("Caribbean"));
        assertEquals(0, new Requirement28(mock(Connection.class)).getPopulationByRegion("  "));
    }
}
