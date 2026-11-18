package com.napier.population.models;

/**
 * Represents a capital city in a population report.
 */
public class CapitalCity {
    private final String name;
    private final String country;
    private final int population;

    /**
     * Creates a capital-city record.
     *
     * @param name capital-city name
     * @param country country containing the capital
     * @param population capital-city population
     */
    public CapitalCity(String name, String country, int population) {
        this.name = name;
        this.country = country;
        this.population = population;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public int getPopulation() {
        return population;
    }
}