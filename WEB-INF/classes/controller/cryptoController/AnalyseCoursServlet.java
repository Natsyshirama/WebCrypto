package controller.cryptoController;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.crypto.Crypto;
import model.crypto.CryptoDAO;
import model.crypto.AnalyseDAO;

import java.util.Map;
import java.util.HashMap;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
public class AnalyseCoursServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Récupération de la liste des cryptomonnaies depuis CryptoDAO
        CryptoDAO cryptoDAO = new CryptoDAO();
        List<Crypto> cryptos = cryptoDAO.getAllCryptos();
        request.setAttribute("cryptos", cryptos); // Envoi à la JSP

        String typeAnalyse = request.getParameter("typeAnalyse");
        String[] selectedCryptos = request.getParameterValues("crypto");
        String dateMinParam = request.getParameter("dateMin");
        String dateMaxParam = request.getParameter("dateMax");

        Timestamp dateMin = null;
        Timestamp dateMax = null;

        try {
            if (dateMinParam != null && !dateMinParam.isEmpty()) {
                dateMin = Timestamp.valueOf(dateMinParam.replace("T", " ") + ":00");
            }
            if (dateMaxParam != null && !dateMaxParam.isEmpty()) {
                dateMax = Timestamp.valueOf(dateMaxParam.replace("T", " ") + ":00");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors du traitement des dates.");
        }

        // Traitement des analyses si les données sont soumises
        if (typeAnalyse != null) {
            AnalyseDAO dao = new AnalyseDAO();
            try {
                Map<String, Double> resultats = dao.analyseCours(typeAnalyse, selectedCryptos, dateMin, dateMax);
                request.setAttribute("analyse", typeAnalyse);
                request.setAttribute("resultats", resultats);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Erreur lors de l'analyse des cours : " + e.getMessage());
            }
        }

        // Redirection vers la JSP
        request.getRequestDispatcher("AnalyseCrypto.jsp").forward(request, response);
    }
}