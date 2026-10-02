package com.napier.population;

import com.napier.population.models.Country;
import com.napier.population.reports.Requirement1;
import com.napier.population.reports.Requirement2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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

                // Add In Number Input For Selecting Requirement.
                Scanner scanner = new Scanner(System.in);
                int requirement;

                do {
                    System.out.println("Please Enter Required Issue Between 1 and 32: ");
                    try {
                        requirement = Integer.parseInt(scanner.nextLine().trim());
                        if (requirement < 1 || requirement > 32) {
                            System.out.println("Please Enter Valid Value Between 1 and 32.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                        requirement = 0; // keeps loop running
                    }
                } while (requirement < 1 || requirement > 32);

            // Switch Based On Requirement
                switch (requirement) {
                    case 1:
                        Requirement1 req1 = new Requirement1(con);
                        List<Country> countries = req1.getAllCountriesWorld();
                        req1.printCountries(countries);
                        break;

                    case 2:
                        List<String> continents =
                                Arrays.asList("Asia", "Europe", "North America", "Africa", "Oceania", "Antarctica", "South America");
                        String continent;

                        while (true) {
                            System.out.println("Enter continent: ");
                            continent = scanner.nextLine().trim();
                            String finalC = continent;
                            continent = continents.stream().filter(c -> c.equalsIgnoreCase(finalC)).findFirst().orElse(null);

                            if (continent != null) break;
                            System.out.println("Invalid continent. Try again.\n");
                        }

                        Requirement2 req2 = new Requirement2(con);
                        req2.printCountries(req2.getCountriesInContinent(continent));
                        break;
                }


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