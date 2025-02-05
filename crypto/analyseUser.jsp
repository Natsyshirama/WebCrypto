<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

            <!DOCTYPE html>
            <html lang="fr">

            <head>
                <meta charset="UTF-8">
                <title>Analyse des utilisateurs</title>
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

                <section id="analyse-utilisateurs">
                    <h1>Analyse des Utilisateurs</h1>

                    <form method="get" action="analyseUser" class="filter-form">
                        <div class="form-group">
                            <label for="dateMax">Date et heure max :</label>
                            <input type="datetime-local" id="dateMax" name="dateMax" value="${param.dateMax}">
                        </div>
                        <button type="submit" class="btn-submit">Filtrer</button>
                    </form>

                    <table class="result-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nom</th>
                                <th>Email</th>
                                <th>Montant Total Achat</th>
                                <th>Montant Total Vente</th>
                                <th>Solde</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty analyses}">
                                    <c:forEach var="analysis" items="${analyses}">
                                        <tr>
                                            <td>${analysis.userId}</td>
                                            <td>${analysis.nom}</td>
                                            <td>${analysis.email}</td>
                                            <td>
                                                <fmt:formatNumber value="${analysis.totalAchat}" type="currency" currencySymbol="Ar" />
                                            </td>
                                            <td>
                                                <fmt:formatNumber value="${analysis.totalVente}" type="currency" currencySymbol="Ar" />
                                            </td>
                                            <td>
                                                <fmt:formatNumber value="${analysis.solde}" type="currency" currencySymbol="Ar" />
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="6" style="text-align: center;">Aucun résultat trouvé.</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </section>

                <footer>
                    <p>© 2023 Plateforme Crypto. Tous droits réservés.</p>
                </footer>
            </body>

            </html>