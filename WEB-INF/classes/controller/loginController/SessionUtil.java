package controller.loginController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionUtil {
    public static long getUserIdConnected(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new Exception("Utilisateur non connecté");
        }
        
        Long userId = (Long) session.getAttribute("idUtilisateur");
        if (userId == null) {
            throw new Exception("Session utilisateur invalide");
        }
        
        return userId;
    }
}