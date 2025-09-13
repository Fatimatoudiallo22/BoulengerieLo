<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Liste des Immeubles</title>
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
<h1>Liste des Immeubles</h1>

<!-- Bouton Ajouter visible seulement pour ADMIN et PROPRIO -->
<c:if test="${sessionScope.user.role == 'ADMIN' || sessionScope.user.role == 'PROPRIO'}">
    <a href="Immeuble?action=add">➕ Ajouter un immeuble</a>
</c:if>

<br/><br/>

<div class="table-container">
    <table class="table table-striped table-hover table-bordered align-middle">
        <thead class="table-dark">
    <tr>

        <th>Nom</th>
        <th>Adresse</th>
        <th>Nombre d'unités</th>
        <th>Équipements</th>
        <th>Description</th>
        <th>Image</th>
        <th>Actions</th>
    </tr>
        </thead>

    <c:forEach var="immeuble" items="${immeubles}">
        <tr>

            <td>${immeuble.nom}</td>
            <td>${immeuble.adresse}</td>
            <td>${immeuble.nbreunite}</td>
            <td>${immeuble.equipementdispo}</td>
            <td>${immeuble.description}</td>
            <td>
                <c:if test="${not empty immeuble.image}">
                    <img src="${immeuble.image}" alt="Image" class="img-fluid rounded" style="max-width: 100%; height: auto;">

                </c:if>
            </td>
            <td>
                <!-- ADMIN -->
                <c:if test="${sessionScope.user.role == 'ADMIN'}">
                    <a href="Immeuble?action=edit&id=${immeuble.id}">Modifier</a> |
                    <a href="Immeuble?action=details&id=${immeuble.id}">Détails</a> |
                    <form action="Immeuble" method="post" style="display:inline">
                        <input type="hidden" name="action" value="delete"/>
                        <input type="hidden" name="id" value="${immeuble.id}"/>
                        <input type="submit" value="Supprimer" onclick="return confirm('Supprimer ?');"/>
                    </form>
                </c:if>

                <!-- PROPRIO -->
                <c:if test="${sessionScope.user.role == 'PROPRIO' && immeuble.proprietaire.id == sessionScope.user.id}">
                    <a href="Immeuble?action=edit&id=${immeuble.id}">Modifier</a>  |
                    <a href="Immeuble?action=details&id=${immeuble.id}">Détails</a> |
                    <form action="Immeuble" method="post" style="display:inline">
                        <input type="hidden" name="action" value="delete"/>
                        <input type="hidden" name="id" value="${immeuble.id}"/>
                        <input type="submit" value="Supprimer" onclick="return confirm('Supprimer ?');"/>
                    </form>
                </c:if>

                <!-- LOCATAIRE -->
                <c:if test="${sessionScope.user.role == 'LOCATAIRE'}">
                    <a href="Immeuble?action=details&id=${immeuble.id}">Détails</a>
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>
</div>
</body>
</html>
