package com.napier.population.reports;

import com.napier.population.models.PopulationReport;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the population report by region.
 */
class Requirement24Test {

    @Test
    void returnsRegionReportAndCalculatesPopulationSplit() {
        AtomicInteger resultsNextCall = new AtomicInteger();
        AtomicReference<String> query = new AtomicReference<>();
        ResultSet results = (ResultSet) Proxy.newProxyInstance(
                ResultSet.class.getClassLoader(),
                new Class<?>[]{ResultSet.class},
                (proxy, method, args) -> switch (method.getName()) {
                    case "next" -> resultsNextCall.getAndIncrement() == 0;
                    case "getString" -> "Caribbean";
                    case "getLong" -> "TotalPopulation".equals(args[0])
                            ? 39900000L : 15000000L;
                    default -> null;
                }
        );
        PreparedStatement statement = (PreparedStatement) Proxy.newProxyInstance(
                PreparedStatement.class.getClassLoader(),
                new Class<?>[]{PreparedStatement.class},
                (proxy, method, args) -> "executeQuery".equals(method.getName())
                        ? results : null
        );
        Connection connection = (Connection) Proxy.newProxyInstance(
                Connection.class.getClassLoader(),
                new Class<?>[]{Connection.class},
                (proxy, method, args) -> {
                    if ("prepareStatement".equals(method.getName())) {
                        query.set((String) args[0]);
                        return statement;
                    }
                    return null;
                }
        );

        Requirement24 report = new Requirement24(connection);
        List<PopulationReport> reports = report.getPopulationByRegion();

        assertTrue(query.get().contains("GROUP BY Region"));
        assertTrue(query.get().contains("GROUP BY country.Region"));
        assertEquals(1, reports.size());
        PopulationReport caribbean = reports.get(0);
        assertEquals("Caribbean", caribbean.getName());
        assertEquals(39900000L, caribbean.getTotalPopulation());
        assertEquals(15000000L, caribbean.getCityPopulation());
        assertEquals(24900000L, caribbean.getNonCityPopulation());
        assertEquals(37.59, caribbean.getCityPopulationPercentage(), 0.01);
        assertEquals(62.41, caribbean.getNonCityPopulationPercentage(), 0.01);
    }

    @Test
    void handlesMissingConnection() {
        Requirement24 report = new Requirement24(null);

        assertTrue(report.getPopulationByRegion().isEmpty());
    }

    @Test
    void handlesEmptyOutput() {
        Requirement24 report = new Requirement24(null);

        assertDoesNotThrow(() -> report.printPopulationReports(List.of()));
    }
}
