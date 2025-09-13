<?php

namespace App\Http\Controllers;
use Illuminate\Support\Facades\Mail;   // ✅ Import correct
use App\Mail\CommandePayeeMail;   
use App\Models\Commande;
use Illuminate\Http\Request;

class CommandeController extends Controller
{
    // Liste des commandes
   public function index(Request $request)
{
    $user = $request->user(); // utilisateur connecté

    try {
        if ($user->role === 'client') {
            // Le client voit uniquement ses commandes
            $commandes = Commande::with('details.produit')
                ->where('utilisateur_id', $user->id)
                ->get();
        } else {
            // Admin et employé voient toutes les commandes
            $commandes = Commande::with('details.produit')->get();
        }

        return response()->json($commandes, 200);
    } catch (\Exception $e) {
        return response()->json(['error' => 'Erreur lors de la récupération des commandes'], 500);
    }
}


    // Détails d'une commande
    public function show($id)
    {
        return Commande::findOrFail($id);
    }

    // Créer une commande
public function store(Request $request)
{
    $request->validate([
        'prix_total' => 'required|numeric',
        'details' => 'required|array',
        'details.*.produit_id' => 'required|exists:produits,id',
        'details.*.quantite' => 'required|integer|min:1',
        'details.*.prix_unitaire' => 'required|numeric'
    ]);

    $utilisateur = $request->user(); // récupère l'utilisateur connecté

    $commande = Commande::create([
        'utilisateur_id' => $utilisateur->id,
        'prix_total' => $request->prix_total,
        'statut' => 'en_attente'
    ]);

    foreach ($request->details as $detail) {
        $commande->details()->create($detail);
    }

    return response()->json($commande, 201);
}



    // Mettre à jour une commande
    public function update(Request $request, $id)
    {
        $commande = Commande::findOrFail($id);

        $data = $request->validate([
            'total' => 'numeric',
        ]);

        $commande->update($data);

        return $commande;
    }

    // Supprimer une commande
    public function destroy($id)
    {
        return Commande::destroy($id);
    }

    // Marquer une commande comme payée et envoyer un email de confirmation

    public function payer($id)
{
    $commande = Commande::with('utilisateur', 'details.produit')->findOrFail($id);

    $commande->statut = 'livree';
    $commande->save();

    // 📧 Envoyer email
    Mail::to($commande->utilisateur->email)->send(new CommandePayeeMail($commande));

    return response()->json(['message' => 'Paiement effectué et email envoyé.']);
}


public function totalParClient($clientId)
{
    $total = Commande::where('utilisateur_id', $clientId)->sum('prix_total');

    return response()->json([
        'client_id' => $clientId,
        'total_commandes' => $total
    ]);
}


}
