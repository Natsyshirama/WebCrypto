<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <!DOCTYPE html>
    <html lang="fr">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Gestion Crypto</title>
        <link rel="stylesheet" href="Styles/styles.css">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" rel="stylesheet">
    </head>

    <body>
        <div class="container">
            <header class="crypto-header">
                <h1> Retraits</h1>
            </header>
            <li><a href="profil">Profil</a></li>

            <ul class="crypto-menu">
                <li>
                    <a href="fond/retrait" class="menu-link">
                        <i class="fas fa-money-bill-transfer"></i> Liste des retraits(Back)
                    </a>
                </li>
                <li>
                    <a href="fond/formInsert" class="menu-link">
                        <i class="fas fa-plus-circle"></i> Nouveau retrait(Front)
                    </a>
                </li>
            </ul>

            <footer class="crypto-footer">
                © 2025 Plateforme Crypto. Tous droits réservés.
            </footer>
        </div>
    </body>

    </html>