<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Nouvelle Demande de Location</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-4">

<h2>Nouvelle Demande de Location</h2>

<form action="${pageContext.request.contextPath}/demandeLocation" method="post">
  <div class="mb-3">
    <label for="locataireId" class="form-label">ID Locataire</label>
    <input type="number" class="form-control" id="locataireId" name="locataireId" required>
  </div>

  <div class="mb-3">
    <label for="uniteId" class="form-label">ID Unité</label>
    <input type="number" class="form-control" id="uniteId" name="uniteId" required>
  </div>

  <div class="mb-3">
    <label for="statut" class="form-label">Statut</label>
    <select class="form-select" id="statut" name="statut">
      <option value="EN_ATTENTE">En attente</option>
      <option value="ACCEPTEE">Acceptée</option>
      <option value="REFUSEE">Refusée</option>
    </select>
  </div>

  <button type="submit" class="btn btn-success">Enregistrer</button>
  <a href="${pageContext.request.contextPath}/demandeLocation" class="btn btn-secondary">Annuler</a>
</form>

</body>
</html>
