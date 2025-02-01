package controller.fondController;

import model.fond.DepotFond;
import model.fond.DepotFondDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class DepotServlet extends HttpServlet{
       
       protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DepotFondDAO dao = new DepotFondDAO();

        List<DepotFond> depots= dao.getDepotsNonValide();
        request.setAttribute("depots", depots);
        request.getRequestDispatcher("/fond/depot.jsp").forward(request, response);
      
       }
       
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {    
               int idDepot = Integer.parseInt(request.getParameter("idDepot"));
               DepotFondDAO dao = new DepotFondDAO();

              dao.validerDepot(idDepot);
              request.setAttribute("message", "Le depot a été valide avec succès !");
               request.getRequestDispatcher("depot.jsp").forward(request, response);
       
       }catch (Exception e) {
              e.printStackTrace();
              request.setAttribute("message", "Erreur lors de l'insertion : " + e.getMessage());
              request.getRequestDispatcher("depot.jsp").forward(request, response);
        }

    }
}