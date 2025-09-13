<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="gestion.etudiant.loimmobilier.model.Utilisateur" %>
<html>
<head>
    <title>Liste des Utilisateurs</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            padding: 20px;
            background-color: #f8f9fa;
        }

        h2 {
            margin-bottom: 20px;
        }

        a.btn-add {
            margin-bottom: 15px;
        }

        /* S'assurer que les colonnes s'adaptent sans scroll horizontal */
        .table-container {
            overflow-x: auto;
        }

        table th, table td {
            white-space: nowrap; /* empêche les cellules de s’étendre sur plusieurs lignes */
        }
    </style>
</head>
<body>

<h2>Liste des Utilisateurs</h2>

<a href="utilisateur?action=add" class="btn btn-primary btn-add">➕ Ajouter un utilisateur</a>

<div class="table-container">
    <table class="table table-striped table-hover table-bordered align-middle">
        <thead class="table-dark">
        <tr>

            <th>Nom</th>
            <th>Prénom</th>
            <th>Adresse</th>
            <th>Email</th>
            <th>Téléphone</th>
            <th>Rôle</th>
            <th>Date inscription</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Utilisateur> utilisateurs = (List<Utilisateur>) request.getAttribute("utilisateurs");
            if (utilisateurs != null) {
                for (Utilisateur u : utilisateurs) {
        %>
        <tr>

            <td><%= u.getNom() %></td>
            <td><%= u.getPrenom() %></td>
            <td><%= u.getAdresse() %></td>
            <td><%= u.getEmail() %></td>
            <td><%= u.getTelephone() %></td>
            <td><%= u.getRole() %></td>
            <td><%= u.getDateInscription() %></td>
            <td>
                <a href="utilisateur?action=edit&id=<%= u.getId() %>" class="btn btn-sm btn-warning">✏ Modifier</a>
                <a href="utilisateur?action=delete&id=<%= u.getId() %>" class="btn btn-sm btn-danger"
                   onclick="return confirm('Supprimer cet utilisateur ?');">🗑 Supprimer</a>
            </td>
        </tr>
        <%      }
        }
        %>
        </tbody>
    </table>
</div>

</body>
</html>
