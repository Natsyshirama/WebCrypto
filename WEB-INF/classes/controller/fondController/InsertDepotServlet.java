package controller.fondController;

import model.fond.DepotFond;
import model.fond.DepotFondDAO;
import controller.loginController.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

public class InsertDepotServlet extends HttpServlet {

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
        long idUtilisateur = SessionUtil.getUserIdConnected(request);
        
         BigDecimal solde = new BigDecimal(request.getParameter("solde"));
         
         DepotFond depot = new DepotFond();
         depot.setSolde(solde);
        depot.setIdUtilisateur(idUtilisateur);
        depot.setEtat(0);

        DepotFondDAO dao = new DepotFondDAO();
        dao.insertDepot(depot);
        
        request.setAttribute("message", "Demande de depot enregistrée !");

} catch (Exception e) {
        request.setAttribute("message", "Erreur : " + e.getMessage());
    }
     doGet(request, response);
}
}