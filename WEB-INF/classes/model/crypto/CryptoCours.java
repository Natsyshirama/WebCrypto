package model.crypto;

import database.DBConnexion;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CryptoCours {

    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

   
    public void start() {
        scheduler.scheduleAtFixedRate(() -> {
            try {
                updateCours();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 10, TimeUnit.SECONDS);
    }

   
    private void updateCours() throws Exception {
        Connection conn = null;
        try {
            conn = DBConnexion.getConnection();
            conn.setAutoCommit(false);

            String selectSQL = "SELECT Id_Cryptomonaie, cours FROM Cryptomonaie";
            try (PreparedStatement psSelect = conn.prepareStatement(selectSQL);
                 ResultSet rs = psSelect.executeQuery()) {

                while (rs.next()) {
                    int idCrypto = rs.getInt("Id_Cryptomonaie");
                    BigDecimal coursTalou = rs.getBigDecimal("cours");

                    BigDecimal newCours = generateNewCours(coursTalou);

                    //sql update crypto
                    String updateSQL = "UPDATE Cryptomonaie SET cours = ? WHERE Id_Cryptomonaie = ?";
                    try (PreparedStatement psUpdat = conn.prepareStatement(updateSQL)) {
                        psUpdat.setBigDecimal(1, newCours);
                        psUpdat.setInt(2, idCrypto);
                        psUpdat.executeUpdate();
                    }

                    // sql insert
                    String insertSQL = "INSERT INTO coursHistorique (idCrypto, datyUpdate, cours) VALUES (?, NOW(), ?)";
                    try (PreparedStatement psInsert = conn.prepareStatement(insertSQL)) {
                      
                        psInsert.setString(1, String.valueOf(idCrypto));
                        psInsert.setBigDecimal(2, coursTalou);
                        psInsert.executeUpdate();
                    }
                }
            }
            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
            }
            throw new Exception("Erreur lors de la mise à jour des cours : " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
            }
        }
    }

    
    private BigDecimal generateNewCours(BigDecimal coursTalou) {
        double random = 0.95 + Math.random() * 0.10; // entre 0.95 et 1.05
        return coursTalou.multiply(BigDecimal.valueOf(random))
                       .setScale(10, BigDecimal.ROUND_HALF_UP);
    }

   
    public void stop() {
        scheduler.shutdownNow();
    }
}
