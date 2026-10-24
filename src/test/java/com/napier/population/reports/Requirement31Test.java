package com.napier.population.reports;

import com.napier.population.models.City;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
 * Tests Requirement 31: the population of a city.
 */
class Requirement31Test {

    /**
     * Confirms every matching city is returned, largest first, with the name bound to the query.
     */
    @Test
    void returnsAllCitiesWithThatName() throws Exception {
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet results = mock(ResultSet.class);

        when(connection.prepareStatement(argThat(sql ->
                sql.contains("WHERE ci.Name = ?")
                        && sql.contains("ORDER BY ci.Population DESC")
        ))).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(results);
        when(results.next()).thenReturn(true, true, false);
        when(results.getString("Name")).thenReturn("Valencia", "Valencia");
        when(results.getString("Country")).thenReturn("Venezuela", "Spain");
        when(results.getString("District")).thenReturn("Carabobo", "Valencia");
        when(results.getInt("Population")).thenReturn(1385000, 739014);

        Requirement31 report = new Requirement31(connection);
        List<City> cities = report.getCitiesByName("Valencia");

        assertEquals(2, cities.size());
        assertEquals("Venezuela", cities.get(0).getCountry());
        assertEquals(1385000, cities.get(0).getPopulation());
        assertEquals("Spain", cities.get(1).getCountry());
        verify(statement).setString(1, "Valencia");
    }

    /**
     * Confirms blank and null names are rejected without touching the database.
     */
    @Test
    void rejectsBlankCity() {
        Connection connection = mock(Connection.class);
        Requirement31 report = new Requirement31(connection);

        assertTrue(report.getCitiesByName("   ").isEmpty());
        assertTrue(report.getCitiesByName(null).isEmpty());
        verifyNoInteractions(connection);
    }

    /**
     * Confirms a missing connection returns an empty result.
     */
    @Test
    void handlesMissingConnection() {
        assertTrue(new Requirement31(null).getCitiesByName("Tokyo").isEmpty());
    }

    /**
     * Confirms printing null, empty and normal lists does not throw.
     */
    @Test
    void printHandlesAllInputs() {
        Requirement31 report = new Requirement31(null);
        List<City> cities = new ArrayList<>();
        cities.add(new City("Tokyo", "Japan", "Tokyo-to", 7980230));

        assertDoesNotThrow(() -> report.printCityPopulation(null));
        assertDoesNotThrow(() -> report.printCityPopulation(new ArrayList<>()));
        assertDoesNotThrow(() -> report.printCityPopulation(cities));
    }
}
