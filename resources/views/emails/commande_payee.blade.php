<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Confirmation de paiement</title>
</head>
<body>
    <h1>Merci pour votre commande !</h1>
    <p>Votre commande #{{ $commande->id }} a bien été payée.</p>
    <p>Montant total : {{ $commande->prix_total }} €</p>
    <p>Statut : {{ $commande->statut }}</p>
</body>
</html>
