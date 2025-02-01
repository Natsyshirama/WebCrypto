<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <!DOCTYPE html>
    <html lang="fr">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Depot</title>
        <link rel="stylesheet" href="styles.css">
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
                    <li><a href="logout">Déconnexion</a></li>
                </ul>
            </nav>
        </header>

        <section id="retrait">
            <h1>Nouveau Depot</h1>
            <p class="message">${message}</p>

            <div class="solde-info">
                <p>Solde disponible : <strong>${solde} €</strong></p>
            </div>

            <form action="insertDepot" method="post" class="retrait-form">
                <div class="form-group">
                    <label for="solde">Montant du depot :</label>
                    <input type="number" step="0.01" name="solde" id="solde" required>
                </div>

                <button type="submit" class="btn-submit">
                <i class="fas fa-money-bill-transfer"></i> Enregistrer
            </button>
            </form>
        </section>

        <footer>
            <p>© 2023 Plateforme Crypto. Tous droits réservés.</p>
        </footer>
    </body>

    </html>