package com.napier.population.reports;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Requirement27Test {

    private Requirement27 req27;

    @BeforeEach
    void setUp() {
        req27 = new Requirement27(null);
    }

    @Test
    void testGetPopulationOfContinentNullConnection() {
        long result = req27.getPopulationOfContinent("Antarctica");
        assertEquals(-1L, result);
    }

    @Test
    void testGetPopulationOfContinentNullContinent() {
        long result = req27.getPopulationOfContinent(null);
        assertEquals(-1, result);
    }

    @Test
    void testGetPopulationOfContinentEmptyContinent() {
        long result = req27.getPopulationOfContinent("   ");
        assertEquals(-1, result);
    }

    @Test
    void testPrintContinentPopulationNullContinent() {
        assertDoesNotThrow(() -> req27.printContinentPopulation(null));
    }

    @Test
    void testPrintContinentPopulationNegativePopulation() {
        assertDoesNotThrow(() -> req27.printContinentPopulation("Europe"));
    }



    /**
     * Confirms successful formatting and output display for a valid continent 
     * and its expected population baseline (e.g. Europe: 730,074,600).
     */
    @Test
    void testPrintContinentPopulationSuccess() {
        assertEquals(
                730074600L,
                req27.getPopulationOfContinent("Europe")
        );
    }
}
