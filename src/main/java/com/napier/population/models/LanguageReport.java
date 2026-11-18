package com.napier.population.models;

/**
 * Stores the worldwide population information for one language.
 */
public class LanguageReport {
    private final String language;
    private final long speakers;
    private final double worldPopulationPercentage;

    /**
     * Creates a language population report.
     *
     * @param language name of the language
     * @param speakers estimated number of speakers
     * @param worldPopulationPercentage percentage of the world population
     */
    public LanguageReport(
            String language,
            long speakers,
            double worldPopulationPercentage) {
        this.language = language;
        this.speakers = speakers;
        this.worldPopulationPercentage = worldPopulationPercentage;
    }

    public String getLanguage() {
        return language;
    }

    public long getSpeakers() {
        return speakers;
    }

    public double getWorldPopulationPercentage() {
        return worldPopulationPercentage;
    }
}