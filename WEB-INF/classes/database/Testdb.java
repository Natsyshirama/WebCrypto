package database;

import database.DBConnexion;

import java.sql.Connection;
import java.sql.SQLException;

public class Testdb {
    public static void main(String[] args) {
        try (Connection conn = DBConnexion.getConnection()) {
            if (conn != null) {
                System.out.println("Connexion réussie à la base de données Docker MySQL !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
