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
 * Tests the top N capital cities in a continent report
 */

public class Requirement21Test {
    /**
     * confirms that the report maps database results, filters by continent
     * and applies the requested limit
     */

    @Test
    void returnsTopCapitalCitiesInContinentInPopulationOrder() throws Exception {
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet results = mock(ResultSet.class);

        when(connection.prepareStatement(argThat(sql ->
                sql.contains("WHERE c.Continent = ?")
                        && sql.contains("ORDER BY ci.Population DESC")
                        && sql.contains("LIMIT ?")
        ))).thenReturn(statement);

        when(statement.executeQuery()).thenReturn(results);
        when(results.next()).thenReturn(true, false);
        when(results.getString("Name")).thenReturn("London");
        when(results.getString("Country")).thenReturn("United Kingdom");
        when(results.getInt("Population")).thenReturn(7285000);

        Requirement21 report = new Requirement21(connection);

        List<CapitalCity> capitalCities = report.getTopNCapitalCitiesByContinent("Europe", 1);

        assertEquals(1, capitalCities.size());
        assertEquals("London", capitalCities.get(0).getName());
        assertEquals("United Kingdom", capitalCities.get(0).getCountry());
        assertEquals(7285000, capitalCities.get(0).getPopulation());

        verify(statement).setString(1, "Europe");
        verify(statement).setInt(2, 1);

    }

    /**
     * Confirms that zero is rejected without querying the database.
     */

    @Test
    void rejectsZeroCities() {
        Connection connection = mock(Connection.class);
        Requirement21 report = new Requirement21(connection);

        List<CapitalCity> capitalCities = report.getTopNCapitalCitiesByContinent("Europe", 0);

        assertTrue(capitalCities.isEmpty());
        verifyNoInteractions(connection);
    }

    /**
     * Confirms that an invalid continent is rejected without querying the database.
     */

    @Test
    void rejectsEmptyContinent() {
        Connection connection = mock(Connection.class);
        Requirement21 report = new Requirement21(connection);

        List<CapitalCity> capitalCities = report.getTopNCapitalCitiesByContinent("", 5);

        assertTrue(capitalCities.isEmpty());
        verifyNoInteractions(connection);
    }

    /**
     * Confirms that a missing connection returns an empty result.
     */

    @Test
    void handlesMissingConnection() {
        Requirement21 report = new Requirement21(null);

        List<CapitalCity> capitalCities = report.getTopNCapitalCitiesByContinent("Europe", 5);

        assertTrue(capitalCities.isEmpty());
    }
}

