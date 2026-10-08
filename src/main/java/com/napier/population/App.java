package com.napier.population;


//Imports all Reports and models.
import com.napier.population.models.*;
import com.napier.population.reports.*;

//Other imports.
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

                // Variable Declaration For Variables Used In More Than One Requirement;
                List<Country> regionCountries;
                String region;

                // Add In Number Input For Selecting Requirement.
                Scanner scanner = new Scanner(System.in);
                boolean running = true;
                while (running) {
                    int requirement;

                    System.out.println("\nPopulation Reporting System");
                    System.out.println("---------------------------");
                    System.out.println("Enter a requirement number between 1 and 32.");
                    System.out.println("Enter 0 to exit.");
                    System.out.print("Selection: ");

                    try {
                        requirement = Integer.parseInt(scanner.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a number.");
                        continue;
                    }

                    if (requirement == 0) {
                        System.out.println("Exiting Population Reporting System.");
                        running = false;
                        continue;
                    }

                    if (requirement < 1 || requirement > 32) {
                        System.out.println("Please enter a valid value between 1 and 32.");
                        continue;
                    }

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

                        case 3:
                            System.out.print("Enter region: ");
                            region = scanner.nextLine().trim();
                            Requirement3 req3 = new Requirement3(con);
                            regionCountries = req3.getCountriesByRegion(region);
                            if (regionCountries.isEmpty()) {
                                System.out.println("No countries found for region: " + region);
                            } else {
                                req3.printCountries(regionCountries);
                            }

                            break;

                        case 4:
                            int n = 0;
                            while (n <= 0) {
                                System.out.println("Enter the number of top populated countries to display (N > 0): ");
                                try {
                                    n = Integer.parseInt(scanner.nextLine().trim());
                                    if (n <= 0) {
                                        System.out.println("N must be greater than 0. Try again.\n");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid number. Try again.\n");
                                }
                            }

                            Requirement4 req4 = new Requirement4(con);
                            List<Country> topCountries = req4.getTopNCountriesWorld(n);
                            req4.printCountries(topCountries);
                            break;
                        case 5: {
                            List<String> validContinents = Arrays.asList(
                                    "Asia", "Europe", "North America",
                                    "Africa", "Oceania", "Antarctica",
                                    "South America");

                            String selectedContinent = null;

                            while (selectedContinent == null) {
                                System.out.println(
                                        "Enter continent (e.g. Asia, Europe, Africa):");
                                String inputContinent = scanner.nextLine().trim();

                                for (String validContinent : validContinents) {
                                    if (validContinent.equalsIgnoreCase(inputContinent)) {
                                        selectedContinent = validContinent;
                                        break;
                                    }
                                }

                                if (selectedContinent == null) {
                                    System.out.println("Invalid continent. Try again.");
                                }
                            }

                            int numberOfCountries = 0;

                            while (numberOfCountries <= 0) {
                                System.out.println(
                                        "Enter the number of countries to display (N > 0):");

                                try {
                                    numberOfCountries =
                                            Integer.parseInt(scanner.nextLine().trim());

                                    if (numberOfCountries <= 0) {
                                        System.out.println("N must be greater than 0.");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid number. Try again.");
                                }
                            }

                            Requirement5 req5 = new Requirement5(con);
                            List<Country> continentCountries =
                                    req5.getTopNCountriesByContinent(
                                            selectedContinent, numberOfCountries);
                            req5.printCountries(continentCountries);
                            break;
                        }
                        case 6:
                            System.out.println("Please enter a region (e.g. Caribbean, Western Europe, Middle East): ");
                            region = scanner.nextLine().trim();
                            while (region.isEmpty()) {
                                System.out.println("Region cannot be empty. Please enter a region: ");
                                region = scanner.nextLine().trim();
                            }

                            int n6 = 0;
                            while (n6 <= 0) {
                                System.out.println("Enter the number of top populated countries to display (N > 0): ");
                                try {
                                    n6 = Integer.parseInt(scanner.nextLine().trim());
                                    if (n6 <= 0) {
                                        System.out.println("N must be greater than 0. Try again.\n");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid number. Try again.\n");
                                }
                            }

                            Requirement6 req6 = new Requirement6(con);

                            regionCountries = req6.getTopNCountriesRegion(region, n6);
                            req6.printCountries(regionCountries);
                            break;
                        case 7:
                            Requirement7 req7 = new Requirement7(con);
                            List<City> cities = req7.getAllCitiesWorld();
                            req7.printCities(cities);
                            break;
                        default:
                            System.out.println("Requirement " + requirement + " has not been implemented yet.");
                            break;

                    }
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