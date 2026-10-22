package com.napier.population.models;

/**
 * Represents a population report for a geographical area.
 */
public class PopulationReport {
    private final String name;
    private final long totalPopulation;
    private final long cityPopulation;
    private final long nonCityPopulation;

    /**
     * Creates a population report record.
     *
     * @param name name of the geographical area
     * @param totalPopulation total population of the area
     * @param cityPopulation population living in cities
     * @param nonCityPopulation population not living in cities
     */
    public PopulationReport(
            String name,
            long totalPopulation,
            long cityPopulation,
            long nonCityPopulation) {
        this.name = name;
        this.totalPopulation = totalPopulation;
        this.cityPopulation = cityPopulation;
        this.nonCityPopulation = nonCityPopulation;
    }

    public String getName() {
        return name;
    }

    public long getTotalPopulation() {
        return totalPopulation;
    }

    public long getCityPopulation() {
        return cityPopulation;
    }

    public long getNonCityPopulation() {
        return nonCityPopulation;
    }

    public double getCityPopulationPercentage() {
        if (totalPopulation == 0) {
            return 0.0;
        }

        return cityPopulation * 100.0 / totalPopulation;
    }

    public double getNonCityPopulationPercentage() {
        if (totalPopulation == 0) {
            return 0.0;
        }

        return nonCityPopulation * 100.0 / totalPopulation;
    }
}