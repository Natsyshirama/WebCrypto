package model.user;

import database.DBConnexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
    public User findByEmail(String email) {
        String sql = "SELECT id FROM user WHERE email = ?";
        User user = null;
        
        try (Connection conn = DBConnexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                user = new User();
                user.setId(rs.getLong("id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
      public User getUserById(long userId) {
        String sql = "SELECT id, nom, email FROM user WHERE id = ?";
        User user = null;
        
        try (Connection conn = DBConnexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                user = new User();
                user.setId(rs.getLong("id"));
                user.setNom(rs.getString("nom"));
                user.setEmail(rs.getString("email"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}