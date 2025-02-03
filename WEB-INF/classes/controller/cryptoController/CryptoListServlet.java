package controller.cryptoController;

import com.google.gson.Gson;
import model.crypto.Crypto;
import model.crypto.CoursHistoriqueDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CryptoListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            CoursHistoriqueDAO dao = new CoursHistoriqueDAO();
            List<Crypto> cryptoList = dao.getAllCryptomonnaies();

            String json = new Gson().toJson(cryptoList);
            PrintWriter out = resp.getWriter();
            out.print(json);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de la récupération des cryptomonnaies.");
        }
    }
}
