<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>Profil Utilisateur</title>
            <link rel="stylesheet" href="Styles/styles.css">
            <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
        </head>

        <body>
            <header>
                <h1>Profil Utilisateur</h1>
                <nav>
                    <ul>
                        <li><a href="insertRetrait">Retrait</a></li>
                        <li><a href="logout">Déconnexion</a></li>
                    </ul>
                </nav>
            </header>

            <section id="profil">
                <div class="profile-card">
                    <div class="profile-header">
                        <i class="fas fa-user-circle fa-5x"></i>
                        <h2>${user.nom}</h2>
                    </div>

                    <div class="profile-info">
                        <p><strong>ID:</strong> ${user.id}</p>
                        <p><strong>Email:</strong> ${user.email}</p>
                    </div>
                </div>
            </section>

            <footer>
                <p>© 2023 Plateforme Crypto. Tous droits réservés.</p>
            </footer>
        </body>

        </html>