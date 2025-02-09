package model.crypto;

import database.DBConnexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class VenteCryptoDAO {

    public List<VenteCrypto> getVenteCryptoList(Timestamp dateDebut, Timestamp dateFin) throws Exception {
        List<VenteCrypto> ventes = new ArrayList<>();

        if (dateDebut == null || dateFin == null) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, 1);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            dateDebut = new Timestamp(cal.getTimeInMillis());

            cal.add(Calendar.MONTH, 1);
            cal.add(Calendar.MILLISECOND, -1);
            dateFin = new Timestamp(cal.getTimeInMillis());
        }

        String sql = "SELECT Id_vendre, nb_crypto, cours, daty, Id_Cryptomonaie, Id_utilisateur " +
                     "FROM mvt_vendre_crypto " +
                     "WHERE daty BETWEEN ? AND ? " +
                     "ORDER BY daty ASC";

        try (Connection conn = DBConnexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, dateDebut);
            stmt.setTimestamp(2, dateFin);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    VenteCrypto vente = new VenteCrypto();
                    vente.setIdVente(rs.getInt("Id_vendre"));
                    vente.setNbCrypto(rs.getBigDecimal("nb_crypto"));
                    vente.setCours(rs.getBigDecimal("cours"));
                    vente.setDaty(rs.getTimestamp("daty"));
                    vente.setIdCryptomonnaie(rs.getInt("Id_Cryptomonaie"));
                    vente.setIdUtilisateur(rs.getLong("Id_utilisateur"));
                    ventes.add(vente);
                }
            }
        }
        return ventes;
    }
} 

























