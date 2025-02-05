<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

        <!DOCTYPE html>
        <html lang="fr">

        <head>
            <meta charset="UTF-8">
            <title>Liste des Vente Crypto</title>
            <link rel="stylesheet" href="styles.css">
        </head>

        <body>
            <h1>Liste des Ventes Crypto</h1>

            <form action="venteListe" method="get">
                <label for="dateDebut">Date de début :</label>
                <input type="date" id="dateDebut" name="dateDebut">
                <label for="dateFin">Date de fin :</label>
                <input type="date" id="dateFin" name="dateFin">
                <button type="submit">Filtrer</button>
            </form>

            <c:if test="${not empty error}">
                <p style="color: red;">${error}</p>
            </c:if>

            <table border="1">
                <thead>
                    <tr>
                        <th>ID Vente</th>
                        <th>Nb Crypto</th>
                        <th>Cours</th>
                        <th>Date</th>
                        <th>ID Cryptomonnaie</th>
                        <th>ID Utilisateur</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="vente" items="${ventes}">
                        <tr>
                            <td>${vente.idVente}</td>
                            <td>${vente.nbCrypto}</td>
                            <td>${vente.cours}</td>
                            <td>${vente.daty}</td>
                            <td>${vente.idCryptomonnaie}</td>
                            <td>${vente.idUtilisateur}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </body>

        </html>