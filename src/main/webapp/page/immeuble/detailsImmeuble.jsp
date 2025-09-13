<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Détails Immeuble</title>
    <!-- Lien vers Bootstrap CSS -->
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
            padding: 10px 0;
            border-bottom: 1px solid #e9ecef;
        }
        .detail-item:last-child {
            border-bottom: none;
        }
        .btn-back {
            display: block;
            margin: 20px auto 0;
            text-align: center;
        }
    </style>
</head>
<body>

<div class="details-container">
    <h1>Détails de l'Immeuble</h1>

    <div class="detail-item"><b>ID:</b> ${immeuble.id}</div>
    <div class="detail-item"><b>Nom:</b> ${immeuble.nom}</div>
    <div class="detail-item"><b>Adresse:</b> ${immeuble.adresse}</div>
    <div class="detail-item"><b>Nombre d'unités:</b> ${immeuble.nbreunite}</div>
    <div class="detail-item"><b>Équipements:</b> ${immeuble.equipementdispo}</div>
    <div class="detail-item"><b>Description:</b> ${immeuble.description}</div>
    <div class="mb-3">
        <b>Images :</b><br/>
        <c:forEach var="img" items="${immeuble.images}">
            <img src="${img}" alt="Image immeuble" style="width: 150px; height: 100px; object-fit: cover; margin: 5px; border-radius: 5px;">
        </c:forEach>
    </div>

    <a href="Immeuble" class="btn btn-secondary btn-back">↩ Retour à la liste</a>
</div>

</body>
</html>
