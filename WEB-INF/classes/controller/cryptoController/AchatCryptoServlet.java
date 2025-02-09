package controller.cryptoController;

import model.crypto.Crypto;
import model.crypto.CryptoDAO;
import model.fond.DepotFondDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import controller.loginController.SessionUtil;

import java.io.IOException;
import java.util.List;

import java.math.BigDecimal;

public class AchatCryptoServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            CryptoDAO cryptoDAO = new CryptoDAO();
        DepotFondDAO dao = new DepotFondDAO();

            List<Crypto> cryptosDisponibles = cryptoDAO.getAllCryptos();

            Long idUtilisateur = SessionUtil.getUserIdConnected(request);
            List<Crypto> cryptosUser = cryptoDAO.getUserCryptos(idUtilisateur);

            request.setAttribute("cryptosDisponibles", cryptosDisponibles);
            request.setAttribute("cryptosUser", cryptosUser);
            BigDecimal solde = dao.getSoldeUser(idUtilisateur);
            request.setAttribute("solde", solde);

            request.getRequestDispatcher("achatCrypto.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Erreur lors du chargement des cryptomonnaies : " + e.getMessage());
            request.getRequestDispatcher("achatCrypto.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int idCrypto = Integer.parseInt(request.getParameter("idCrypto"));
            double quantite = Double.parseDouble(request.getParameter("quantite"));

            Long idUtilisateur = SessionUtil.getUserIdConnected(request);
            if (idUtilisateur == null) {
                throw new Exception("Utilisateur non connecté.");
            }

            CryptoDAO cryptoDAO = new CryptoDAO();
            cryptoDAO.acheterCrypto(idCrypto, idUtilisateur, quantite);

            request.setAttribute("message", "Achat effectué avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Erreur lors de l'achat : " + e.getMessage());
        }

        doGet(request, response);
    }
}
