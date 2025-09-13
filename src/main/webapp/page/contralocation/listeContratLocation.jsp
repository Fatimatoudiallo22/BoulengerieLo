<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Liste des Contrats</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body {
      background-color: #f8f9fa;
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      padding: 40px;
    }
    h1 {
      text-align: center;
      margin-bottom: 30px;
      color: #0d6efd;
    }
    .table-container {
      max-width: 1000px;
      margin: auto;
      background: #fff;
      padding: 25px;
      border-radius: 12px;
      box-shadow: 0 0 15px rgba(0,0,0,0.1);
    }
    .btn-sm {
      margin: 2px;
    }
    .top-actions {
      display: flex;
      justify-content: flex-end;
      margin-bottom: 15px;
    }
  </style>
</head>
<body>

<div class="table-container">
  <h1>Liste des Contrats de Location</h1>

  <div class="top-actions">
    <a href="ContratLocation?action=add" class="btn btn-success">➕ Ajouter un nouveau contrat</a>
  </div>

  <table class="table table-striped table-hover align-middle">
    <thead class="table-primary">
    <tr>
      <th>ID</th>
      <th>Nom du Contrat</th>
      <th>Locataire</th>
      <th>Unité</th>
      <th>Date début</th>
      <th>Date fin</th>
      <th>Montant</th>
      <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="contrat" items="${contrats}">
      <tr>
        <td>${contrat.id}</td>
        <td>${contrat.nom}</td>
        <td>${contrat.locataire.nom}</td>
        <td>${contrat.uniteLocation.nom}</td>
        <td>${contrat.dateDebut}</td>
        <td>${contrat.dateFin}</td>
        <td>${contrat.montant} FCFA</td>
        <td>
          <a href="ContratLocation?action=details&id=${contrat.id}" class="btn btn-info btn-sm">👁 Détails</a>

          <c:if test="${role ne 'LOCATAIRE'}">
            <a href="ContratLocation?action=edit&id=${contrat.id}" class="btn btn-warning btn-sm">✏ Modifier</a>
            <form action="ContratLocation" method="post" style="display:inline;">
              <input type="hidden" name="action" value="delete"/>
              <input type="hidden" name="id" value="${contrat.id}"/>
              <button type="submit" class="btn btn-danger btn-sm"
                      onclick="return confirm('Voulez-vous supprimer ce contrat ?');">🗑 Supprimer</button>
            </form>
          </c:if>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>

</body>
</html>
