<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>${immeuble != null ? "Modifier" : "Ajouter"} Immeuble</title>
  <!-- Lien vers Bootstrap CSS -->
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
  <h1>${immeuble != null ? "Modifier" : "Ajouter"} Immeuble</h1>

  <form action="Immeuble" method="post">
    <input type="hidden" name="id" value="${immeuble != null ? immeuble.id : ''}"/>

    <div class="mb-3">
      <label for="nom" class="form-label">Nom de l'immeuble :</label>
      <input type="text" class="form-control" id="nom" name="nom" value="${immeuble != null ? immeuble.nom : ''}" required>
    </div>

    <div class="mb-3">
      <label for="adresse" class="form-label">Adresse :</label>
      <input type="text" class="form-control" id="adresse" name="adresse" value="${immeuble != null ? immeuble.adresse : ''}" required>
    </div>

    <div class="mb-3">
      <label for="nbreUnite" class="form-label">Nombre d'unités :</label>
      <input type="number" class="form-control" id="nbreUnite" name="nbreUnite" value="${immeuble != null ? immeuble.nbreunite : ''}" required>
    </div>

    <div class="mb-3">
      <label for="equipementdispo" class="form-label">Équipements :</label>
      <input type="text" class="form-control" id="equipementdispo" name="equipementdispo" value="${immeuble != null ? immeuble.equipementdispo : ''}">
    </div>

    <div class="mb-3">
      <label for="description" class="form-label">Description :</label>
      <textarea class="form-control" id="description" name="description">${immeuble != null ? immeuble.description : ''}</textarea>
    </div>
      <div>
        <label>Image :</label>
        <input type="file" name="image">
      </div>


    <div class="btn-group">
      <button type="submit" class="btn btn-primary">💾 Enregistrer</button>
      <a href="Immeuble" class="btn btn-secondary">↩ Retour à la liste</a>
    </div>
  </form>
</div>

</body>
</html>
