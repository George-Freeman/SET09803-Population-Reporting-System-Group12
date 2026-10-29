package com.napier.population;

import com.napier.population.models.CapitalCity;
import com.napier.population.models.City;
import com.napier.population.models.Country;
import com.napier.population.models.LanguageReport;
import com.napier.population.reports.Requirement1;
import com.napier.population.reports.Requirement10;
import com.napier.population.reports.Requirement20;
import com.napier.population.reports.Requirement26;
import com.napier.population.reports.Requirement32;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the Population Reporting System.
 * Tests implemented report functionality against the real Docker world database.
 */

public class AppIntegrationTest {

    static Connection con;

    /**
     * Connect to the real world database before running integration tests.
     */

    @BeforeAll
    static void init() {
        App.connect("localhost:33060", 30000);
        con = App.getConnection();

        assertNotNull(con);

    }

    /**
     * Tests Requirement 1 against the real world database.
     */

    @Test

    void testGetAllCountriesWorld() {
        Requirement1 requirement1 = new Requirement1(con);

        List<Country> countries = requirement1.getAllCountriesWorld();

        assertNotNull(countries);
        assertFalse(countries.isEmpty());

        Country firstCountry = countries.get(0);

        assertEquals("CHN", firstCountry.getCode());
        assertEquals("China", firstCountry.getName());
        assertEquals(1277558000, firstCountry.getPopulation());

    }

    /**
     * Tests Requirement 10 by retrieving cities in Japan
     * from the real world database.
     */

    @Test
    void testGetCitiesInCountry() {
        Requirement10 requirement10 = new Requirement10(con);

        List<City> cities = requirement10.getCitiesInCountry("Japan");

        assertNotNull(cities);
        assertFalse(cities.isEmpty());

        City firstCity = cities.get(0);

        assertEquals("Tokyo", firstCity.getName());
        assertEquals("Japan", firstCity.getCountry());
        assertEquals("Tokyo-to", firstCity.getDistrict());
        assertEquals(7980230, firstCity.getPopulation());

    }

    /**
     * Tests Requirement 20 by retrieving the top three
     * populated capital cities from the real database.
     */

    @Test
    void testGetTopCapitalCitiesWorld() {
        Requirement20 requirement20 = new Requirement20(con);

        List<CapitalCity> capitalCities = requirement20.getTopCapitalCitiesWorld(3);

        assertNotNull(capitalCities);
        assertEquals(3, capitalCities.size());

        CapitalCity firstCapital = capitalCities.get(0);

        assertEquals("Seoul", firstCapital.getName());
        assertEquals("South Korea", firstCapital.getCountry());
        assertEquals(9981619, firstCapital.getPopulation());

        assertTrue(capitalCities.get(0).getPopulation() >= capitalCities.get(1).getPopulation()
        );

        assertTrue(capitalCities.get(1).getPopulation() >= capitalCities.get(2).getPopulation()

        );

    }

    /**
     * Tests Requirement 26 by retrieving the total
     * population of the world.
     */

    @Test
    void testGetWorldPopulation() {
        Requirement26 requirement26 = new Requirement26(con);

        long population = requirement26.getWorldPopulation();

        assertEquals(6078749450L, population);

    }

    /**
     * Tests Requirement 32 against the real world database.
     */

    @Test
    void testGetLanguagePopulationReport() {
        Requirement32 requirement32 = new Requirement32(con);
        List<LanguageReport> reports = requirement32.getLanguagePopulationReport();

        assertNotNull(reports);
        assertEquals(5, reports.size());

        LanguageReport firstLanguage = reports.get(0);

        assertEquals("Chinese", firstLanguage.getLanguage());
        assertTrue(firstLanguage.getSpeakers() > 0);
        assertTrue(firstLanguage.getWorldPopulationPercentage() > 0);

        // Query should return languages from greatest
        // number of speakers to smallest.
        for (int i = 0; i < reports.size() - 1; i++) {
            assertTrue(reports.get(i).getSpeakers() >= reports.get(i + 1).getSpeakers()
            );
        }
    }

    /**
     * Disconnect from the database after all integration tests.
     */

    @AfterAll
    static void cleanUp() {
        App.disconnect();
    }

}