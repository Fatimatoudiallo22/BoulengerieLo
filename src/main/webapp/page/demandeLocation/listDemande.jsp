<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Liste des Demandes</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-4">

<h2>Liste des Demandes de Location</h2>

<a href="${pageContext.request.contextPath}/demandeLocation?action=add" class="btn btn-success mb-3">Nouvelle Demande</a>

<table class="table table-bordered table-striped">
    <thead>
    <tr>
        <th>ID</th>a
        <th>Locataire (ID)</th>
        <th>Unité (ID)</th>
        <th>Date Demande</th>
        <th>Statut</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="d" items="${demandes}">
        <tr>
            <td>${d.id}</td>
            <td>${d.locataire.id}</td>
            <td>${d.uniteLocation.id}</td>
            <td>${d.dateDemande}</td>
            <td>${d.statut}</td>
            <td>
                <a href="${pageContext.request.contextPath}/demandeLocation?action=edit&id=${d.id}" class="btn btn-primary btn-sm">Modifier</a>
                <a href="${pageContext.request.contextPath}/demandeLocation?action=delete&id=${d.id}" class="btn btn-danger btn-sm"
                   onclick="return confirm('Voulez-vous vraiment supprimer cette demande ?')">Supprimer</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
