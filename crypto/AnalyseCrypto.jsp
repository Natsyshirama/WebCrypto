<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

        <!DOCTYPE html>
        <html lang="fr">

        <head>
            <meta charset="UTF-8">
            <title>Analyse des Cours Historiques</title>
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

            <section id="analyse-cours">
                <h1>Analyse des Cours Historiques</h1>

                <form action="analyseCrypto" method="get" class="analyse-form">
                    <div class="form-group">
                        <label for="typeAnalyse">Type d'analyse :</label>
                        <select id="typeAnalyse" name="typeAnalyse" required>
                    <option value="quartile">1er Quartile</option>
                    <option value="max">Maximum</option>
                    <option value="min">Minimum</option>
                    <option value="moyenne">Moyenne</option>
                    <option value="ecartType">Écart-type</option>
                </select>
                    </div>

                    <fieldset>
                        <legend>Cryptomonnaies :</legend>
                        <input type="checkbox" name="crypto" value="tous" id="cryptoTous">
                        <label for="cryptoTous">Tous</label>
                        <c:forEach var="crypto" items="${cryptos}">
                            <input type="checkbox" name="crypto" value="${crypto.id}" id="crypto${crypto.id}">
                            <label for="crypto${crypto.id}">${crypto.nom}</label>
                        </c:forEach>
                    </fieldset>

                    <div class="form-group">
                        <label for="dateMin">Date et heure min :</label>
                        <input type="datetime-local" id="dateMin" name="dateMin">
                    </div>

                    <div class="form-group">
                        <label for="dateMax">Date et heure max :</label>
                        <input type="datetime-local" id="dateMax" name="dateMax">
                    </div>

                    <button type="submit" class="btn-submit">Valider</button>
                </form>

                <c:if test="${not empty analyse}">
                    <h2>Type d'analyse effectué : ${analyse}</h2>
                </c:if>

                <c:if test="${not empty resultats}">
                    <h2>Résultats de l'analyse</h2>
                    <c:forEach var="entry" items="${resultats}">
                        <p>${entry.key} : ${entry.value}</p>
                    </c:forEach>
                </c:if>

                <c:if test="${not empty error}">
                    <h2 style="color: red;">Erreur</h2>
                    <p>${error}</p>
                </c:if>
            </section>

            <footer>
                <p>© 2023 Plateforme Crypto. Tous droits réservés.</p>
            </footer>
        </body>

        </html>