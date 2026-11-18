package com.napier.population.reports;

import com.napier.population.models.CapitalCity;
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
 * Tests the top N capital cities in a region report.
 */
class Requirement22Test {

    /**
     * Confirms that the report maps database results, filters by region and applies the limit.
     */
    @Test
    void returnsTopCapitalCitiesInPopulationOrder() throws Exception {
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet results = mock(ResultSet.class);

        when(connection.prepareStatement(argThat(sql ->
                sql.contains("WHERE country.Region = ?")
                        && sql.contains("ORDER BY city.Population DESC")
                        && sql.contains("LIMIT ?")
        ))).thenReturn(statement);

        when(statement.executeQuery()).thenReturn(results);
        when(results.next()).thenReturn(true, true, false);
        when(results.getString("Name")).thenReturn("Seoul", "Jakarta");
        when(results.getString("Country")).thenReturn("South Korea", "Indonesia");
        when(results.getInt("Population")).thenReturn(9981619, 9604900);

        Requirement22 report = new Requirement22(connection);
        List<CapitalCity> capitalCities =
                report.getTopCapitalCitiesRegion("Caribbean", 2);

        assertEquals(2, capitalCities.size());
        assertEquals("Seoul", capitalCities.get(0).getName());
        assertEquals("South Korea", capitalCities.get(0).getCountry());
        assertEquals(9981619, capitalCities.get(0).getPopulation());
        assertEquals("Jakarta", capitalCities.get(1).getName());

        verify(statement).setString(1, "Caribbean");
        verify(statement).setInt(2, 2);
    }

    /**
     * Confirms that a blank region is rejected without querying the database.
     */
    @Test
    void rejectsBlankRegion() {
        Connection connection = mock(Connection.class);
        Requirement22 report = new Requirement22(connection);

        assertTrue(report.getTopCapitalCitiesRegion("   ", 5).isEmpty());
        assertTrue(report.getTopCapitalCitiesRegion(null, 5).isEmpty());
        verifyNoInteractions(connection);
    }

    /**
     * Confirms that zero or negative N is rejected without querying the database.
     */
    @Test
    void rejectsZeroOrNegativeCities() {
        Connection connection = mock(Connection.class);
        Requirement22 report = new Requirement22(connection);

        assertTrue(report.getTopCapitalCitiesRegion("Caribbean", 0).isEmpty());
        assertTrue(report.getTopCapitalCitiesRegion("Caribbean", -3).isEmpty());
        verifyNoInteractions(connection);
    }

    /**
     * Confirms that a missing connection returns an empty result.
     */
    @Test
    void handlesMissingConnection() {
        Requirement22 report = new Requirement22(null);

        assertTrue(report.getTopCapitalCitiesRegion("Caribbean", 5).isEmpty());
    }

    /**
     * Confirms that printing null, empty and normal lists does not throw.
     */
    @Test
    void printCapitalCitiesHandlesAllInputs() {
        Requirement22 report = new Requirement22(null);
        List<CapitalCity> capitalCities = new ArrayList<>();
        capitalCities.add(new CapitalCity("Seoul", "South Korea", 9981619));

        assertDoesNotThrow(() -> report.printCapitalCities(null));
        assertDoesNotThrow(() -> report.printCapitalCities(new ArrayList<>()));
        assertDoesNotThrow(() -> report.printCapitalCities(capitalCities));
    }
}
