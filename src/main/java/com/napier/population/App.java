package com.napier.population;

// Imports all Reports and models
import com.napier.population.models.*;
import com.napier.population.reports.*;

// Other imports
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class App {

    /**
     * Connection to the MySQL database.
     */
    private static Connection con = null;

    /**
     * Connect to the MySQL database.
     *
     * @param location Database host and port (e.g., "localhost:33060" or "db:3306")
     * @param delay    Delay in milliseconds before/between connection attempts
     */
    public static void connect(String location, int delay) {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        String url = "jdbc:mysql://" + location + "/world?allowPublicKeyRetrieval=true&useSSL=false";
        System.out.println("Connecting to database host/port: " + location + " with a delay of " + delay + " ms.");

        // Initial delay allowing MySQL container to initialize before the first connection attempt
        if (delay > 0) {
            System.out.println("Waiting " + delay + " ms for database service to initialize...");
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }

        int retries = 10;
        // Retry while the database starts and imports its data.
        for (int attempt = 1; attempt <= retries; attempt++) {
            System.out.println("Connecting to database... attempt " + attempt);
            try {
                con = DriverManager.getConnection(url, "root", "example");
                System.out.println("Successfully connected to world database");

                // Confirm that Java can read the imported country records.
                try (Statement stmt = con.createStatement();
                     ResultSet results = stmt.executeQuery("SELECT COUNT(*) FROM country")) {
                    if (results.next()) {
                        System.out.println("Number of countries: " + results.getInt(1));
                    }
                }
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + attempt);
                System.out.println(sqle.getMessage());
            }

            // Short pause between retry attempts if not yet connected
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public static void disconnect() {
        if (con != null) {
            try {
                con.close();
                System.out.println("Database connection closed.");
            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }

    public static void main(String[] args) {
        // Connect to database based on CLI / Docker arguments according to Lab 7
        if (args.length < 1) {
            // Local execution in IntelliJ connecting to Docker MySQL exposed on port 33060
            connect("localhost:33060", 2500);
        } else if (args.length == 1) {
            // Location provided with default 0 delay
            connect(args[0], 0);
        } else {
            // Full location and delay supplied (e.g. from docker compose: "db:3306" and "10000")
            connect(args[0], Integer.parseInt(args[1]));
        }

        if (con == null) {
            System.out.println("Could not establish a database connection. Exiting application.");
            return;
        }

        // Variable Declaration For Variables Used In More Than One Requirement
        List<Country> regionCountries;
        String region;
        List<String> continents =
                Arrays.asList("Asia", "Europe", "North America", "Africa", "Oceania", "Antarctica", "South America");
        String continent;

        // Number Input For Selecting Requirement
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
                        System.out.println("Enter continent (e.g. Asia, Europe, Africa):");
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
                        System.out.println("Enter the number of countries to display (N > 0):");
                        try {
                            numberOfCountries = Integer.parseInt(scanner.nextLine().trim());
                            if (numberOfCountries <= 0) {
                                System.out.println("N must be greater than 0.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid number. Try again.");
                        }
                    }

                    Requirement5 req5 = new Requirement5(con);
                    List<Country> continentCountries = req5.getTopNCountriesByContinent(selectedContinent, numberOfCountries);
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

                case 16: {
                    String district16 = "";
                    while (district16.isEmpty()) {
                        System.out.println("Enter district (e.g. Texas, England, Tokyo-to): ");
                        district16 = scanner.nextLine().trim();
                        if (district16.isEmpty()) {
                            System.out.println("District cannot be empty. Try again.\n");
                        }
                    }

                    int n16 = 0;
                    while (n16 <= 0) {
                        System.out.println("Enter the number of top populated cities to display (N > 0): ");
                        try {
                            n16 = Integer.parseInt(scanner.nextLine().trim());
                            if (n16 <= 0) {
                                System.out.println("N must be greater than 0. Try again.\n");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid number. Try again.\n");
                        }
                    }

                    Requirement16 req16 = new Requirement16(con);
                    List<City> cities16 = req16.getTopNCitiesInDistrict(district16, n16);
                    if (cities16.isEmpty()) {
                        System.out.println("No cities found for district: " + district16);
                    } else {
                        req16.printCities(cities16);
                    }
                    break;
                }

                case 20: {
                    int numberOfCapitalCities = 0;
                    while (numberOfCapitalCities <= 0) {
                        System.out.println("Enter the number of capital cities to display (N > 0):");
                        try {
                            numberOfCapitalCities = Integer.parseInt(scanner.nextLine().trim());
                            if (numberOfCapitalCities <= 0) {
                                System.out.println("N must be greater than 0.");
                            }
                        } catch (NumberFormatException exception) {
                            System.out.println("Invalid number. Try again.");
                        }
                    }

                    Requirement20 requirement20 = new Requirement20(con);
                    List<CapitalCity> capitalCities = requirement20.getTopCapitalCitiesWorld(numberOfCapitalCities);
                    requirement20.printCapitalCities(capitalCities);
                    break;
                }

                case 21: {
                    List<String> continents21 = Arrays.asList("Asia", "Europe", "North America", "Africa", "Oceania", "Antarctica", "South America");
                    String continent21;

                    while (true) {
                        System.out.println("Enter continent: ");
                        continent21 = scanner.nextLine().trim();
                        String finalContinent = continent21;
                        continent21 = continents21.stream().filter(c -> c.equalsIgnoreCase(finalContinent)).findFirst().orElse(null);

                        if (continent21 != null) {
                            break;
                        }
                        System.out.println("Invalid continent. Try again \n");
                    }

                    int n21 = 0;
                    while (n21 <= 0) {
                        System.out.println("Enter the number of top populated capital cities to display (N > 0):");
                        try {
                            n21 = Integer.parseInt(scanner.nextLine().trim());
                            if (n21 <= 0) {
                                System.out.println("N must be greater than 0. Try again \n");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid number. Try again \n");
                        }
                    }

                    Requirement21 req21 = new Requirement21(con);
                    List<CapitalCity> capitalCities21 = req21.getTopNCapitalCitiesByContinent(continent21, n21);
                    req21.printCapitalCities(capitalCities21);
                    break;
                }
                case 22: {

                    String region22 = "";

                    while (region22.isEmpty()) {
                        System.out.println("Enter region (e.g. Caribbean, Western Europe, Middle East):");

                        region22 = scanner.nextLine().trim();

                        if (region22.isEmpty()) {
                            System.out.println("Region cannot be empty. Try again.");

                        }

                    }

                    int numberOfCapitalCities22 = 0;

                    while (numberOfCapitalCities22 <= 0) {
                        System.out.println("Enter the number of capital cities to display (N > 0):");

                        try {
                            numberOfCapitalCities22 = Integer.parseInt(scanner.nextLine().trim());

                            if (numberOfCapitalCities22 <= 0) {
                                System.out.println("N must be greater than 0.");

                            }

                        } catch (NumberFormatException exception) {
                            System.out.println("Invalid number. Try again.");

                        }

                    }

                    Requirement22 requirement22 = new Requirement22(con);
                    List<CapitalCity> capitalCities22 = requirement22.getTopCapitalCitiesRegion(region22, numberOfCapitalCities22);

                    if (capitalCities22.isEmpty()) {
                        System.out.println("No capital cities found for region: " + region22);

                    } else {
                        requirement22.printCapitalCities(capitalCities22);
                    }

                    break;
                }

                case 23: {
                    Requirement23 requirement23 = new Requirement23(con);
                    List<PopulationReport> populationReports = requirement23.getPopulationByContinent();
                    requirement23.printPopulationReports(populationReports);
                    break;
                }


                case 27:
                    List<String> validContinents27 = Arrays.asList(
                            "Asia", "Europe", "North America",
                            "Africa", "Oceania", "Antarctica",
                            "South America");

                    String selectedContinent27 = null;

                    while (selectedContinent27 == null) {
                        System.out.println("Enter continent (e.g. Asia, Europe, Africa, North America, South America, Oceania, Antarctica):");
                        String input = scanner.nextLine().trim();

                        for (String c : validContinents27) {
                            if (c.equalsIgnoreCase(input)) {
                                selectedContinent27 = c;
                                break;
                            }
                        }

                        if (selectedContinent27 == null) {
                            System.out.println("Invalid continent. Try again.");
                        }
                    }

                    Requirement27 req27 = new Requirement27(con);
                    req27.printContinentPopulation(selectedContinent27);
                    break;

                case 24: {
                    Requirement24 requirement24 = new Requirement24(con);
                    List<PopulationReport> populationReports = requirement24.getPopulationByRegion();
                    requirement24.printPopulationReports(populationReports);
                    break;
                }
                case 26: {
                    Requirement26 requirement26 = new Requirement26(con);
                    long worldPopulation = requirement26.getWorldPopulation();
                    System.out.println();
                    System.out.println("----------------------------------------");
                    System.out.printf("World Population: %,d%n", worldPopulation);
                    System.out.println("----------------------------------------");
                    break;
                }

                case 28: {
                    String region28 = "";
                    while (region28.isEmpty()) {
                        System.out.print("Enter region (e.g. Caribbean, Western Europe, North America): ");
                        region28 = scanner.nextLine().trim();
                        if (region28.isEmpty()) {
                            System.out.println("Region cannot be empty. Try again.");
                        }
                    }

                    Requirement28 requirement28 = new Requirement28(con);
                    requirement28.printPopulation(region28);
                    break;
                }

                case 29: {
                    String country29 = "";
                    while (country29.isEmpty()) {
                        System.out.println("Enter country (e.g. Japan, Brazil, United Kingdom): ");
                        country29 = scanner.nextLine().trim();
                        if (country29.isEmpty()) {
                            System.out.println("Country cannot be empty. Try again.");
                        }
                    }

                    Requirement29 req29 = new Requirement29(con);
                    req29.printCountryPopulation(country29);
                    break;
                }

                default:
                    System.out.println("Requirement " + requirement + " has not been implemented yet.");
                    break;
            }
        }

        // Close scanner and database connection on exit
        scanner.close();
        disconnect();
    }
}
