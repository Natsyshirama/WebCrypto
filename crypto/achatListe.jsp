<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

        <!DOCTYPE html>
        <html lang="fr">

        <head>
            <meta charset="UTF-8">
            <title>Liste des Achats Crypto</title>
            <link rel="stylesheet" href="styles.css">
        </head>

        <body>
            <h1>Liste des Achats Crypto</h1>

            <form action="achatListe" method="get">
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
                        <th>ID Achat</th>
                        <th>Nb Crypto</th>
                        <th>Cours</th>
                        <th>Date</th>
                        <th>ID Cryptomonnaie</th>
                        <th>ID Utilisateur</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="achat" items="${achats}">
                        <tr>
                            <td>${achat.idAchat}</td>
                            <td>${achat.nbCrypto}</td>
                            <td>${achat.cours}</td>
                            <td>${achat.daty}</td>
                            <td>${achat.idCryptomonnaie}</td>
                            <td>${achat.idUtilisateur}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </body>

        </html>