<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Liste des Unités</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body {
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      padding: 20px;
      background-color: #f8f9fa;
    }

    h2 {
      margin-bottom: 20px;
    }

    a.btn-add {
      margin-bottom: 15px;
    }

    /* S'assurer que les colonnes s'adaptent sans scroll horizontal */
    .table-container {
      overflow-x: auto;
    }

    table th, table td {
      white-space: nowrap; /* empêche les cellules de s’étendre sur plusieurs lignes */
    }
  </style>
</head>
<body>
<h1>Liste des Unités de Location</h1>

<!-- Bouton Ajouter visible seulement pour ADMIN et PROPRIO -->
<c:if test="${sessionScope.user.role == 'ADMIN' || sessionScope.user.role == 'PROPRIO'}">
  <a href="UniteLocation?action=add">➕ Ajouter une unité</a>
  <a href="DemandeLocation?action=list" class="btn btn-info btn-sm ms-2">📄 Liste des demandes</a>
</c:if>

<div class="table-container">
  <table class="table table-striped table-hover table-bordered align-middle">
    <thead class="table-dark">
  <tr>
    <th>ID</th>
    <th>Nom</th>
    <th>Numéro</th>
    <th>Nombre de pièces</th>
    <th>Surface</th>
    <th>Loyer Mensuel</th>
    <th>Disponibilité</th>
    <th>Immeuble</th>
    <th>Image</th>
    <th>Actions</th>
  </tr>
    </thead>
  <!-- Parcours des unités -->
  <c:forEach var="unite" items="${unites}">
    <tr>
      <td>${unite.id}</td>
      <td>${unite.nom}</td>
      <td>${unite.numero}</td>
      <td>${unite.nombrePiece}</td>
      <td>${unite.surface}</td>
      <td>${unite.loyerMensuel}</td>
      <td>${unite.dispo}</td>
       <td>${unite.immeuble.nom}</td>
      <td>
        <c:if test="${not empty unite.imagePath}">
          <img src="${pageContext.request.contextPath}/${unite.imagePath}" alt="Image unité" width="100" height="80"/>
        </c:if>
        <c:if test="${empty unite.imagePath}">
          Pas d'image
        </c:if>
      </td>
      <td>
        <!-- ADMIN : toutes les actions -->
        <c:if test="${sessionScope.user.role == 'ADMIN'}">
          <a href="UniteLocation?action=edit&id=${unite.id}">Modifier</a> |
          <a href="UniteLocation?action=details&id=${unite.id}">Détails</a> |
          <form action="UniteLocation" method="post" style="display:inline">
            <input type="hidden" name="action" value="delete"/>
            <input type="hidden" name="id" value="${unite.id}"/>
            <input type="submit" value="Supprimer" onclick="return confirm('Supprimer ?');"/>
          </form>
        </c:if>

        <!-- PROPRIO : seulement ses unités -->
        <c:if test="${sessionScope.user.role == 'PROPRIO' && unite.immeuble.proprietaire.id == sessionScope.user.id}">
          <a href="UniteLocation?action=edit&id=${unite.id}">Modifier</a> |
          <a href="UniteLocation?action=details&id=${unite.id}">Détails</a> |
          <form action="UniteLocation" method="post" style="display:inline">
            <input type="hidden" name="action" value="delete"/>
            <input type="hidden" name="id" value="${unite.id}"/>
            <input type="submit" value="Supprimer" onclick="return confirm('Supprimer ?');"/>
          </form>
        </c:if>

        <!-- LOCATAIRE : seulement voir les détails -->
        <c:if test="${sessionScope.user.role == 'LOCATAIRE'}">
          <a href="UniteLocation?action=details&id=${unite.id}">Détails</a>
          <!-- Bouton pour ouvrir le mini-formulaire modal -->
          <c:choose>
            <c:when test="${demandesLocataire != null && demandesLocataire.contains(unite.id)}">
              <button type="button" class="btn btn-secondary btn-sm" disabled>Déjà demandé</button>
            </c:when>
            <c:otherwise>
              <button type="button" class="btn btn-success btn-sm" data-bs-toggle="modal" data-bs-target="#demandeModal${unite.id}">
                📩 Demande de location
              </button>

              <!-- Modal pour la demande -->
              <div class="modal fade" id="demandeModal${unite.id}" tabindex="-1" aria-labelledby="demandeModalLabel${unite.id}" aria-hidden="true">
                <div class="modal-dialog">
                  <div class="modal-content">
                    <form action="${pageContext.request.contextPath}/demandeLocation" method="post">
                      <div class="modal-header">
                        <h5 class="modal-title" id="demandeModalLabel${unite.id}">Demande de location - ${unite.nom}</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                      </div>
                      <div class="modal-body">
                        <input type="hidden" name="locataireId" value="${sessionScope.user.id}">
                        <input type="hidden" name="uniteId" value="${unite.id}">
                        <input type="hidden" name="statut" value="EN_ATTENTE">
                        <div class="mb-2"><strong>Nom :</strong> ${unite.nom}</div>
                        <div class="mb-2"><strong>Numéro :</strong> ${unite.numero}</div>
                        <div class="mb-2"><strong>Surface :</strong> ${unite.surface} m²</div>
                        <div class="mb-2"><strong>Loyer :</strong> ${unite.loyerMensuel} €</div>
                      </div>
                      <div class="modal-footer">
                        <button type="submit" class="btn btn-success">Valider la demande</button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button>
                      </div>
                    </form>
                  </div>
                </div>
              </div>
            </c:otherwise>
          </c:choose>
        </c:if>

      </td>
    </tr>
  </c:forEach>
  </table>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>