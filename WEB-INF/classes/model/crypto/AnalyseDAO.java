package model.crypto;

import database.DBConnexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class AnalyseDAO {

    public String getCryptoNameById(int idCrypto) {
        String sql = "SELECT nom FROM Cryptomonaie WHERE Id_Cryptomonaie = ?";
        try (Connection conn = DBConnexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCrypto);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nom");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, Double> analyseCours(String typeAnalyse, String[] cryptos, Timestamp dateMin, Timestamp dateMax) throws Exception {
        StringBuilder sql = new StringBuilder("SELECT ch.cours, c.nom FROM coursHistorique ch JOIN cryptomonaie c ON ch.idCrypto = c.Id_Cryptomonaie WHERE ch.datyUpdate BETWEEN ? AND ?");
        if (cryptos != null && !Arrays.asList(cryptos).contains("tous")) {
            sql.append(" AND ch.idCrypto IN (");
            for (int i = 0; i < cryptos.length; i++) {
                sql.append("?");
                if (i < cryptos.length - 1) sql.append(",");
            }
            sql.append(")");
        }

        try (Connection conn = DBConnexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            stmt.setTimestamp(1, dateMin);
            stmt.setTimestamp(2, dateMax);
            int index = 3;
            if (cryptos != null && !Arrays.asList(cryptos).contains("tous")) {
                for (String crypto : cryptos) {
                    stmt.setString(index++, crypto);
                }
            }

            ResultSet rs = stmt.executeQuery();

            Map<String, Double> resultats = new HashMap<>();
            Map<String, Double> sommeCours = new HashMap<>();
            Map<String, Double> sommeCarres = new HashMap<>();
            Map<String, Integer> nombreCours = new HashMap<>();
            Map<String, List<Double>> coursParCrypto = new HashMap<>();

            while (rs.next()) {
                String nomCrypto = rs.getString("nom");
                double cours = rs.getDouble("cours");

                if ("max".equals(typeAnalyse)) {
                    resultats.put(nomCrypto, Math.max(resultats.getOrDefault(nomCrypto, Double.MIN_VALUE), cours));
                }
                if ("min".equals(typeAnalyse)) {
                    resultats.put(nomCrypto, Math.min(resultats.getOrDefault(nomCrypto, Double.MAX_VALUE), cours));
                }
                if ("moyenne".equals(typeAnalyse) || "ecartType".equals(typeAnalyse)) {
                    sommeCours.put(nomCrypto, sommeCours.getOrDefault(nomCrypto, 0.0) + cours);
                    sommeCarres.put(nomCrypto, sommeCarres.getOrDefault(nomCrypto, 0.0) + cours * cours);
                    nombreCours.put(nomCrypto, nombreCours.getOrDefault(nomCrypto, 0) + 1);
                }
                if ("quartile".equals(typeAnalyse)) {
                    coursParCrypto.computeIfAbsent(nomCrypto, k -> new ArrayList<>()).add(cours);
                }
            }

            if ("moyenne".equals(typeAnalyse)) {
                for (Map.Entry<String, Double> entry : sommeCours.entrySet()) {
                    String nomCrypto = entry.getKey();
                    double somme = entry.getValue();
                    int count = nombreCours.get(nomCrypto);
                    resultats.put(nomCrypto, somme / count);
                }
            }
            if ("ecartType".equals(typeAnalyse)) {
                for (Map.Entry<String, Double> entry : sommeCours.entrySet()) {
                    String nomCrypto = entry.getKey();
                    double somme = entry.getValue();
                    double sommeCarre = sommeCarres.get(nomCrypto);
                    int count = nombreCours.get(nomCrypto);
                    double moyenne = somme / count;
                    double variance = (sommeCarre / count) - (moyenne * moyenne);
                    double ecartType = Math.sqrt(variance);
                    resultats.put(nomCrypto, ecartType);
                }
            }
            if ("quartile".equals(typeAnalyse)) {
                for (Map.Entry<String, List<Double>> entry : coursParCrypto.entrySet()) {
                    String nomCrypto = entry.getKey();
                    List<Double> coursList = entry.getValue();
                    Collections.sort(coursList);
                    int indexQuartile = (int) Math.ceil(0.25 * coursList.size()) - 1;
                    double premierQuartile = coursList.get(indexQuartile);
                    resultats.put(nomCrypto, premierQuartile);
                }
            }

            return resultats;
        }
    }
}
