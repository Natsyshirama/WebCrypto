<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <!DOCTYPE html>
        <html lang="fr">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Liste des Demandes de Validation de Depots</title>
            <link rel="stylesheet" href="retrait.css">
            <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
        </head>

        <body>
            <header>
                <h1>Plateforme Crypto</h1>
                <nav>
                    <ul>
                        <li><a href="#market">Marché</a></li>
                        <li><a href="#wallet">Portefeuille</a></li>
                        <li><a href="#trade">Achat/Vente</a></li>
                    </ul>
                </nav>
            </header>

            <section id="liste-retraits">
                <h1>Liste des Depots Non Validés</h1>
                <p class="message">${message}</p>

                <table class="retrait-table">
                    <thead>
                        <tr>
                            <th>ID Depot</th>
                            <th>Solde</th>
                            <th>Date</th>
                            <th>ID Utilisateur</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="depot" items="${depots}">
                            <tr>
                                <td>${depot.idDepot}</td>
                                <td>${depot.solde}</td>
                                <td>${depot.daty}</td>
                                <td>${depot.idUtilisateur}</td>
                                <td>
                                    <form action="valideDepot" method="post">
                                        <input type="hidden" name="idDepot" value="${depot.idDepot}">
                                        <button type="submit" class="btn-valider">
                                    <i class="fas fa-check"></i> Valider
                                </button>
                                    </form>

                                </td>
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