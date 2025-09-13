<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Détails Paiement</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body {
      background-color: #f8f9fa;
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      padding: 40px;
    }
    .details-card {
      max-width: 600px;
      margin: auto;
      padding: 30px;
      background-color: #ffffff;
      border-radius: 12px;
      box-shadow: 0 0 15px rgba(0,0,0,0.1);
    }
    h1 {
      text-align: center;
      margin-bottom: 25px;
      color: #0d6efd;
    }
    .detail-item {
      margin-bottom: 15px;
      font-size: 16px;
    }
    .detail-item strong {
      width: 160px;
      display: inline-block;
      color: #495057;
    }
    .btn-group {
      display: flex;
      justify-content: space-between;
      margin-top: 20px;
    }
  </style>
</head>
<body>

<div class="details-card">
  <h1>Détails du Paiement</h1>

  <div class="detail-item"><strong>ID:</strong> ${paiement.id}</div>
  <div class="detail-item"><strong>Contrat:</strong> ${paiement.contratLocation.id}</div>
  <div class="detail-item"><strong>Montant:</strong> ${paiement.prix} FCFA</div>
  <div class="detail-item"><strong>Date paiement:</strong> ${paiement.datepaiement}</div>
  <div class="detail-item"><strong>Mode:</strong> ${paiement.modePaiement}</div>
  <div class="detail-item"><strong>Status:</strong> ${paiement.statusPaiement}</div>

  <div class="btn-group">
    <a href="paiement" class="btn btn-secondary">⬅ Retour</a>
    <a href="paiement?action=edit&id=${paiement.id}" class="btn btn-warning">✏ Modifier</a>
  </div>
</div>

</body>
</html>
