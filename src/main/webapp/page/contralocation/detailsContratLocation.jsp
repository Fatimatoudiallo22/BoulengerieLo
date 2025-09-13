<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Détails du Contrat</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body {
      background-color: #f8f9fa;
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      padding: 40px;
    }
    .details-container {
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
    .detail-item {
      padding: 10px 0;
      border-bottom: 1px solid #e9ecef;
    }
    .detail-item:last-child {
      border-bottom: none;
    }
    .btn-group {
      display: flex;
      justify-content: space-between;
      margin-top: 20px;
    }
  </style>
</head>
<body>

<div class="details-container">
  <h1>Détails du Contrat</h1>

  <div class="detail-item"><strong>ID :</strong> ${contrat.id}</div>
  <div class="detail-item"><strong>Nom :</strong> ${contrat.nom}</div>
  <div class="detail-item"><strong>Date Début :</strong> ${contrat.dateDebut}</div>
  <div class="detail-item"><strong>Date Fin :</strong> ${contrat.dateFin}</div>
  <div class="detail-item"><strong>Montant :</strong> ${contrat.montant} FCFA</div>
  <div class="detail-item"><strong>Locataire :</strong> ${contrat.locataire.nom}</div>
  <div class="detail-item"><strong>Unité :</strong> ${contrat.uniteLocation.nom}</div>

  <div class="btn-group">
    <a href="ContratLocation" class="btn btn-secondary">↩ Retour à la liste</a>
    <a href="ContratLocation?action=edit&id=${contrat.id}" class="btn btn-primary">✏️ Modifier</a>
  </div>
</div>

</body>
</html>
