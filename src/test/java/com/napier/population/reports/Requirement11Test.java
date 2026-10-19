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
 * Tests Requirement 11: all the cities in a district organised by largest population to smallest.
 */
class Requirement11Test {

    /**
     * Confirms that database rows are mapped to cities and the query is built as expected.
     */
    @Test
    void returnsCitiesInPopulationOrder() throws Exception {
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet results = mock(ResultSet.class);

        when(connection.prepareStatement(argThat(sql ->
                sql.contains("ci.District = ?")
                        && sql.contains("ORDER BY ci.Population DESC")
        ))).thenReturn(statement);

        when(statement.executeQuery()).thenReturn(results);
        when(results.next()).thenReturn(true, true, false);
        when(results.getString("Name")).thenReturn("Tokyo", "Osaka");
        when(results.getString("Country")).thenReturn("Japan", "Japan");
        when(results.getString("District")).thenReturn("Tokyo-to", "Osaka");
        when(results.getInt("Population")).thenReturn(7980230, 2595674);

        Requirement11 report = new Requirement11(connection);
        List<City> cities = report.getCitiesInDistrict("Tokyo-to");

        assertEquals(2, cities.size());
        assertEquals("Tokyo", cities.get(0).getName());
        assertEquals("Japan", cities.get(0).getCountry());
        assertEquals("Tokyo-to", cities.get(0).getDistrict());
        assertEquals(7980230, cities.get(0).getPopulation());
        assertEquals("Osaka", cities.get(1).getName());

        verify(statement).setString(1, "Tokyo-to");
    }

    /**
     * Confirms that a blank district is rejected without querying the database.
     */
    @Test
    void rejectsBlankDistrict() {
        Connection connection = mock(Connection.class);
        Requirement11 report = new Requirement11(connection);

        assertTrue(report.getCitiesInDistrict("   ").isEmpty());
        assertTrue(report.getCitiesInDistrict(null).isEmpty());
        verifyNoInteractions(connection);
    }

    /**
     * Confirms that a missing connection returns an empty result.
     */
    @Test
    void handlesMissingConnection() {
        Requirement11 report = new Requirement11(null);

        assertTrue(report.getCitiesInDistrict("Tokyo-to").isEmpty());
    }

    /**
     * Confirms that printing null, empty and normal lists does not throw.
     */
    @Test
    void printCitiesHandlesAllInputs() {
        Requirement11 report = new Requirement11(null);
        List<City> cities = new ArrayList<>();
        cities.add(new City("Tokyo", "Japan", "Tokyo-to", 7980230));

        assertDoesNotThrow(() -> report.printCities(null));
        assertDoesNotThrow(() -> report.printCities(new ArrayList<>()));
        assertDoesNotThrow(() -> report.printCities(cities));
    }
}
