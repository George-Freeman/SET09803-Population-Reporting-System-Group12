package com.napier.population.reports;

import com.napier.population.models.City;
import com.napier.population.models.Country;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class Requirement1To7Test {

    @Test
    void requirement1MapsRowsAndHandlesNoConnectionAndSqlFailure() throws Exception {
        Connection connection = mock(Connection.class);
        Statement statement = mock(Statement.class);
        ResultSet results = mock(ResultSet.class);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(results);
        when(results.next()).thenReturn(true, false);
        stubCountry(results, null);

        List<Country> countries = new Requirement1(connection).getAllCountriesWorld();

        assertEquals(1, countries.size());
        assertEquals("GBR", countries.get(0).getCode());
        assertEquals("N/A", countries.get(0).getCapital());
        assertTrue(new Requirement1(null).getAllCountriesWorld().isEmpty());

        Connection brokenConnection = mock(Connection.class);
        when(brokenConnection.createStatement()).thenThrow(new SQLException("offline"));
        assertTrue(new Requirement1(brokenConnection).getAllCountriesWorld().isEmpty());
    }

    @Test
    void requirement2MapsCountriesAndRejectsInvalidContinent() throws Exception {
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet results = countryResult("London");
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(results);

        List<Country> countries = new Requirement2(connection).getCountriesInContinent("Europe");

        assertCountry(countries.get(0), "London");
        verify(statement).setString(1, "Europe");
        Connection untouched = mock(Connection.class);
        Requirement2 invalid = new Requirement2(untouched);
        assertTrue(invalid.getCountriesInContinent(null).isEmpty());
        assertTrue(invalid.getCountriesInContinent("  ").isEmpty());
        verifyNoInteractions(untouched);
    }

    @Test
    void requirement3MapsCountriesAndHandlesMissingCapital() throws Exception {
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        ResultSet results = countryResult(null);
        when(statement.executeQuery()).thenReturn(results);

        List<Country> countries = new Requirement3(connection).getCountriesByRegion("Caribbean");

        assertCountry(countries.get(0), "N/A");
        verify(statement).setString(1, "Caribbean");
        assertTrue(new Requirement3(null).getCountriesByRegion("Caribbean").isEmpty());
        assertTrue(new Requirement3(connection).getCountriesByRegion(" ").isEmpty());
    }

    @Test
    void requirement4MapsTopCountriesAndRejectsInvalidLimit() throws Exception {
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        ResultSet results = countryResult(null);
        when(statement.executeQuery()).thenReturn(results);

        List<Country> countries = new Requirement4(connection).getTopNCountriesWorld(2);

        assertCountry(countries.get(0), "N/A");
        verify(statement).setInt(1, 2);
        assertTrue(new Requirement4(null).getTopNCountriesWorld(2).isEmpty());
        assertTrue(new Requirement4(connection).getTopNCountriesWorld(0).isEmpty());
        assertTrue(new Requirement4(connection).getTopNCountriesWorld(-1).isEmpty());
    }

    @Test
    void requirement5TrimsContinentAndBindsLimit() throws Exception {
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        ResultSet results = countryResult("London");
        when(statement.executeQuery()).thenReturn(results);

        List<Country> countries = new Requirement5(connection).getTopNCountriesByContinent(" Europe ", 1);

        assertCountry(countries.get(0), "London");
        verify(statement).setString(1, "Europe");
        verify(statement).setInt(2, 1);
        assertTrue(new Requirement5(null).getTopNCountriesByContinent("Europe", 1).isEmpty());
        assertTrue(new Requirement5(connection).getTopNCountriesByContinent(null, 1).isEmpty());
        assertTrue(new Requirement5(connection).getTopNCountriesByContinent("Europe", 0).isEmpty());
    }

    @Test
    void requirement6MapsTopCountriesAndRejectsInvalidRegionOrLimit() throws Exception {
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        ResultSet results = countryResult("Kingston");
        when(statement.executeQuery()).thenReturn(results);

        List<Country> countries = new Requirement6(connection).getTopNCountriesRegion("Caribbean", 3);

        assertCountry(countries.get(0), "Kingston");
        verify(statement).setString(1, "Caribbean");
        verify(statement).setInt(2, 3);
        assertTrue(new Requirement6(null).getTopNCountriesRegion("Caribbean", 3).isEmpty());
        assertTrue(new Requirement6(connection).getTopNCountriesRegion(null, 3).isEmpty());
        assertTrue(new Requirement6(connection).getTopNCountriesRegion("Caribbean", 0).isEmpty());
    }

    @Test
    void requirement7MapsCitiesAndHandlesNullConnectionAndSqlFailure() throws Exception {
        Connection connection = mock(Connection.class);
        Statement statement = mock(Statement.class);
        ResultSet results = mock(ResultSet.class);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(results);
        when(results.next()).thenReturn(true, false);
        when(results.getString("Name")).thenReturn("Tokyo");
        when(results.getString("Country")).thenReturn("Japan");
        when(results.getString("District")).thenReturn("Tokyo-to");
        when(results.getInt("Population")).thenReturn(7980230);

        List<City> cities = new Requirement7(connection).getAllCitiesWorld();

        assertEquals(1, cities.size());
        assertEquals("Tokyo", cities.get(0).getName());
        assertEquals("Japan", cities.get(0).getCountry());
        assertEquals("Tokyo-to", cities.get(0).getDistrict());
        assertEquals(7980230, cities.get(0).getPopulation());
        assertTrue(new Requirement7(null).getAllCitiesWorld().isEmpty());

        Connection brokenConnection = mock(Connection.class);
        when(brokenConnection.createStatement()).thenThrow(new SQLException("offline"));
        IllegalStateException error = assertThrows(
                IllegalStateException.class,
                () -> new Requirement7(brokenConnection).getAllCitiesWorld());
        assertEquals("Failed to fetch city details", error.getMessage());
        assertInstanceOf(SQLException.class, error.getCause());
    }

    @Test
    void countryAndCityPrintersHandleNullAndEmptyLists() {
        assertDoesNotThrow(() -> new Requirement1(null).printCountries(null));
        assertDoesNotThrow(() -> new Requirement2(null).printCountries(List.of()));
        assertDoesNotThrow(() -> new Requirement3(null).printCountries(null));
        assertDoesNotThrow(() -> new Requirement4(null).printCountries(List.of()));
        assertDoesNotThrow(() -> new Requirement5(null).printCountries(null));
        assertDoesNotThrow(() -> new Requirement6(null).printCountries(List.of()));
        assertDoesNotThrow(() -> new Requirement7(null).printCities(null));
        assertDoesNotThrow(() -> new Requirement7(null).printCities(List.of()));
    }

    private static ResultSet countryResult(String capital) throws SQLException {
        ResultSet results = mock(ResultSet.class);
        when(results.next()).thenReturn(true, false);
        stubCountry(results, capital);
        return results;
    }

    private static void stubCountry(ResultSet results, String capital) throws SQLException {
        when(results.getString("Code")).thenReturn("GBR");
        when(results.getString("Name")).thenReturn("United Kingdom");
        when(results.getString("Continent")).thenReturn("Europe");
        when(results.getString("Region")).thenReturn("Western Europe");
        when(results.getInt("Population")).thenReturn(59623400);
        when(results.getString("Capital")).thenReturn(capital);
    }

    private static void assertCountry(Country country, String capital) {
        assertEquals("GBR", country.getCode());
        assertEquals("United Kingdom", country.getName());
        assertEquals("Europe", country.getContinent());
        assertEquals("Western Europe", country.getRegion());
        assertEquals(59623400, country.getPopulation());
        assertEquals(capital, country.getCapital());
    }
}
