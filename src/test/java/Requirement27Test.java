package com.napier.population.reports;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Requirement27Test {

    private Requirement27 req27;

    @BeforeEach
    void setUp() {
        req27 = new Requirement27(null);
    }

    @Test
    void testGetPopulationOfContinentNullConnection() {
        long result = req27.getPopulationOfContinent("Europe");
        assertEquals(-1, result);
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
        req27.printContinentPopulation(null, 1000000L);
    }

    @Test
    void testPrintContinentPopulationNegativePopulation() {
        req27.printContinentPopulation("Europe", -1);
    }

    @Test
    void testPrintContinentPopulationValid() {
        req27.printContinentPopulation("Europe", 730000000L);
    }
}