package model.crypto;

import database.DBConnexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AchatCryptoDAO {

    /**
     * Récupère la liste des achats dans l'intervalle [dateDebut, dateFin].
     * Si dateDebut et dateFin sont null, retourne les achats du mois en cours.
     */
    public List<AchatCrypto> getAchatCryptoList(Timestamp dateDebut, Timestamp dateFin) throws Exception {
        List<AchatCrypto> achats = new ArrayList<>();

        // Si aucun filtre n'est fourni, on utilise le mois en cours par défaut
        if (dateDebut == null || dateFin == null) {
            Calendar cal = Calendar.getInstance();
            // Début du mois en cours
            cal.set(Calendar.DAY_OF_MONTH, 1);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            dateDebut = new Timestamp(cal.getTimeInMillis());

            // Fin du mois en cours : passage au mois suivant puis retrait d'un milliseconde
            cal.add(Calendar.MONTH, 1);
            cal.add(Calendar.MILLISECOND, -1);
            dateFin = new Timestamp(cal.getTimeInMillis());
        }

        String sql = "SELECT Id_achat, nb_crypto, cours, daty, Id_Cryptomonaie, Id_utilisateur " +
                     "FROM mvt_achat_crypto " +
                     "WHERE daty BETWEEN ? AND ? " +
                     "ORDER BY daty ASC";

        try (Connection conn = DBConnexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, dateDebut);
            stmt.setTimestamp(2, dateFin);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    AchatCrypto achat = new AchatCrypto();
                    achat.setIdAchat(rs.getInt("Id_achat"));
                    achat.setNbCrypto(rs.getBigDecimal("nb_crypto"));
                    achat.setCours(rs.getBigDecimal("cours"));
                    achat.setDaty(rs.getTimestamp("daty"));
                    achat.setIdCryptomonnaie(rs.getInt("Id_Cryptomonaie"));
                    achat.setIdUtilisateur(rs.getLong("Id_utilisateur"));
                    achats.add(achat);
                }
            }
        }
        return achats;
    }
} 