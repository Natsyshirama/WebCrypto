package controller.loginController;

import model.user.User;
import model.user.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        
        try {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.findByEmail(email);
            
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("idUtilisateur", user.getId());
                response.sendRedirect("home.jsp");
            } else {
                request.setAttribute("message", "Aucun utilisateur trouvé avec cet email");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Erreur de connexion");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}