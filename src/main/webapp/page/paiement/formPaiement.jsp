<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>${paiement != null ? "Modifier Paiement" : "Ajouter Paiement"}</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body {
      background-color: #f8f9fa;
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      padding: 40px;
    }
    .form-container {
      max-width: 650px;
      margin: auto;
      padding: 30px;
      background-color: #ffffff;
      border-radius: 12px;
      box-shadow: 0 0 15px rgba(0,0,0,0.1);
    }
    h1 {
      text-align: center;
      margin-bottom: 25px;
    }
    .btn-group {
      display: flex;
      justify-content: space-between;
    }
  </style>
</head>
<body>

<div class="container">
  <div class="form-container">
    <h1>${paiement != null ? "Modifier Paiement" : "Ajouter Paiement"}</h1>

    <form action="paiement" method="post">
      <input type="hidden" name="id" value="${paiement.id}" />


      <!-- Contrat -->
      <div class="mb-3">
        <label class="form-label">Contrat / Locataire :</label>
        <select name="contratId" class="form-select" required>
          <c:forEach var="contrat" items="${contrats}">
            <option value="${contrat.id}" ${paiement != null && paiement.contratLocation.id == contrat.id ? "selected" : ""}>
              Contrat: ${contrat.nom}  -  Locataire: ${contrat.locataire.nom}
            </option>
          </c:forEach>
        </select>
      </div>
      <!-- Montant -->
      <div class="mb-3">
        <label class="form-label">Montant (FCFA) :</label>
        <input type="number" step="1" name="prix" value="${paiement.prix}" class="form-control" required/>
      </div>

      <!-- Date -->
      <div class="mb-3">
        <label class="form-label">Date de Paiement :</label>
        <input type="datetime-local" name="datepaiement" value="${paiement.datepaiement}" class="form-control" required/>
      </div>

      <!-- Mode -->
      <div class="mb-3">
        <label class="form-label">Mode de Paiement :</label>
        <select name="modePaiement" class="form-select">
          <option value="ESPECE" ${paiement.modePaiement == 'ESPECE' ? 'selected' : ''}>ESPECE</option>
          <option value="VIREMENT" ${paiement.modePaiement == 'VIREMENT' ? 'selected' : ''}>VIREMENT</option>
          <option value="CHEQUE" ${paiement.modePaiement == 'CHEQUE' ? 'selected' : ''}>CHEQUE</option>
        </select>
      </div>



      <!-- Statut -->
      <div class="mb-3">
        <label class="form-label">Statut :</label>
        <select name="statusPaiement" class="form-select">
          <option value="EN_ATTEND" ${paiement.statusPaiement == 'EN_ATTEND' ? 'selected' : ''}>EN_ATTEND</option>
          <option value="EN_RETARD" ${paiement.statusPaiement == 'EN_RETARD' ? 'selected' : ''}>EN_RETARD</option>
          <option value="PAYE" ${paiement.statusPaiement == 'PAYE' ? 'selected' : ''}>PAYE</option>
        </select>
      </div>

      <!-- Boutons -->
      <div class="btn-group">
        <a href="paiement" class="btn btn-secondary">⬅ Retour</a>
        <button type="submit" class="btn btn-success">💾 Enregistrer</button>
      </div>
    </form>
  </div>
</div>

</body>
</html>
