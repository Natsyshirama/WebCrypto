<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

        <!DOCTYPE html>
        <html lang="fr">

        <head>
            <meta charset="UTF-8">
            <title>Liste des Ventes Crypto</title>
            <link rel="stylesheet" href="styles.css">
        </head>

        <body>
            <header>
                <h1>Plateforme Crypto</h1>
                <nav>
                    <ul>
                        <li><a href="#market">Marché</a></li>
                        <li><a href="#wallet">Portefeuille</a></li>
                        <li><a href="#trade">Achat/Vente</a></li>
                        <li><a href="logout">Déconnexion</a></li>
                    </ul>
                </nav>
            </header>

            <section id="vente-liste">
                <h1>Liste des Ventes Crypto</h1>

                <form action="venteListe" method="get" class="filter-form">
                    <div class="form-group">
                        <label for="dateDebut">Date de début :</label>
                        <input type="date" id="dateDebut" name="dateDebut">
                    </div>
                    <div class="form-group">
                        <label for="dateFin">Date de fin :</label>
                        <input type="date" id="dateFin" name="dateFin">
                    </div>
                    <button type="submit" class="btn-submit">Filtrer</button>
                </form>

                <c:if test="${not empty error}">
                    <p class="error-message">${error}</p>
                </c:if>

                <table class="retrait-table">
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
            </section>

            <footer>
                <p>© 2023 Plateforme Crypto. Tous droits réservés.</p>
            </footer>
        </body>

        </html>