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
 * Tests Requirement 9: all the cities in a region, largest population first.
 */
class Requirement9Test {

    /**
     * Confirms that database rows are mapped to cities and the region is bound to the query.
     */
    @Test
    void returnsCitiesInPopulationOrder() throws Exception {
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet results = mock(ResultSet.class);

        when(connection.prepareStatement(argThat(sql ->
                sql.contains("WHERE c.Region = ?")
                        && sql.contains("ORDER BY ci.Population DESC")
        ))).thenReturn(statement);

        when(statement.executeQuery()).thenReturn(results);
        when(results.next()).thenReturn(true, true, false);
        when(results.getString("Name")).thenReturn("La Habana", "Santo Domingo de Guzmán");
        when(results.getString("Country")).thenReturn("Cuba", "Dominican Republic");
        when(results.getString("District")).thenReturn("La Habana", "Distrito Nacional");
        when(results.getInt("Population")).thenReturn(2256000, 1609966);

        Requirement9 report = new Requirement9(connection);
        List<City> cities = report.getCitiesInRegion("Caribbean");

        assertEquals(2, cities.size());
        assertEquals("La Habana", cities.get(0).getName());
        assertEquals("Cuba", cities.get(0).getCountry());
        assertEquals("La Habana", cities.get(0).getDistrict());
        assertEquals(2256000, cities.get(0).getPopulation());
        assertEquals("Santo Domingo de Guzmán", cities.get(1).getName());

        verify(statement).setString(1, "Caribbean");
    }

    /**
     * Confirms that a blank region is rejected without querying the database.
     */
    @Test
    void rejectsBlankRegion() {
        Connection connection = mock(Connection.class);
        Requirement9 report = new Requirement9(connection);

        assertTrue(report.getCitiesInRegion("   ").isEmpty());
        assertTrue(report.getCitiesInRegion(null).isEmpty());
        verifyNoInteractions(connection);
    }

    /**
     * Confirms that a missing connection returns an empty result.
     */
    @Test
    void handlesMissingConnection() {
        Requirement9 report = new Requirement9(null);

        assertTrue(report.getCitiesInRegion("Caribbean").isEmpty());
    }

    /**
     * Confirms that printing null, empty and normal lists does not throw.
     */
    @Test
    void printCitiesHandlesAllInputs() {
        Requirement9 report = new Requirement9(null);
        List<City> cities = new ArrayList<>();
        cities.add(new City("Tokyo", "Japan", "Tokyo-to", 7980230));

        assertDoesNotThrow(() -> report.printCities(null));
        assertDoesNotThrow(() -> report.printCities(new ArrayList<>()));
        assertDoesNotThrow(() -> report.printCities(cities));
    }
}
