package com.napier.population.reports;

import com.napier.population.models.CapitalCity;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

/**
 * Tests the top N capital cities report.
 */
class Requirement20Test {

    /**
     * Confirms that the report maps database results and applies the requested limit.
     */
    @Test
    void returnsTopCapitalCitiesInPopulationOrder() throws Exception {
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet results = mock(ResultSet.class);

        when(connection.prepareStatement(argThat(sql ->
                sql.contains("ORDER BY city.Population DESC")
                        && sql.contains("LIMIT ?")
        ))).thenReturn(statement);

        when(statement.executeQuery()).thenReturn(results);
        when(results.next()).thenReturn(true, false);
        when(results.getString("Name")).thenReturn("Seoul");
        when(results.getString("Country")).thenReturn("South Korea");
        when(results.getInt("Population")).thenReturn(9981619);

        Requirement20 report = new Requirement20(connection);
        List<CapitalCity> capitalCities =
                report.getTopCapitalCitiesWorld(1);

        assertEquals(1, capitalCities.size());
        assertEquals("Seoul", capitalCities.get(0).getName());
        assertEquals("South Korea", capitalCities.get(0).getCountry());
        assertEquals(9981619, capitalCities.get(0).getPopulation());

        verify(statement).setInt(1, 1);
    }

    /**
     * Confirms that zero is rejected without querying the database.
     */
    @Test
    void rejectsZeroCities() {
        Connection connection = mock(Connection.class);
        Requirement20 report = new Requirement20(connection);

        List<CapitalCity> capitalCities =
                report.getTopCapitalCitiesWorld(0);

        assertTrue(capitalCities.isEmpty());
        verifyNoInteractions(connection);
    }

    /**
     * Confirms that a missing connection returns an empty result.
     */
    @Test
    void handlesMissingConnection() {
        Requirement20 report = new Requirement20(null);

        List<CapitalCity> capitalCities =
                report.getTopCapitalCitiesWorld(5);

        assertTrue(capitalCities.isEmpty());
    }
}