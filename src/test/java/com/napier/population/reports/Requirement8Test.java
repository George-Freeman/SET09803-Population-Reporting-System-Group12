package com.napier.population.reports;

import com.napier.population.models.City;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Requirement8Test {

    static Requirement8 req8;

    @BeforeAll
    static void init() {
        // Initialise with null connection to test defensive programming
        req8 = new Requirement8(null);
    }

    @Test
    void testPrintCitiesNull() {
        assertDoesNotThrow(() -> req8.printCities(null));
    }

    @Test
    void testPrintCitiesEmpty() {
        List<City> cities = new ArrayList<>();
        assertDoesNotThrow(() -> req8.printCities(cities));
    }

    @Test
    void testPrintCitiesNormal() {
        List<City> cities = new ArrayList<>();
        City city = new City("Tokyo", "Japan", "Tokyo-to", 7980230);
        cities.add(city);
        assertDoesNotThrow(() -> req8.printCities(cities));
    }

    @Test
    void testGetCitiesInContinentNullConnection() {
        List<City> cities = req8.getCitiesInContinent("Europe");
        assertNotNull(cities);
        assertTrue(cities.isEmpty());
    }

    @Test
    void testGetCitiesInContinentNullContinent() {
        List<City> cities = req8.getCitiesInContinent(null);
        assertNotNull(cities);
        assertTrue(cities.isEmpty());
    }

    @Test
    void testGetCitiesInContinentEmptyContinent() {
        List<City> cities = req8.getCitiesInContinent("   ");
        assertNotNull(cities);
        assertTrue(cities.isEmpty());
    }
}