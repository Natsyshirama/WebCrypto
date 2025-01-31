package controller.fondController;

import model.fond.RetraitFond;
import model.fond.RetraitFondDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class RetraitServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        RetraitFondDAO dao = new RetraitFondDAO();
        List<RetraitFond> retraits = dao.getRetraitsNonValide();

        request.setAttribute("retraits", retraits);
        request.getRequestDispatcher("/fond/retrait.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         try {    
            int idRetrait = Integer.parseInt(request.getParameter("idRetrait"));

        RetraitFondDAO retraitFondDAO = new RetraitFondDAO();
        retraitFondDAO.validerRetrait(idRetrait);
        
        request.setAttribute("message", "Le retrait a été valide avec succès !");
        request.getRequestDispatcher("retrait.jsp").forward(request, response);

    }catch (Exception e) {
        e.printStackTrace();
            request.setAttribute("message", "Erreur lors de l'insertion : " + e.getMessage());
            request.getRequestDispatcher("retrait.jsp").forward(request, response);
        }
}
}
