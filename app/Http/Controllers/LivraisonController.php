<?php

namespace App\Http\Controllers;
use App\Mail\CommandePayeeMail;
use Illuminate\Support\Facades\Mail;
use App\Models\Commande;
use App\Models\Livraison;
use Illuminate\Http\Request;

class LivraisonController extends Controller
{
    // Liste toutes les livraisons
    public function index()
    {
        try {
            $livraisons = Livraison::all();
            return response()->json($livraisons, 200);
        } catch (\Exception $e) {
            return response()->json(['error' => 'Erreur lors de la récupération des livraisons'], 500);
        }
    }

    // Affiche une livraison spécifique
    public function show($id)
    {
        return Livraison::findOrFail($id);
    }

    // Crée une nouvelle livraison
   public function store(Request $request)
{
    $data = $request->validate([
        'commande_id'      => 'required|exists:commandes,id',
        'adresse_livraison'=> 'nullable|string|max:255',
        'date_prevue'      => 'nullable|date',
        'statut'           => 'required|in:en_preparation,en_livraison,livree,annulee',
    ]);

    $livraison = Livraison::create($data);

    return response()->json($livraison, 201);
}

    // Met à jour une livraison
    public function update(Request $request, $id)
{
    $livraison = Livraison::findOrFail($id);

    $data = $request->validate([
        'adresse_livraison'=> 'nullable|string|max:255',
        'date_prevue'      => 'nullable|date',
        'date_livree'      => 'nullable|date',
        'statut'           => 'in:en_preparation,en_livraison,livree,annulee',
    ]);

    $livraison->update($data);

    return response()->json($livraison, 200);
}

    // Supprime une livraison
    public function destroy($id)
    {
        return Livraison::destroy($id);
    }





















    public function confirmerLivraison(Request $request, $commandeId)
{
    $commande = Commande::with('utilisateur', 'details.produit')->findOrFail($commandeId);

    $data = $request->validate([
        'adresse_livraison' => 'required|string|max:255',
        'date_prevue' => 'required|date'
    ]);

    // Créer la livraison
    $livraison = $commande->livraison()->create([
        'adresse_livraison' => $data['adresse_livraison'],
        'date_prevue' => $data['date_prevue'],
        'statut' => 'en_preparation',
        'employe_id' => null // ou assigner un livreur
    ]);

    // Marquer la commande comme payée
    $commande->statut = 'livree';
    $commande->save();

    // Envoyer email de confirmation
    Mail::to($commande->utilisateur->email)->send(new CommandePayeeMail($commande));

    return response()->json([
        'message' => 'Livraison créée, commande payée et mail envoyé',
        'livraison' => $livraison
    ]);
}

}


