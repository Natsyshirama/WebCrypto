<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

		<!DOCTYPE html>
		<html lang="en">

		<head>
			<meta charset="UTF-8">
			<title>Vendre une Cryptomonnaie</title>
		</head>

		<body>
			<h1>AVendre une Cryptomonnaie</h1>
			<div class="solde-info">
				<p>Solde disponible : <strong>${solde} €</strong></p>
			</div>
							<script type="module" src="mvt_vendre_crypto.js"></script>

			<!-- Formulaire d'achat -->
			<form action="vendreCrypto" method="post">
				<label for="idCrypto">Choisissez votre cryptomonnaie :</label>
				<select name="idCrypto" required>
					<c:forEach var="crypto" items="${myCryptos}">
						<option value="${crypto.idCrypto}"
							data-max="${crypto.quantite}"
							data-cours="${crypto.coursActuel}">
							${crypto.nom} - Possédé : ${crypto.quantite}
						</option>
					</c:forEach>
				</select>

				<br><br>

				<label for="quantite">Quantité :</label>
				<input type="number" step="0.000000001" name="quantite" required>

				<br><br>

				<input type="submit" value="Vendre">
			</form>

			<!-- Message de confirmation ou d'erreur -->
			<c:if test="${not empty message}">
				<p><strong>${message}</strong></p>
			</c:if>

			<hr>

			<!-- Affichage des cryptos possédées -->
			<h2>Vos cryptomonnaies</h2>
			<table border="1">
				<thead>
					<tr>
						<th>Nom</th>
						<th>Cours</th>
						<th>Quantité possédée</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="crypto" items="${cryptosUser}">
						<tr>
							<td>${crypto.nom}</td>
							<td>${crypto.cours}</td>
							<td>${crypto.totalCrypto}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</body>

		</html>