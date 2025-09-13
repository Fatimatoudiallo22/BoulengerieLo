<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Modifier Demande</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-4">

<h2>Modifier Demande</h2>

<form action="${pageContext.request.contextPath}/demandeLocation" method="post">
  <input type="hidden" name="id" value="${demande.id}"/>

  <div class="mb-3">
    <label for="locataireId" class="form-label">ID Locataire</label>
    <input type="number" class="form-control" id="locataireId" name="locataireId"
           value="${demande.locataire.id}" required>
  </div>

  <div class="mb-3">
    <label for="uniteId" class="form-label">ID Unité</label>
    <input type="number" class="form-control" id="uniteId" name="uniteId"
           value="${demande.uniteLocation.id}" required>
  </div>

  <div class="mb-3">
    <label for="statut" class="form-label">Statut</label>
    <select class="form-select" id="statut" name="statut">
      <option value="EN_ATTENTE" ${demande.statut == 'EN_ATTENTE' ? 'selected' : ''}>En attente</option>
      <option value="ACCEPTEE" ${demande.statut == 'ACCEPTEE' ? 'selected' : ''}>Acceptée</option>
      <option value="REFUSEE" ${demande.statut == 'REFUSEE' ? 'selected' : ''}>Refusée</option>
    </select>
  </div>

  <button type="submit" class="btn btn-primary">Modifier</button>
  <a href="${pageContext.request.contextPath}/demandeLocation" class="btn btn-secondary">Annuler</a>
</form>

</body>
</html>
