package com.napier.population.reports;

import com.napier.population.models.City;
import com.napier.population.reports.Requirement9;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Requirement9Test {

    static Requirement9 req9;

    @BeforeAll
    static void init() {
        // Defensive initialisation with null connection
        req9 = new Requirement9(null);
    }

    @Test
    void testPrintCitiesNull() {
        assertDoesNotThrow(() -> req9.printCities(null));
    }

    @Test
    void testPrintCitiesEmpty() {
        List<City> cities = new ArrayList<>();
        assertDoesNotThrow(() -> req9.printCities(cities));
    }

    @Test
    void testPrintCitiesContainsNull() {
        List<City> cities = new ArrayList<>();
        cities.add(null);
        assertDoesNotThrow(() -> req9.printCities(cities));
    }

    @Test
    void testPrintCitiesNormal() {
        List<City> cities = new ArrayList<>();
        City city = new City();
        city.setName("La Habana");
        city.setCountry("Cuba");
        city.setDistrict("La Habana");
        city.setPopulation(2256000);
        cities.add(city);

        assertDoesNotThrow(() -> req9.printCities(cities));
    }

    @Test
    void testGetCitiesInRegionNullConnection() {
        List<City> cities = req9.getCitiesInRegion("Caribbean");
        assertNotNull(cities);
        assertTrue(cities.isEmpty());
    }

    @Test
    void testGetCitiesInRegionNullRegion() {
        List<City> cities = req9.getCitiesInRegion(null);
        assertNotNull(cities);
        assertTrue(cities.isEmpty());
    }

    @Test
    void testGetCitiesInRegionEmptyRegion() {
        List<City> cities = req9.getCitiesInRegion("   ");
        assertNotNull(cities);
        assertTrue(cities.isEmpty());
    }
}