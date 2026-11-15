package com.napier.population.reports;

import com.napier.population.models.City;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for {@link Requirement19} (Capital cities in a region).
 * Tests defensive guards, input validation, and null safety when running
 * in isolation without an active database connection.
 *
 * @author Group 12
 * @version 1.0
 */
class Requirement19Test {

    /**
     * Instance of Requirement19 under test.
     */
    private Requirement19 req19;

    /**
     * Sets up the test environment before each test execution.
     * Initializes the report instance with a null database connection.
     */
    @BeforeEach
    void setUp() {
        req19 = new Requirement19(null);
    }

    /**
     * Verifies that attempting to retrieve capital cities with a null
     * database connection returns null rather than throwing an exception.
     */
    @Test
    void testGetCapitalCitiesByRegionNullConnection() {
        ArrayList<City> result = req19.getCapitalCitiesByRegion("Western Europe");
        assertNull(result);
    }

    /**
     * Verifies that passing a null region parameter returns null.
     */
    @Test
    void testGetCapitalCitiesByRegionNullRegion() {
        ArrayList<City> result = req19.getCapitalCitiesByRegion(null);
        assertNull(result);
    }

    /**
     * Verifies that passing an empty or whitespace-only region string returns null.
     */
    @Test
    void testGetCapitalCitiesByRegionEmptyRegion() {
        ArrayList<City> result = req19.getCapitalCitiesByRegion("   ");
        assertNull(result);
    }

    /**
     * Verifies that passing a null list to the print method is handled safely
     * without throwing a NullPointerException.
     */
    @Test
    void testPrintCapitalCitiesNullList() {
        assertDoesNotThrow(() -> req19.printCapitalCities(null));
    }

    /**
     * Verifies that passing an empty list to the print method is handled safely
     * without throwing an exception.
     */
    @Test
    void testPrintCapitalCitiesEmptyList() {
        ArrayList<City> emptyList = new ArrayList<>();
        assertDoesNotThrow(() -> req19.printCapitalCities(emptyList));
    }

    /**
     * Verifies that passing a list containing a null element to the print method
     * is handled gracefully without crashing.
     */
    @Test
    void testPrintCapitalCitiesListContainsNull() {
        ArrayList<City> listWithNull = new ArrayList<>();
        listWithNull.add(null);
        assertDoesNotThrow(() -> req19.printCapitalCities(listWithNull));
    }

    /**
     * Verifies that passing a populated list of valid City objects prints
     * successfully without throwing any exceptions.
     */
    @Test
    void testPrintCapitalCitiesSuccess() {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(new City("Berlin", "Germany", "Berlin", 3386667));
        cities.add(new City("Paris", "France", "Île-de-France", 2125246));
        assertDoesNotThrow(() -> req19.printCapitalCities(cities));
    }
}