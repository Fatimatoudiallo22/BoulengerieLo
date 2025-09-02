<?php

namespace App\Http\Controllers;

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
            'commande_id' => 'required|exists:commandes,id',
            'adresse'     => 'required|string|max:255',
            'date'        => 'required|date',
            'statut'      => 'required|in:en préparation,en cours,livrée',
        ]);

        return Livraison::create($data);
    }

    // Met à jour une livraison
    public function update(Request $request, $id)
    {
        $livraison = Livraison::findOrFail($id);

        $data = $request->validate([
            'adresse' => 'string|max:255',
            'date'    => 'date',
            'statut'  => 'in:en préparation,en cours,livrée',
        ]);

        $livraison->update($data);

        return $livraison;
    }

    // Supprime une livraison
    public function destroy($id)
    {
        return Livraison::destroy($id);
    }
}
