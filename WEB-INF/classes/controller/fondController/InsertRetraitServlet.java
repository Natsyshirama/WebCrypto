package controller.fondController;

import model.fond.RetraitFond;
import model.fond.RetraitFondDAO;
import controller.loginController.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

public class InsertRetraitServlet extends HttpServlet {// InsertRetraitServlet.java

protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
        long idUtilisateur = SessionUtil.getUserIdConnected(request);
        
        RetraitFondDAO retraitDAO = new RetraitFondDAO();
        BigDecimal solde = retraitDAO.getSoldeUser(idUtilisateur);
        
        request.setAttribute("solde", solde);
        request.getRequestDispatcher("insertRetrait.jsp").forward(request, response);
        
    } catch (Exception e) {
        response.sendRedirect("login.jsp");
    }
}

protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
        long idUtilisateur = SessionUtil.getUserIdConnected(request);
        
        BigDecimal solde = new BigDecimal(request.getParameter("solde"));
        RetraitFond retrait = new RetraitFond();
        retrait.setSolde(solde);
        retrait.setIdUtilisateur(idUtilisateur);
        retrait.setEtat(0);

        RetraitFondDAO retraitDAO = new RetraitFondDAO();
        retraitDAO.insertRetrait(retrait);
        
        request.setAttribute("message", "Demande de retrait enregistrée !");
        
    } catch (Exception e) {
        request.setAttribute("message", "Erreur : " + e.getMessage());
    }
    
    doGet(request, response); // Recharge la page avec les nouvelles données
}
}
