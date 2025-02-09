package controller.cryptoController;

import model.crypto.VenteCrypto;
import model.crypto.VenteCryptoDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

public class VenteListeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String dateDebutParam = request.getParameter("dateDebut");
        String dateFinParam = request.getParameter("dateFin");

        Timestamp dateDebut = null;
        Timestamp dateFin = null;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (dateDebutParam != null && !dateDebutParam.trim().isEmpty()) {
                dateDebut = new Timestamp(sdf.parse(dateDebutParam).getTime());
            }
            if (dateFinParam != null && !dateFinParam.trim().isEmpty()) {
                dateFin = new Timestamp(sdf.parse(dateFinParam).getTime());
                dateFin.setTime(dateFin.getTime() + (23 * 3600 + 59 * 60 + 59) * 1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        VenteCryptoDAO dao = new VenteCryptoDAO();
        try {
            List<VenteCrypto> ventes = dao.getVenteCryptoList(dateDebut, dateFin);
            request.setAttribute("ventes", ventes);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de la récupération des Ventes : " + e.getMessage());
        }
        request.getRequestDispatcher("venteListe.jsp").forward(request, response);
    }
}
