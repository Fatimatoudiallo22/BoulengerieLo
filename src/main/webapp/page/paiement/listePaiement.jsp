<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Liste des Paiements</title>
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

<div class="container">
    <div class="table-container">
        <h1>Liste des Paiements</h1>

        <a href="paiement?action=add" class="btn btn-primary btn-add">➕ Ajouter un paiement</a>

        <div class="table-responsive">
            <table class="table table-bordered table-hover align-middle text-center">
                <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Contrat</th>
                    <th>Locataire</th>
                    <th>Montant</th>
                    <th>Date</th>
                    <th>Mode</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="paiement" items="${paiements}">
                    <tr>
                        <td>${paiement.id}</td>
                        <td>${paiement.contratLocation.nom}</td>
                        <td>${paiement.contratLocation.locataire.nom}</td>
                        <td><b>${paiement.prix} FCFA</b></td>
                        <td>${paiement.datepaiement}</td>
                        <td>${paiement.modePaiement}</td>
                        <td>
                            <c:choose>
                                <c:when test="${paiement.statusPaiement eq 'PAYE'}">
                                    <span class="badge bg-success">PAYE</span>
                                </c:when>
                                <c:when test="${paiement.statusPaiement eq 'EN_ATTEND'}">
                                    <span class="badge bg-warning text-dark">EN_ATTEND</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-danger">EN_RETARD</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td class="actions">
                            <div>
                                <a href="paiement?action=edit&id=${paiement.id}" class="btn btn-sm btn-warning">✏</a>
                                <a href="paiement?action=details&id=${paiement.id}" class="btn btn-sm btn-info text-white">🔍</a>
                                <form action="paiement" method="post" style="display:inline;">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="id" value="${paiement.id}">
                                    <button type="submit" class="btn btn-sm btn-danger"
                                            onclick="return confirm('Voulez-vous vraiment supprimer ce paiement ?')">
                                        🗑
                                    </button>
                                </form>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
</html>
