package controller.userController;

import model.user.User;
import model.user.UserDAO;
import controller.loginController.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ProfilServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        try {
            
            long userId =  SessionUtil.getUserIdConnected(request);
            UserDAO userDAO = new UserDAO();
            User user = userDAO.getUserById(userId);
            
            if (user != null) {
                request.setAttribute("user", user);
                request.getRequestDispatcher("Profil.jsp").forward(request, response);
            } else {
                throw new Exception("Utilisateur non trouvé");
            }
            
        } catch (Exception e) {
            request.setAttribute("message", "Erreur: " + e.getMessage());
            request.getRequestDispatcher("home.jsp").forward(request, response);
        }
    }
}