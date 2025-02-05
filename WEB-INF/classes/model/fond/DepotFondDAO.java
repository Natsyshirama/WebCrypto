package model.fond;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import database.DBConnexion;
import java.math.BigDecimal;

public class DepotFondDAO {

    // depot NOn valider
    public List<DepotFond> getDepotsNonValide(){
        List<DepotFond> depots = new ArrayList<>();
        String sql = "SELECT * FROM mvt_depot_fond WHERE etat = 0";
        try(
            Connection conn = DBConnexion.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery()){
                
            while (rs.next()) {
                DepotFond depot = new DepotFond();
                depot.setIdDepot(rs.getInt("Id_depot"));
                depot.setSolde(rs.getBigDecimal("solde"));
                depot.setDaty(rs.getTimestamp("daty"));
                depot.setEtat(rs.getInt("etat"));
                depot.setIdUtilisateur(rs.getLong("Id_utilisateur"));
                
                depots.add(depot);
            }    
        }catch (Exception e) {
            e.printStackTrace();
        }
        return depots;
    }

public void validerDepot(int idDepot){
        String sqlSelect = "SELECT solde, Id_utilisateur FROM mvt_depot_fond WHERE Id_depot = ?";
        String sqlUpdateDepot = "UPDATE mvt_depot_fond SET etat = 1 WHERE Id_depot = ?";
        String sqlUpdateFondUser = "UPDATE fond_user SET solde = solde + ? WHERE Id_utilisateur = ? AND solde >= ?";
    
    try (Connection conn = DBConnexion.getConnection();
         PreparedStatement stmtSelect = conn.prepareStatement(sqlSelect);
         PreparedStatement stmtUpdateDepot = conn.prepareStatement(sqlUpdateDepot);
         PreparedStatement stmtUpdateFondUser = conn.prepareStatement(sqlUpdateFondUser)) {

        stmtSelect.setInt(1, idDepot);
        ResultSet rs = stmtSelect.executeQuery();

        if (rs.next()) {
            BigDecimal soldeDepot = rs.getBigDecimal("solde");
            long idUtilisateur = rs.getLong("Id_utilisateur");

            stmtUpdateFondUser.setBigDecimal(1, soldeDepot);
            stmtUpdateFondUser.setLong(2, idUtilisateur);
            stmtUpdateFondUser.setBigDecimal(3, soldeDepot);

            int rowsUpdated = stmtUpdateFondUser.executeUpdate();
            
                // Validation du retrait si le solde a été mis à jour
                stmtUpdateDepot.setInt(1, idDepot);
                stmtUpdateDepot.executeUpdate();
          
   }
   } catch (Exception e) {
        e.printStackTrace();
    }
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

public void insertDepot(DepotFond depot) throws Exception {
    String insertDepotSQL = "INSERT INTO mvt_depot_fond (solde, daty, etat, Id_utilisateur) VALUES (?, current_timestamp(), ?, ?)";
    
    try (Connection conn = DBConnexion.getConnection();
         PreparedStatement insertStmt = conn.prepareStatement(insertDepotSQL)) {
        
        conn.setAutoCommit(false);  // Démarrer une transaction

            insertStmt.setBigDecimal(1, depot.getSolde());
            insertStmt.setInt(2, depot.getEtat());
            insertStmt.setLong(3, depot.getIdUtilisateur());
            insertStmt.executeUpdate();
        
        conn.commit();  // Valider la transaction

    } catch (Exception e) {
        e.printStackTrace();
        throw new Exception("Erreur lors de l'insertion du depot : " + e.getMessage());
    }
}
}