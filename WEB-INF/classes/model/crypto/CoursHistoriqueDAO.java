package model.crypto;

import model.crypto.CoursHistorique;

import database.DBConnexion;

import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CoursHistoriqueDAO {

   public List<Crypto> getAllCryptomonnaies() throws Exception {
    String query = "SELECT Id_Cryptomonaie, nom FROM cryptomonaie";
    List<Crypto> cryptoList = new ArrayList<>();

    try (Connection conn = DBConnexion.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Crypto crypto = new Crypto();
            crypto.setId(rs.getInt("Id_Cryptomonaie"));
            crypto.setNom(rs.getString("nom"));
            cryptoList.add(crypto);
        }
    }
    return cryptoList;
}

public List<CoursHistorique> getCoursByIdCryptomonnaie(int idCrypto) throws Exception {
    String sql = "SELECT datyUpdate, cours FROM coursHistorique WHERE idCrypto = ? ORDER BY datyUpdate ASC";
    List<CoursHistorique> coursList = new ArrayList<>();

    try (Connection conn = DBConnexion.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, idCrypto);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            CoursHistorique ch = new CoursHistorique();
            ch.setDatyUpdate(rs.getTimestamp("datyUpdate").toLocalDateTime());
            ch.setCours(rs.getBigDecimal("cours"));
            coursList.add(ch);
        }
    }
    return coursList;
}

}
