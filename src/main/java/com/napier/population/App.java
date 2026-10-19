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

        // Initial delay allowing MySQL container to initialize before the first connection attempt
        System.out.println("Waiting 10 seconds for database service to initialize...");
        Thread.sleep(10000);

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
                List<String> continents =
                        Arrays.asList("Asia", "Europe", "North America", "Africa", "Oceania", "Antarctica", "South America");
                String continent;

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
                       
                        case 8:
                            while (true) {
                                System.out.println("Enter continent (e.g. Asia, Europe, North America, Africa, Oceania, Antarctica, South America): ");
                                continent = scanner.nextLine().trim();
                                String targetContinent = continent;
                                continent = continents.stream()
                                        .filter(c -> c.equalsIgnoreCase(targetContinent))
                                        .findFirst()
                                        .orElse(null);

                                if (continent != null) {
                                    break;
                                }
                                System.out.println("Invalid continent entered. Please try again.\n");
                            }

                            Requirement8 req8 = new Requirement8(con);
                            List<City> continentCities = req8.getCitiesInContinent(continent);
                            req8.printCities(continentCities);
                            break;
                        
                        case 9:
                            System.out.println("Enter region (e.g. Caribbean, Western Europe, Middle East): ");
                            region = scanner.nextLine().trim();
                            while (region.isEmpty()) {
                                System.out.println("Region cannot be empty. Please enter a region: ");
                                region = scanner.nextLine().trim();
                            }

                            Requirement9 req9 = new Requirement9(con);
                            List<City> regionCities = req9.getCitiesInRegion(region);
                            if (regionCities.isEmpty()) {
                                System.out.println("No cities found for region: " + region);
                            } else {
                                req9.printCities(regionCities);
                            }
                            break;

                        case 10: {
                        String country10 = "";
                        while (country10.isEmpty()) {
                            System.out.println("Enter country (e.g. Japan, Brazil, United Kingdom): ");
                            country10 = scanner.nextLine().trim();
                            if (country10.isEmpty()) {
                                System.out.println("Country cannot be empty. Try again.\n");
                            }
                        }

                        Requirement10 req10 = new Requirement10(con);
                        List<City> cities10 = req10.getCitiesInCountry(country10);
                        if (cities10.isEmpty()) {
                            System.out.println("No cities found for country: " + country10);
                        } else {
                            req10.printCities(cities10);
                        }
                        break;
                        }

                        case 11: {
                        String district11 = "";
                        while (district11.isEmpty()) {
                            System.out.println("Enter district (e.g. Texas, England, Tokyo-to): ");
                            district11 = scanner.nextLine().trim();
                            if (district11.isEmpty()) {
                                System.out.println("District cannot be empty. Try again.\n");
                            }
                        }

                        Requirement11 req11 = new Requirement11(con);
                        List<City> cities11 = req11.getCitiesInDistrict(district11);
                        if (cities11.isEmpty()) {
                            System.out.println("No cities found for district: " + district11);
                        } else {
                            req11.printCities(cities11);
                        }
                        break;
                        }

                        case 12: {
                        int n12 = 0;
                        while (n12 <= 0) {
                            System.out.println("Enter the number of top populated cities to display (N > 0): ");
                            try {
                                n12 = Integer.parseInt(scanner.nextLine().trim());
                                if (n12 <= 0) {
                                    System.out.println("N must be greater than 0. Try again.\n");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid number. Try again.\n");
                            }
                        }

                        Requirement12 req12 = new Requirement12(con);
                        List<City> cities12 = req12.getTopNCitiesWorld(n12);
                        req12.printCities(cities12);
                        break;
                        }

                        case 13: {
                        String continent13 = "";
                        while (continent13.isEmpty()) {
                            System.out.println("Enter continent (e.g. Asia, Europe, North America, Africa, Oceania, Antarctica, South America): ");
                            continent13 = scanner.nextLine().trim();
                            if (continent13.isEmpty()) {
                                System.out.println("Continent cannot be empty. Try again.\n");
                            }
                        }

                        int n13 = 0;
                        while (n13 <= 0) {
                            System.out.println("Enter the number of top populated cities to display (N > 0): ");
                            try {
                                n13 = Integer.parseInt(scanner.nextLine().trim());
                                if (n13 <= 0) {
                                    System.out.println("N must be greater than 0. Try again.\n");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid number. Try again.\n");
                            }
                        }

                        Requirement13 req13 = new Requirement13(con);
                        List<City> cities13 = req13.getTopNCitiesInContinent(continent13, n13);
                        if (cities13.isEmpty()) {
                            System.out.println("No cities found for continent: " + continent13);
                        } else {
                            req13.printCities(cities13);
                        }
                        break;
                        }

                        case 14: {
                        String region14 = "";
                        while (region14.isEmpty()) {
                            System.out.println("Enter region (e.g. Caribbean, Western Europe, Middle East): ");
                            region14 = scanner.nextLine().trim();
                            if (region14.isEmpty()) {
                                System.out.println("Region cannot be empty. Try again.\n");
                            }
                        }

                        int n14 = 0;
                        while (n14 <= 0) {
                            System.out.println("Enter the number of top populated cities to display (N > 0): ");
                            try {
                                n14 = Integer.parseInt(scanner.nextLine().trim());
                                if (n14 <= 0) {
                                    System.out.println("N must be greater than 0. Try again.\n");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid number. Try again.\n");
                            }
                        }

                        Requirement14 req14 = new Requirement14(con);
                        List<City> cities14 = req14.getTopNCitiesInRegion(region14, n14);
                        if (cities14.isEmpty()) {
                            System.out.println("No cities found for region: " + region14);
                        } else {
                            req14.printCities(cities14);
                        }
                        break;
                        }

                        case 15: {
                        String country15 = "";
                        while (country15.isEmpty()) {
                            System.out.println("Enter country (e.g. Japan, Brazil, United Kingdom): ");
                            country15 = scanner.nextLine().trim();
                            if (country15.isEmpty()) {
                                System.out.println("Country cannot be empty. Try again.\n");
                            }
                        }

                        int n15 = 0;
                        while (n15 <= 0) {
                            System.out.println("Enter the number of top populated cities to display (N > 0): ");
                            try {
                                n15 = Integer.parseInt(scanner.nextLine().trim());
                                if (n15 <= 0) {
                                    System.out.println("N must be greater than 0. Try again.\n");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid number. Try again.\n");
                            }
                        }

                        Requirement15 req15 = new Requirement15(con);
                        List<City> cities15 = req15.getTopNCitiesInCountry(country15, n15);
                        if (cities15.isEmpty()) {
                            System.out.println("No cities found for country: " + country15);
                        } else {
                            req15.printCities(cities15);
                        }
                        break;
                        }

                         case 20: {
                            int numberOfCapitalCities = 0;

                            while (numberOfCapitalCities <= 0) {
                                System.out.println(
                                        "Enter the number of capital cities to display (N > 0):"
                                );

                                try {
                                    numberOfCapitalCities =
                                            Integer.parseInt(scanner.nextLine().trim());

                                    if (numberOfCapitalCities <= 0) {
                                        System.out.println("N must be greater than 0.");
                                    }
                                } catch (NumberFormatException exception) {
                                    System.out.println("Invalid number. Try again.");
                                }
                            }

                            Requirement20 requirement20 = new Requirement20(con);
                            List<CapitalCity> capitalCities =
                                    requirement20.getTopCapitalCitiesWorld(
                                            numberOfCapitalCities
                                    );

                            requirement20.printCapitalCities(capitalCities);
                            break;
                        }

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