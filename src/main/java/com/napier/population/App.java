package com.napier.population;

import com.napier.population.models.Country;
import com.napier.population.reports.Requirement1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class App {
    public static void main(String[] args) throws InterruptedException {
        String url = "jdbc:mysql://db:3306/world?sslMode=REQUIRED";

        // Retry while the database starts and imports its data.
        for (int attempt = 1; attempt <= 10; attempt++) {
            System.out.println("Connecting to database... attempt " + attempt);

            try (Connection con =
                         DriverManager.getConnection(url, "root", "example")) {
                System.out.println("Successfully connected to world database");

                // Confirm that Java can read the imported country records.
                try (Statement stmt = con.createStatement();
                     ResultSet results =
                             stmt.executeQuery("SELECT COUNT(*) FROM country")) {
                    if (results.next()) {
                        System.out.println(
                                "Number of countries: " + results.getInt(1));
                    }
                }

                //Requirement 1 - Gernate All Countries With Polulation Largest To Smallest.
                Requirement1 req1 = new Requirement1(con);
                List<Country> countries = req1.getAllCountriesWorld();
                req1.printCountries(countries);

                // The connection closes automatically when leaving this block.
                return;
            } catch (SQLException e) {
                System.out.println("Database attempt failed: " + e.getMessage());

                if (attempt == 10) {
                    throw new IllegalStateException(
                            "Could not complete the database connection test", e);
                }

                Thread.sleep(10000);
            }
        }
    }
}