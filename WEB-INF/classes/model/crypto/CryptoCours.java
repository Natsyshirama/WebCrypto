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

    /**
     * Démarre la mise à jour du cours toutes les 10 secondes.
     */
    public void start() {
        scheduler.scheduleAtFixedRate(() -> {
            try {
                updateCours();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 10, TimeUnit.SECONDS);
    }

    /**
     * Pour chaque cryptomonnaie, met à jour le cours et insère l'ancienne valeur dans coursHistorique.
     */
    private void updateCours() throws Exception {
        Connection conn = null;
        try {
            conn = DBConnexion.getConnection();
            conn.setAutoCommit(false);

            // Récupération de toutes les cryptomonnaies
            String selectSQL = "SELECT Id_Cryptomonaie, cours FROM Cryptomonaie";
            try (PreparedStatement psSelect = conn.prepareStatement(selectSQL);
                 ResultSet rs = psSelect.executeQuery()) {

                while (rs.next()) {
                    int idCrypto = rs.getInt("Id_Cryptomonaie");
                    BigDecimal coursTalou = rs.getBigDecimal("cours");

                    // Générer une nouvelle valeur pour le cours (par exemple, avec une fluctuation aléatoire)
                    BigDecimal newCours = generateNewCours(coursTalou);

                    // Mise à jour du cours dans Cryptomonaie
                    String updateSQL = "UPDATE Cryptomonaie SET cours = ? WHERE Id_Cryptomonaie = ?";
                    try (PreparedStatement psUpdat = conn.prepareStatement(updateSQL)) {
                        psUpdat.setBigDecimal(1, newCours);
                        psUpdat.setInt(2, idCrypto);
                        psUpdat.executeUpdate();
                    }

                    // Insertion de l'ancienne valeur dans coursHistorique
                    String insertSQL = "INSERT INTO coursHistorique (idCrypto, datyUpdate, cours) VALUES (?, NOW(), ?)";
                    try (PreparedStatement psInsert = conn.prepareStatement(insertSQL)) {
                        // Attention : dans votre table coursHistorique, le champ idCrypto est défini comme varchar(50)
                        // On convertit donc l'id en chaîne de caractères.
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

    /**
     * Méthode pour générer une nouvelle valeur pour le cours.
     * Ici, on applique un facteur aléatoire compris entre 0.95 et 1.05.
     */
    private BigDecimal generateNewCours(BigDecimal coursTalou) {
        double random = 0.95 + Math.random() * 0.10; // entre 0.95 et 1.05
        return coursTalou.multiply(BigDecimal.valueOf(random))
                       .setScale(10, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Arrête le scheduler (à appeler lors de l'arrêt de l'application).
     */
    public void stop() {
        scheduler.shutdownNow();
    }
}
