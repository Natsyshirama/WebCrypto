<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

            <!DOCTYPE html>
            <html lang="fr">

            <head>
                <meta charset="UTF-8">
                <title>Analyse des utilisateurs</title>
                <style>
                    table {
                        width: 100%;
                        border-collapse: collapse;
                    }
                    
                    th,
                    td {
                        border: 1px solid #ddd;
                        padding: 8px;
                    }
                    
                    th {
                        background-color: #f2f2f2;
                    }
                    
                    form {
                        margin-bottom: 20px;
                    }
                    
                    label {
                        margin-right: 10px;
                        font-weight: bold;
                    }
                    
                    input[type="datetime-local"] {
                        padding: 5px;
                    }
                    
                    button {
                        padding: 8px 12px;
                        background-color: #007bff;
                        color: white;
                        border: none;
                        cursor: pointer;
                    }
                    
                    button:hover {
                        background-color: #0056b3;
                    }
                </style>
            </head>

            <body>
                <h1>Analyse des Utilisateurs</h1>

                <form method="get" action="analyseUser">
                    <label for="dateMax">Date et heure max :</label>
                    <input type="datetime-local" id="dateMax" name="dateMax" value="${param.dateMax}">
                    <button type="submit">Filtrer</button>
                </form>

                <table>
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
                                    <td colspan=" 6 " style="text-align: center; ">Aucun résultat trouvé.</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </body>

            </html>