package com.napier.population.reports;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Generates the population of the world
 */
public class Requirement26 {
    private final Connection con;

    public Requirement26(Connection con) {
        this.con = con;
    }

    /**
     * Requirement 26: The population of the world.
     *
     * @return The Total population of the world.
     */
    public long getWorldPopulation() {
        long population = 0;

        if (con == null) {
            return population;
        }

        String sql = "SELECT SUM(Population) AS population FROM country";

        try (PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                population = rs.getLong("population");
            }
        } catch (SQLException e) {
                System.out.println("Failed to get world population");
                e.printStackTrace();
        }
        return population;
    }
}
