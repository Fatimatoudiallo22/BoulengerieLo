<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Ajouter Utilisateur</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body {
      background-color: #f8f9fa;
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      padding: 40px;
    }

    .form-container {
      max-width: 600px;
      margin: auto;
      padding: 30px;
      background-color: #ffffff;
      border-radius: 10px;
      box-shadow: 0 0 15px rgba(0,0,0,0.1);
    }

    h2 {
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
  <h2>Ajouter un nouvel utilisateur</h2>

  <form action="utilisateur" method="post">
    <div class="mb-3">
      <label for="nom" class="form-label">Nom :</label>
      <input type="text" class="form-control" id="nom" name="nom" required>
    </div>

    <div class="mb-3">
      <label for="prenom" class="form-label">Prénom :</label>
      <input type="text" class="form-control" id="prenom" name="prenom" required>
    </div>

    <div class="mb-3">
      <label for="adresse" class="form-label">Adresse :</label>
      <input type="text" class="form-control" id="adresse" name="adresse">
    </div>

    <div class="mb-3">
      <label for="email" class="form-label">Email :</label>
      <input type="email" class="form-control" id="email" name="email" required>
    </div>

    <div class="mb-3">
      <label for="telephone" class="form-label">Téléphone :</label>
      <input type="text" class="form-control" id="telephone" name="telephone">
    </div>

    <div class="mb-3">
      <label for="password" class="form-label">Password :</label>
      <input type="password" class="form-control" id="password" name="password" required>
    </div>

    <div class="mb-3">
      <label for="role" class="form-label">Rôle :</label>
      <select class="form-select" id="role" name="role" required>
        <option value="">-- Sélectionner un rôle --</option>
        <option value="ADMIN">ADMIN</option>
        <option value="PROPRIO">PROPRIO</option>
        <option value="LOCATAIRE">LOCATAIRE</option>
      </select>
    </div>

    <div class="btn-group">
      <button type="submit" class="btn btn-success">✅ Enregistrer</button>
      <a href="utilisateur" class="btn btn-secondary">↩ Retour</a>
    </div>
  </form>
</div>

</body>
</html>
