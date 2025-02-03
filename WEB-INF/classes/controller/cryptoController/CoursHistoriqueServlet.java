package controller.cryptoController;

import com.google.gson.Gson;
import model.crypto.CoursHistorique;
import model.crypto.CoursHistoriqueDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CoursHistoriqueServlet extends HttpServlet {
    @Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");

    String idCryptoParam = req.getParameter("idCrypto");

    try {
        if (idCryptoParam == null || idCryptoParam.trim().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID Cryptomonnaie manquant.");
            return;
        }

        int idCrypto = Integer.parseInt(idCryptoParam);
        CoursHistoriqueDAO dao = new CoursHistoriqueDAO();
        List<CoursHistorique> coursList = dao.getCoursByIdCryptomonnaie(idCrypto);

        String json = new Gson().toJson(coursList);
        resp.getWriter().print(json);

    } catch (NumberFormatException e) {
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID Cryptomonnaie invalide.");
    } catch (Exception e) {
        e.printStackTrace();
        resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de la récupération des données.");
    }
}

}
