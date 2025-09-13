<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tableau de Bord Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <style>
        /* --- Reset & Body --- */
        * { margin:0; padding:0; box-sizing:border-box; }
        body { font-family:'Segoe UI', sans-serif; background:#f8f9fa; overflow-x:hidden; }

        /* --- Sidebar --- */
        .sidebar {
            height:100vh; width:250px; position:fixed; top:0; left:0;
            background:#212529; color:white; padding:20px 15px; display:flex; flex-direction:column;
        }
        .sidebar h1 { text-align:center; margin-bottom:20px; font-size:24px; }
        .sidebar a {
            display:block; padding:12px 20px; margin-bottom:10px; text-decoration:none;
            color:white; background:#0d6efd; border-radius:6px; text-align:center; transition:0.3s;
        }
        .sidebar a:hover { background:#0b5ed7; }
        .logout-btn {
            width:100%; padding:12px; background:#dc3545; color:white; border:none;
            border-radius:6px; font-weight:bold; cursor:pointer; transition:0.3s;
        }
        .logout-btn:hover { background:#b02a37; margin-top:10px; }

        /* --- Header --- */
        .header {
            margin-left:250px; height:60px; background:white;
            border-bottom:1px solid #dee2e6; display:flex; align-items:center; padding:0 20px;
            font-weight:bold; color:#343a40; position:fixed; top:0; left:250px; right:0; z-index:100;
        }

        /* --- Content --- */
        .content { margin-left:250px; margin-top:60px; padding:20px; height:calc(100vh - 60px); }
        .content iframe {
            width:100%; height:100%; border:none; border-radius:10px; background:white;
            box-shadow:0px 0px 10px rgba(0,0,0,0.1);
        }

        /* --- Welcome Content --- */
        .welcome-content {
            display:flex; flex-direction:column; align-items:center; justify-content:center;
            height:100%; text-align:center; padding:40px; background:white; border-radius:16px;
        }
        .welcome-content h1 { font-size:32px; color:#343a40; margin-bottom:12px; }
        .welcome-content p { font-size:16px; color:#6c757d; max-width:600px; line-height:1.5; }
    </style>
</head>
<body>

<!-- Sidebar -->
<div class="sidebar">
    <h1>Admin</h1>
    <a href="${pageContext.request.contextPath}/utilisateur" target="contentFrame">👤 Utilisateurs</a>
    <a href="${pageContext.request.contextPath}/Immeuble" target="contentFrame">🏢 Immeubles</a>
    <a href="${pageContext.request.contextPath}/UniteLocation" target="contentFrame">📦 Unités</a>
    <a href="${pageContext.request.contextPath}/ContratLocation" target="contentFrame">📑 Contrats</a>
    <a href="${pageContext.request.contextPath}/paiement" target="contentFrame">💳 Paiements</a>

    <form action="${pageContext.request.contextPath}/logout" method="get">
        <button type="submit" class="logout-btn">🚪 Déconnexion</button>
    </form>
</div>

<!-- Header -->
<div class="header">
    🏠 Tableau de Bord - Administration
</div>

<!-- Content -->
<div class="content">
    <iframe name="contentFrame" id="contentFrame" style="display:none;"></iframe>

    <!-- Welcome content -->
    <div class="welcome-content" id="welcomeContent">
        <h1>Bienvenue dans l'Administration</h1>
        <p>Gérez vos utilisateurs, immeubles, unités et contrats facilement depuis cette interface.</p>
    </div>
</div>

<script>
    // Affiche le welcome content ou iframe selon clic sur liens
    const links = document.querySelectorAll('.sidebar a');
    const iframe = document.getElementById('contentFrame');
    const welcome = document.getElementById('welcomeContent');

    links.forEach(link => {
        link.addEventListener('click', function() {
            iframe.style.display = 'block';
            welcome.style.display = 'none';
        });
    });
</script>

</body>
</html>
