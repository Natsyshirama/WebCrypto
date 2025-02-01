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
            
            if (rowsUpdated > 0) {
                // Validation du retrait si le solde a été mis à jour
                stmtUpdateDepot.setInt(1, idDepot);
                stmtUpdateDepot.executeUpdate();
            } 
        } else {
            System.out.println("Depot introuvable.");
        }
    }catch (Exception e) {
        e.printStackTrace();
    }
}
}