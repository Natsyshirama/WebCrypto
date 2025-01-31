package model.fond;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import database.DBConnexion;
import java.math.BigDecimal;

public class RetraitFondDAO {
    public List<RetraitFond> getRetraitsNonValide() {
        List<RetraitFond> retraits = new ArrayList<>();
        String sql = "SELECT * FROM mvt_retrait_fond WHERE etat = 0";

        try (Connection conn = DBConnexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                RetraitFond retrait = new RetraitFond();
                retrait.setIdRetrait(rs.getInt("Id_retrait"));
                retrait.setSolde(rs.getBigDecimal("solde"));
                retrait.setDaty(rs.getTimestamp("daty"));
                retrait.setEtat(rs.getInt("etat"));
                retrait.setIdUtilisateur(rs.getLong("Id_utilisateur"));
                retraits.add(retrait);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retraits;
    }

     public List<RetraitFond> getRetraitsValide() {
        List<RetraitFond> retraits = new ArrayList<>();
        String sql = "SELECT * FROM mvt_retrait_fond WHERE etat = 1";

        try (Connection conn = DBConnexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                RetraitFond retrait = new RetraitFond();
                retrait.setIdRetrait(rs.getInt("Id_retrait"));
                retrait.setSolde(rs.getBigDecimal("solde"));
                retrait.setDaty(rs.getTimestamp("daty"));
                retrait.setEtat(rs.getInt("etat"));
                retrait.setIdUtilisateur(rs.getLong("Id_utilisateur"));
                retraits.add(retrait);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retraits;
    }

public BigDecimal getSoldeUser(long idUtilisateur) {
    String sql = "SELECT solde FROM fond_user WHERE Id_utilisateur = ?";
    BigDecimal solde = BigDecimal.ZERO;

    try (Connection conn = DBConnexion.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setLong(1, idUtilisateur);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                solde = rs.getBigDecimal("solde");
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return solde;
}


public void insertRetrait(RetraitFond retrait) throws Exception {
    String checkSoldeSQL = "SELECT solde FROM fond_user WHERE Id_utilisateur = ?";
    String insertRetraitSQL = "INSERT INTO mvt_retrait_fond (solde, daty, etat, Id_utilisateur) VALUES (?, current_timestamp(), ?, ?)";
    
    try (Connection conn = DBConnexion.getConnection();
         PreparedStatement checkSoldeStmt = conn.prepareStatement(checkSoldeSQL);
         PreparedStatement insertStmt = conn.prepareStatement(insertRetraitSQL)) {
        
        conn.setAutoCommit(false);  // Démarrer une transaction

        // Vérification du solde disponible
        checkSoldeStmt.setLong(1, retrait.getIdUtilisateur());
        ResultSet rs = checkSoldeStmt.executeQuery();
        
        if (rs.next()) {
            BigDecimal soldeDisponible = rs.getBigDecimal("solde");

            // Vérification du solde suffisant
            if (soldeDisponible == null || soldeDisponible.compareTo(retrait.getSolde()) < 0) {
                throw new Exception("Solde insuffisant pour effectuer ce retrait.");
            }

            // Insertion du retrait
            insertStmt.setBigDecimal(1, retrait.getSolde());
            insertStmt.setInt(2, retrait.getEtat());
            insertStmt.setLong(3, retrait.getIdUtilisateur());
            insertStmt.executeUpdate();

            conn.commit();  // Valider la transaction
        } else {
            throw new Exception("Utilisateur introuvable.");
        }
    } catch (Exception e) {
        e.printStackTrace();
        throw new Exception("Erreur lors de l'insertion du retrait : " + e.getMessage());
    }
}

public void validerRetrait(int idRetrait) {
    String sqlSelect = "SELECT solde, Id_utilisateur FROM mvt_retrait_fond WHERE Id_retrait = ?";
    String sqlUpdateRetrait = "UPDATE mvt_retrait_fond SET etat = 1 WHERE Id_retrait = ?";
    String sqlUpdateFondUser = "UPDATE fond_user SET solde = solde - ? WHERE Id_utilisateur = ? AND solde >= ?";

    try (Connection conn = DBConnexion.getConnection();
         PreparedStatement stmtSelect = conn.prepareStatement(sqlSelect);
         PreparedStatement stmtUpdateRetrait = conn.prepareStatement(sqlUpdateRetrait);
         PreparedStatement stmtUpdateFondUser = conn.prepareStatement(sqlUpdateFondUser)) {

        // Récupérer le solde du retrait et l'utilisateur concerné
        stmtSelect.setInt(1, idRetrait);
        ResultSet rs = stmtSelect.executeQuery();

        if (rs.next()) {
            BigDecimal soldeRetrait = rs.getBigDecimal("solde");
            long idUtilisateur = rs.getLong("Id_utilisateur");

            // Vérification et mise à jour de fond_user si solde suffisant
            stmtUpdateFondUser.setBigDecimal(1, soldeRetrait);
            stmtUpdateFondUser.setLong(2, idUtilisateur);
            stmtUpdateFondUser.setBigDecimal(3, soldeRetrait);

            int rowsUpdated = stmtUpdateFondUser.executeUpdate();

            if (rowsUpdated > 0) {
                // Validation du retrait si le solde a été mis à jour
                stmtUpdateRetrait.setInt(1, idRetrait);
                stmtUpdateRetrait.executeUpdate();
            } else {
                System.out.println("Solde insuffisant pour valider le retrait.");
            }
        } else {
            System.out.println("Retrait introuvable.");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}


}