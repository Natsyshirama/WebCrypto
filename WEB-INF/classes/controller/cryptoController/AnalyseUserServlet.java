package controller.cryptoController;

import model.crypto.UserAnalise;
import model.crypto.UserAnalyseDAO;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AnalyseUserServlet extends HttpServlet{

    
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String dateMaxParam = request.getParameter("dateMax");
    UserAnalyseDAO analyseDAO = new UserAnalyseDAO();
    List<UserAnalise> analyses = analyseDAO.getUserAnalise(dateMaxParam);

    request.setAttribute("analyses", analyses);
    request.getRequestDispatcher("analyseUser.jsp").forward(request, response);
}

}