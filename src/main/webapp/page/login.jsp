
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Connexion</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f5f5f5;
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
    }
    .login-container {
      background-color: #fff;
      padding: 30px;
      border-radius: 10px;
      box-shadow: 0 0 10px rgba(0,0,0,0.2);
      width: 350px;
    }
    h2 {
      text-align: center;
      margin-bottom: 20px;
    }
    input[type=email], input[type=password] {
      width: 100%;
      padding: 10px;
      margin: 8px 0 15px 0;
      border: 1px solid #ccc;
      border-radius: 5px;
    }
    input[type=submit] {
      width: 100%;
      padding: 10px;
      background-color: #4CAF50;
      border: none;
      color: white;
      font-size: 16px;
      border-radius: 5px;
      cursor: pointer;
    }
    input[type=submit]:hover {
      background-color: #45a049;
    }
    .error {
      color: red;
      text-align: center;
      margin-bottom: 15px;
    }
  </style>
</head>
<body>
<div class="login-container">
  <h2>Connexion</h2>
  <!-- Affichage de l'erreur si elle existe -->
  <c:if test="${not empty error}">
    <div class="error">${error}</div>
  </c:if>

  <form action="login" method="post">
    <label for="email">Email :</label>
    <input type="email" id="email" name="email" required>

    <label for="password">Mot de passe :</label>
    <input type="password" id="password" name="password" required>

    <input type="submit" value="Se connecter">
  </form>
</div>
</body>
</html>
