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
                <h1>Retraits et Gestion Crypto</h1>
            </header>

            <nav>
                <ul class="crypto-menu">
                    <li>
                        <a href="profil" class="menu-link">
                            <i class="fas fa-user"></i> Profil
                        </a>
                    </li>
                    <li>
                        <a href="fond/retrait" class="menu-link">
                            <i class="fas fa-money-bill-transfer"></i> Liste des Retraits
                        </a>
                    </li>
                    <li>
                        <a href="fond/depot" class="menu-link">
                            <i class="fas fa-money-bill-wave"></i> Liste des Dépôts
                        </a>
                    </li>
                    <li>
                        <a href="fond/formInsert" class="menu-link">
                            <i class="fas fa-plus-circle"></i> Nouveau Retrait
                        </a>
                    </li>
                    <li>
                        <a href="fond/formInsertDepot" class="menu-link">
                            <i class="fas fa-plus-circle"></i> Nouveau Dépôt
                        </a>
                    </li>
                    <li>
                        <a href="crypto/formAchatCrypto" class="menu-link">
                            <i class="fas fa-plus-circle"></i> Achat Crypto
                        </a>
                    </li>
                    <li>
                        <a href="crypto/formVenteCrypto" class="menu-link">
                            <i class="fas fa-plus-circle"></i> Vente Crypto
                        </a>
                    </li>
                    <li>
                        <a href="crypto/achatListe" class="menu-link">
                            <i class="fas fa-list-alt"></i> Liste Achat Crypto
                        </a>
                    </li>
                    <li>
                        <a href="crypto/venteListe" class="menu-link">
                            <i class="fas fa-list-alt"></i> Liste Vente Crypto
                        </a>
                    </li>
                    <li>
                        <a href="crypto/analyseCrypto" class="menu-link">
                            <i class="fas fa-chart-line"></i> Analyse Crypto
                        </a>
                    </li>
                    <li>
                        <a href="crypto/analyseUser.jsp" class="menu-link">
                            <i class="fas fa-chart-pie"></i> Analyse Utilisateur
                        </a>
                    </li>
                </ul>
            </nav>

            <footer class="crypto-footer">
                © 2025 Plateforme Crypto. Tous droits réservés.
            </footer>
        </div>
    </body>

    </html>