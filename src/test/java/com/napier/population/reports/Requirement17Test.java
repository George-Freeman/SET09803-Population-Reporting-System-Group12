package com.napier.population.reports;

import com.napier.population.models.CapitalCity;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

/**
 * Tests the report of all capital cities in the world.
 */
class Requirement17Test {

    /**
     * Confirms that database results are mapped in population order.
     */
    @Test
    void returnsAllCapitalCitiesInPopulationOrder() throws Exception {
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet results = mock(ResultSet.class);

        when(connection.prepareStatement(argThat(sql ->
                sql.contains("JOIN city ON country.Capital = city.ID")
                        && sql.contains("ORDER BY city.Population DESC")
                        && !sql.contains("LIMIT")
        ))).thenReturn(statement);

        when(statement.executeQuery()).thenReturn(results);
        when(results.next()).thenReturn(true, true, false);
        when(results.getString("Name"))
                .thenReturn("Seoul", "Jakarta");
        when(results.getString("Country"))
                .thenReturn("South Korea", "Indonesia");
        when(results.getInt("Population"))
                .thenReturn(9981619, 9604900);

        Requirement17 report = new Requirement17(connection);
        List<CapitalCity> capitalCities =
                report.getCapitalCitiesWorld();

        assertEquals(2, capitalCities.size());
        assertEquals("Seoul", capitalCities.get(0).getName());
        assertEquals("South Korea", capitalCities.get(0).getCountry());
        assertEquals(9981619, capitalCities.get(0).getPopulation());

        assertEquals("Jakarta", capitalCities.get(1).getName());
        assertEquals("Indonesia", capitalCities.get(1).getCountry());
        assertEquals(9604900, capitalCities.get(1).getPopulation());

        verify(statement).executeQuery();
    }

    /**
     * Confirms that a missing connection returns an empty result.
     */
    @Test
    void handlesMissingConnection() {
        Requirement17 report = new Requirement17(null);

        List<CapitalCity> capitalCities =
                report.getCapitalCitiesWorld();

        assertTrue(capitalCities.isEmpty());
    }

    /**
     * Confirms that a database error is handled without crashing.
     */
    @Test
    void handlesDatabaseError() throws Exception {
        Connection connection = mock(Connection.class);

        when(connection.prepareStatement(argThat(sql ->
                sql.contains("ORDER BY city.Population DESC")
        ))).thenThrow(new SQLException("Database unavailable"));

        Requirement17 report = new Requirement17(connection);

        List<CapitalCity> capitalCities =
                report.getCapitalCitiesWorld();

        assertTrue(capitalCities.isEmpty());
    }

    /**
     * Confirms that null and empty reports are handled safely.
     */
    @Test
    void handlesMissingReportData() {
        Connection connection = mock(Connection.class);
        Requirement17 report = new Requirement17(connection);

        assertDoesNotThrow(() ->
                report.printCapitalCities(null));

        assertDoesNotThrow(() ->
                report.printCapitalCities(new ArrayList<>()));

        verifyNoInteractions(connection);
    }

    /**
     * Confirms that a populated report can be displayed safely.
     */
    @Test
    void printsCapitalCities() {
        Requirement17 report = new Requirement17(null);

        List<CapitalCity> capitalCities = List.of(
                new CapitalCity(
                        "Seoul",
                        "South Korea",
                        9981619
                )
        );

        assertDoesNotThrow(() ->
                report.printCapitalCities(capitalCities));
    }
}