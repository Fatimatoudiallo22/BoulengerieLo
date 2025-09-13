<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Formulaire Contrat</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body {
      background-color: #f8f9fa;
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      padding: 40px;
    }
    .form-container {
      max-width: 700px;
      margin: auto;
      padding: 30px;
      background-color: #ffffff;
      border-radius: 10px;
      box-shadow: 0 0 15px rgba(0,0,0,0.1);
    }
    h1 {
      text-align: center;
      margin-bottom: 30px;
      color: #0d6efd;
    }
    .btn-group {
      display: flex;
      justify-content: space-between;
    }
  </style>
</head>
<body>

<div class="form-container">
  <h1>${contrat != null ? "Modifier le contrat" : "Ajouter un nouveau contrat"}</h1>

  <form action="ContratLocation" method="post">
    <input type="hidden" name="id" value="${contrat.id}"/>
    <input type="hidden" name="action" value="${contrat != null ? 'edit' : 'add'}"/>

    <div class="mb-3">
      <label class="form-label">Date Début :</label>
      <input type="datetime-local" class="form-control" name="dateDebut" value="${contrat.dateDebut}" required/>
    </div>
    <div class="mb-3">
      <label for="nom" class="form-label">Nom :</label>
      <input type="text" class="form-control" id="nom" name="nom" value="${contrat.nom}" required/>
    </div>
    <div class="mb-3">
      <label class="form-label">Date Fin :</label>
      <input type="datetime-local" class="form-control" name="dateFin" value="${contrat.dateFin}" required/>
    </div>

    <div class="mb-3">
      <label class="form-label">Montant :</label>
      <input type="number" class="form-control" name="montant" value="${contrat.montant}" required/>
    </div>

    <div class="mb-3">
      <label class="form-label">Locataire :</label>
      <select class="form-select" name="locataireId" required>
        <c:forEach var="locataire" items="${locataires}">
          <option value="${locataire.id}" ${contrat.locataire != null && contrat.locataire.id == locataire.id ? 'selected' : ''}>
              ${locataire.nom}
          </option>
        </c:forEach>
      </select>
    </div>

    <div class="mb-3">
      <label class="form-label">Unité :</label>
      <select class="form-select" name="uniteId" required>
        <c:forEach var="unite" items="${unites}">
          <option value="${unite.id}" ${contrat.uniteLocation != null && contrat.uniteLocation.id == unite.id ? 'selected' : ''}>
              ${unite.nom}
          </option>
        </c:forEach>
      </select>
    </div>

    <div class="btn-group">
      <button type="submit" class="btn btn-primary">💾 Enregistrer</button>
      <a href="ContratLocation" class="btn btn-secondary">↩ Retour à la liste</a>
    </div>
  </form>
</div>

</body>
</html>
