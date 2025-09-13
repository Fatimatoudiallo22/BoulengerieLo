<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>${unite != null ? "Modifier" : "Ajouter"} Unité</title>
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
    }
    .btn-group {
      display: flex;
      justify-content: space-between;
    }
  </style>
</head>
<body>

<div class="form-container">
  <h1>${unite != null ? "Modifier" : "Ajouter"} Unité de Location</h1>

  <form action="UniteLocation" method="post">
    <input type="hidden" name="id" value="${unite != null ? unite.id : ''}"/>

    <div class="mb-3">
      <label class="form-label">Nom:</label>
      <input type="text" class="form-control" name="nom" value="${unite != null ? unite.nom : ''}" required/>
    </div>

    <div class="mb-3">
      <label class="form-label">Numéro:</label>
      <input type="text" class="form-control" name="numero" value="${unite != null ? unite.numero : ''}" required/>
    </div>

    <div class="mb-3">
      <label class="form-label">Nombre de pièces:</label>
      <input type="number" class="form-control" name="nombrePiece" value="${unite != null ? unite.nombrePiece : ''}" required/>
    </div>

    <div class="mb-3">
      <label class="form-label">Surface (m²):</label>
      <input type="number" step="0.01" class="form-control" name="surface" value="${unite != null ? unite.surface : ''}" required/>
    </div>

    <div class="mb-3">
      <label class="form-label">Loyer Mensuel:</label>
      <input type="number" class="form-control" name="loyerMensuel" value="${unite != null ? unite.loyerMensuel : ''}" required/>
    </div>

    <div class="mb-3">
      <label class="form-label">Disponibilité:</label>
      <select class="form-select" name="dispo">
        <option value="LIBRE" ${unite != null && unite.dispo == 'LIBRE' ? 'selected' : ''}>Libre</option>
        <option value="OCCUPE" ${unite != null && unite.dispo == 'OCCUPE' ? 'selected' : ''}>Occupé</option>
      </select>
    </div>

    <div class="mb-3">
      <label class="form-label">Immeuble:</label>
      <select class="form-select" name="immeubleId" required>
        <c:forEach var="immeuble" items="${immeubles}">
          <option value="${immeuble.id}" ${unite != null && unite.immeuble != null && unite.immeuble.id == immeuble.id ? 'selected' : ''}>
              ${immeuble.adresse}
          </option>
        </c:forEach>
      </select>
    </div>

    <form action="UniteLocation" method="post" enctype="multipart/form-data">
      <div class="mb-3">
        <label for="imagePath" class="form-label">Image de l'unité</label>
        <input type="file" class="form-control" id="imagePath" name="imagefile">
      </div>

      <button type="submit" class="btn btn-primary">Enregistrer</button>
    </form>


    <div class="btn-group">
      <button type="submit" class="btn btn-primary">💾 Enregistrer</button>
      <a href="UniteLocation" class="btn btn-secondary">↩ Retour à la liste</a>
    </div>
  </form>
</div>

</body>
</html>
