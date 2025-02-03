package controller.cryptoController;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import model.crypto.Crypto;
import model.crypto.Cryptoko;
import model.fond.DepotFondDAO;
import model.crypto.CryptoDAO; 
import controller.loginController.SessionUtil;

import java.io.IOException;
import java.util.List;

import java.math.BigDecimal;

public class VendreCryptoServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            CryptoDAO cryptoDAO = new CryptoDAO();
            DepotFondDAO dao = new DepotFondDAO();

            // Liste des cryptos disponibles pour l'achat

            // Liste des cryptos déjà possédées par l'utilisateur
            Long idUtilisateur = SessionUtil.getUserIdConnected(request);
            List<Crypto> cryptosUser = cryptoDAO.getUserCryptos(idUtilisateur);
            List<Cryptoko> myCryptos = cryptoDAO.getCryptosPossedees(idUtilisateur);

            request.setAttribute("myCryptos", myCryptos);
            request.setAttribute("cryptosUser", cryptosUser);
            BigDecimal solde = dao.getSoldeUser(idUtilisateur);
            request.setAttribute("solde", solde);

            request.getRequestDispatcher("vendreCrypto.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Erreur lors du chargement des cryptomonnaies : " + e.getMessage());
            request.getRequestDispatcher("vendreCrypto.jsp").forward(request, response);
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
        int idCrypto = Integer.parseInt(request.getParameter("idCrypto"));
        double quantite = Double.parseDouble(request.getParameter("quantite"));
        
        Long userId = SessionUtil.getUserIdConnected(request);
        if(userId == null) throw new Exception("Authentification requise");
        
        new CryptoDAO().vendreCrypto(idCrypto, userId, quantite);
        
        request.setAttribute("message", "Vente exécutée avec succès !");
    } catch (Exception e) {
        request.setAttribute("message", "Erreur de vente: " + e.getMessage());
    }
    
    doGet(request, response); // Recharge les données mises à jour
}

}