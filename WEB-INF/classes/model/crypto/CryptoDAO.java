package model.crypto;

import database.DBConnexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class CryptoDAO {

    // Récupérer toutes les cryptomonnaies
    public List<Crypto> getAllCryptos() {
        List<Crypto> cryptos = new ArrayList<>();
        String sql = "SELECT Id_Cryptomonaie, nom, cours FROM Cryptomonaie";

        try (Connection conn = DBConnexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Crypto crypto = new Crypto();
                crypto.setId(rs.getInt("Id_Cryptomonaie"));
                crypto.setNom(rs.getString("nom"));
                crypto.setCours(rs.getBigDecimal("cours"));
                cryptos.add(crypto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cryptos;
    }

public BigDecimal getCoursCrypto(int idCrypto) throws Exception {
    String selectCoursSQL = "SELECT cours FROM Cryptomonaie WHERE Id_Cryptomonaie = ?";
    try (Connection conn = DBConnexion.getConnection();
         PreparedStatement stmt = conn.prepareStatement(selectCoursSQL)) {
        stmt.setInt(1, idCrypto);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getBigDecimal("cours");
            } else {
                throw new Exception("Cryptomonnaie introuvable.");
            }
        }
    }
}


public List<Crypto> getUserCryptos(long idUtilisateur) throws Exception {
    List<Crypto> cryptos = new ArrayList<>();
    String sql = """
        SELECT c.Id_Cryptomonaie, c.nom, c.cours, SUM(cu.nb_crypto) AS total_crypto
        FROM crypto_user cu
        JOIN Cryptomonaie c ON cu.Id_Cryptomonaie = c.Id_Cryptomonaie
        WHERE cu.Id_utilisateur = ?
        GROUP BY c.Id_Cryptomonaie, c.nom, c.cours
        """;  // La requête est modifiée pour regrouper les quantités possédées

    try (Connection conn = DBConnexion.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setLong(1, idUtilisateur);

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Crypto crypto = new Crypto();
                crypto.setId(rs.getInt("Id_Cryptomonaie"));
                crypto.setNom(rs.getString("nom"));
                crypto.setCours(rs.getBigDecimal("cours"));
                crypto.setTotalCrypto(rs.getBigDecimal("total_crypto"));  // Utilisation de la somme des cryptos
                cryptos.add(crypto);
            }
        }
    }
    return cryptos;
}



public void acheterCrypto(int idCrypto, long idUtilisateur, double quantite) throws Exception {
    String selectFondSQL = "SELECT solde FROM fond_user WHERE Id_utilisateur = ?";
    String insertAchatSQL = "INSERT INTO mvt_achat_crypto (nb_crypto, cours, Id_Cryptomonaie, Id_utilisateur)  VALUES (?, ?, ?, ?)";
    String updateCryptoUserSQL = "INSERT INTO crypto_user (nb_crypto, Id_Cryptomonaie, Id_utilisateur) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE nb_crypto = nb_crypto + VALUES(nb_crypto)";
    String updateFondSQL = "UPDATE fond_user SET solde = solde - ? WHERE Id_utilisateur = ?";

    try (Connection conn = DBConnexion.getConnection()) {
        conn.setAutoCommit(false);

        // Vérification du solde utilisateur
        BigDecimal soldeUtilisateur;
        BigDecimal montantAchat;
        BigDecimal cours;

        // Récupération du cours actuel de la cryptomonnaie
        String selectCoursSQL = "SELECT cours FROM Cryptomonaie WHERE Id_Cryptomonaie = ?";
        try (PreparedStatement stmtCours = conn.prepareStatement(selectCoursSQL)) {
            stmtCours.setInt(1, idCrypto);
            try (ResultSet rsCours = stmtCours.executeQuery()) {
                if (!rsCours.next()) {
                    throw new Exception("Cryptomonnaie introuvable.");
                }
                cours = rsCours.getBigDecimal("cours");
            }
        }

        montantAchat = cours.multiply(BigDecimal.valueOf(quantite));

        // Vérification du solde disponible
        try (PreparedStatement stmtFond = conn.prepareStatement(selectFondSQL)) {
            stmtFond.setLong(1, idUtilisateur);
            try (ResultSet rs = stmtFond.executeQuery()) {
                if (!rs.next()) {
                    throw new Exception("Aucun fond utilisateur trouvé.");
                }
                soldeUtilisateur = rs.getBigDecimal("solde");
            }
        }

        if (soldeUtilisateur.compareTo(montantAchat) < 0) {
            throw new Exception("Fonds insuffisants pour effectuer l'achat.");
        }

        // Insertion dans `mvt_achat_crypto`
        try (PreparedStatement stmtAchat = conn.prepareStatement(insertAchatSQL)) {
            stmtAchat.setBigDecimal(1, BigDecimal.valueOf(quantite));
            stmtAchat.setBigDecimal(2, cours);
            stmtAchat.setInt(3, idCrypto);
            stmtAchat.setLong(4, idUtilisateur);
            stmtAchat.executeUpdate();
        }

        // Mise à jour ou insertion dans `crypto_user`
        try (PreparedStatement stmtCryptoUser = conn.prepareStatement(updateCryptoUserSQL)) {
            stmtCryptoUser.setBigDecimal(1, BigDecimal.valueOf(quantite));
            stmtCryptoUser.setInt(2, idCrypto);
            stmtCryptoUser.setLong(3, idUtilisateur);
            stmtCryptoUser.executeUpdate();
        }

        // Mise à jour du solde utilisateur
        try (PreparedStatement stmtFondUpdate = conn.prepareStatement(updateFondSQL)) {
            stmtFondUpdate.setBigDecimal(1, montantAchat);
            stmtFondUpdate.setLong(2, idUtilisateur);
            stmtFondUpdate.executeUpdate();
        }

        conn.commit();
    } catch (Exception e) {
        e.printStackTrace();
        throw new Exception("Erreur lors de l'achat de la cryptomonnaie : " + e.getMessage());
    }
}


public List<Cryptoko> getCryptosPossedees(long idUtilisateur) throws SQLException {
    String sql = """
        SELECT cu.Id_Cryptomonaie AS idCrypto, 
               c.nom, 
               cu.nb_crypto AS quantite,
               c.cours AS coursActuel
        FROM crypto_user cu
        INNER JOIN Cryptomonaie c ON cu.Id_Cryptomonaie = c.Id_Cryptomonaie
        WHERE cu.Id_utilisateur = ? AND cu.nb_crypto > 0
        """;
    
    List<Cryptoko> result = new ArrayList<>();

    try (Connection conn = DBConnexion.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
         
        stmt.setLong(1, idUtilisateur);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
           Cryptoko cp = new Cryptoko();
            cp.setIdCrypto(rs.getInt("idCrypto"));
            cp.setNom(rs.getString("nom"));
            cp.setQuantite(rs.getBigDecimal("quantite"));
            cp.setCoursActuel(rs.getBigDecimal("coursActuel"));
            result.add(cp);

        }
    }
    return result;
}


public void vendreCrypto(int idCrypto, long idUtilisateur, double quantite) throws Exception {
    String selectCryptoSQL = "SELECT nb_crypto FROM crypto_user WHERE Id_Cryptomonaie = ? AND Id_utilisateur = ?";
    String insertVenteSQL = """
        INSERT INTO mvt_vendre_crypto (nb_crypto, cours, Id_Cryptomonaie, Id_utilisateur) 
        VALUES (?, ?, ?, ?)
        """;
    String updateCryptoUserSQL = """
        UPDATE crypto_user 
        SET nb_crypto = nb_crypto - ? 
        WHERE Id_Cryptomonaie = ? AND Id_utilisateur = ?
        """;
    String updateFondSQL = "UPDATE fond_user SET solde = solde + ? WHERE Id_utilisateur = ?";
    String selectCoursSQL = "SELECT cours FROM Cryptomonaie WHERE Id_Cryptomonaie = ?";

    try (Connection conn = DBConnexion.getConnection()) {
        conn.setAutoCommit(false);

        // 1. Vérification de la quantité disponible
        BigDecimal cryptoDisponible;
        try (PreparedStatement stmt = conn.prepareStatement(selectCryptoSQL)) {
            stmt.setInt(1, idCrypto);
            stmt.setLong(2, idUtilisateur);
            ResultSet rs = stmt.executeQuery();
            
            if (!rs.next()) throw new Exception("Vous ne possédez pas cette cryptomonnaie");
            cryptoDisponible = rs.getBigDecimal("nb_crypto");
            
            if (cryptoDisponible.compareTo(BigDecimal.valueOf(quantite)) < 0) {
                throw new Exception("Quantité insuffisante (disponible: " + cryptoDisponible + ")");
            }
        }

        // 2. Récupération du cours actuel
        BigDecimal cours;
        try (PreparedStatement stmt = conn.prepareStatement(selectCoursSQL)) {
            stmt.setInt(1, idCrypto);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) throw new Exception("Cryptomonnaie introuvable");
            cours = rs.getBigDecimal("cours");
        }

        // 3. Calcul du montant de la vente
        BigDecimal montantVente = cours.multiply(BigDecimal.valueOf(quantite));

        // 4. Insertion dans l'historique des ventes
        try (PreparedStatement stmt = conn.prepareStatement(insertVenteSQL)) {
            stmt.setBigDecimal(1, BigDecimal.valueOf(quantite));
            stmt.setBigDecimal(2, cours);
            stmt.setInt(3, idCrypto);
            stmt.setLong(4, idUtilisateur);
            stmt.executeUpdate();
        }

        // 5. Mise à jour du portefeuille crypto
        try (PreparedStatement stmt = conn.prepareStatement(updateCryptoUserSQL)) {
            stmt.setBigDecimal(1, BigDecimal.valueOf(quantite));
            stmt.setInt(2, idCrypto);
            stmt.setLong(3, idUtilisateur);
            int rowsUpdated = stmt.executeUpdate();
            
            if (rowsUpdated == 0) {
                throw new Exception("Erreur critique lors de la mise à jour du portefeuille");
            }
        }

        // 6. Crédit du solde utilisateur
        try (PreparedStatement stmt = conn.prepareStatement(updateFondSQL)) {
            stmt.setBigDecimal(1, montantVente);
            stmt.setLong(2, idUtilisateur);
            stmt.executeUpdate();
        }

        conn.commit();
    } catch (SQLException e) {
        throw new Exception("Erreur technique lors de la vente: " + e.getMessage());
    }
}
}
