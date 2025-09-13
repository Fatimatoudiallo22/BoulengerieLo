<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Détails Unité</title>
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
    }
    .detail-item {
      margin-bottom: 15px;
      font-size: 16px;
    }
    .detail-item b {
      width: 200px;
      display: inline-block;
      color: #495057;
    }
    .btn-back {
      display: block;
      margin: auto;
      margin-top: 20px;
    }
  </style>
</head>
<body>

<div class="details-container">
  <h1>Détails de l'Unité de Location</h1>

  <div class="detail-item"><b>ID :</b> ${unite.id}</div>
  <div class="detail-item"><b>Nom :</b> ${unite.nom}</div>
  <div class="detail-item"><b>Numéro :</b> ${unite.numero}</div>
  <div class="detail-item"><b>Nombre de pièces :</b> ${unite.nombrePiece}</div>
  <div class="detail-item"><b>Surface :</b> ${unite.surface} m²</div>
  <div class="detail-item"><b>Loyer Mensuel :</b> ${unite.loyerMensuel} FCFA</div>
  <div class="detail-item"><b>Disponibilité :</b> ${unite.dispo}</div>
  <div class="detail-item"><b>Immeuble :</b> ${unite.immeuble != null ? unite.immeuble.adresse : ''}</div>

  <a href="UniteLocation" class="btn btn-secondary btn-back">↩ Retour à la liste</a>
</div>

</body>
</html>
