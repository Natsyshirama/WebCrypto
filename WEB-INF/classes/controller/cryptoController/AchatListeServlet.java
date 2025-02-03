package controller.cryptoController;

import model.crypto.AchatCrypto;
import model.crypto.AchatCryptoDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

public class AchatListeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String dateDebutParam = request.getParameter("dateDebut");
        String dateFinParam = request.getParameter("dateFin");

        Timestamp dateDebut = null;
        Timestamp dateFin = null;

        try {
            // Format attendu : "yyyy-MM-dd"
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (dateDebutParam != null && !dateDebutParam.trim().isEmpty()) {
                // Créer un Timestamp pour le début de la journée
                dateDebut = new Timestamp(sdf.parse(dateDebutParam).getTime());
            }
            if (dateFinParam != null && !dateFinParam.trim().isEmpty()) {
                // Créer un Timestamp pour la fin de la journée (23:59:59)
                dateFin = new Timestamp(sdf.parse(dateFinParam).getTime());
                // Ajouter 23:59:59 à la date fin
                dateFin.setTime(dateFin.getTime() + (23 * 3600 + 59 * 60 + 59) * 1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // En cas d'erreur de parsing, on laisse les valeurs null pour utiliser le mois en cours
        }

        AchatCryptoDAO dao = new AchatCryptoDAO();
        try {
            List<AchatCrypto> achats = dao.getAchatCryptoList(dateDebut, dateFin);
            request.setAttribute("achats", achats);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de la récupération des achats : " + e.getMessage());
        }
        request.getRequestDispatcher("achatListe.jsp").forward(request, response);
    }
}
