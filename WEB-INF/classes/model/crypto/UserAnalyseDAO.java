package model.crypto;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import database.DBConnexion;


public class UserAnalyseDAO {
    public List<UserAnalise> getUserAnalise(String dateMax) {
    List<UserAnalise> analyses = new ArrayList<>();
    String query = """
        SELECT u.id, u.nom, u.email,
               COALESCE((SELECT SUM(nb_crypto * cours) 
                         FROM mvt_achat_crypto 
                         WHERE Id_utilisateur = u.id 
                         AND daty <= ?) , 0) AS totalAchat,
               COALESCE((SELECT SUM(nb_crypto * cours) 
                         FROM mvt_vendre_crypto 
                         WHERE Id_utilisateur = u.id 
                         AND daty <= ?) , 0) AS totalVente,
               COALESCE((SELECT solde 
                         FROM fond_user 
                         WHERE Id_utilisateur = u.id), 0) AS solde
        FROM user u;
    """;

    try (Connection conn = DBConnexion.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setString(1, dateMax);
        stmt.setString(2, dateMax);

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                long userId = rs.getLong("id");
                String nom = rs.getString("nom");
                String email = rs.getString("email");
                BigDecimal totalAchat = rs.getBigDecimal("totalAchat");
                BigDecimal totalVente = rs.getBigDecimal("totalVente");
                BigDecimal solde = rs.getBigDecimal("solde");

                analyses.add(new UserAnalise(userId, nom, email, totalAchat, totalVente, solde));
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return analyses;
}

}
