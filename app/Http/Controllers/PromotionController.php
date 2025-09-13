<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\promotion;
use App\Models\produit;
use App\Models\pack;

class PromotionController extends Controller
{
    /**
     * Liste toutes les promotions
     */
    public function index()
    {
        try {
            // Récupérer toutes les promotions avec leurs produits et packs associés
            $promotions = promotion::with(['produit', 'pack'])->get();
            return response()->json($promotions, 200);
        } catch (\Exception $e) {
            return response()->json(['error' => 'Erreur lors de la récupération des promotions'], 500);
        }
    
    }

    /**
     * Créer une promotion
     */
    public function store(Request $request)
    {
        $request->validate([
            'titre' => 'required|string|max:255',
            'description' => 'nullable|string',
            'type' => 'required|in:pourcentage,montant',
           'valeur' => [
    'required',
    'numeric',
    function ($attribute, $value, $fail) use ($request) {
        if ($request->type === 'pourcentage' && $value < 0) {
            $fail($attribute.' doit être positif pour les promotions en pourcentage.');
        }
    },
],

            'date_debut' => 'nullable|date',
            'date_fin' => 'nullable|date|after_or_equal:date_debut',
            'produit_id' => 'nullable|exists:produits,id',
            'pack_id' => 'nullable|exists:packs,id'
        ]);

        $promotion = promotion::create($request->all());

        return response()->json([
            'message' => 'Promotion créée avec succès',
            'data' => $promotion->load(['produit', 'pack'])
        ], 201);
    }

    /**
     * Afficher une promotion
     */
    public function show($id)
    {
        $promotion = promotion::with(['produit', 'pack'])->findOrFail($id);
        return response()->json($promotion);
    }

    /**
     * Mettre à jour une promotion
     */
    public function update(Request $request, $id)
    {
        $promotion = promotion::findOrFail($id);

        $request->validate([
            'titre' => 'sometimes|required|string|max:255',
            'description' => 'nullable|string',
            'type' => 'sometimes|required|in:pourcentage,montant',
           'valeur' => 'sometimes|required|numeric',
            'date_debut' => 'nullable|date',
            'date_fin' => 'nullable|date|after_or_equal:date_debut',
            'produit_id' => 'nullable|exists:produits,id',
            'pack_id' => 'nullable|exists:packs,id'
        ]);

        $promotion->update($request->all());

        return response()->json([
            'message' => 'Promotion mise à jour avec succès',
            'data' => $promotion->load(['produit', 'pack'])
        ]);
    }

    /**
     * Supprimer une promotion
     */
    public function destroy($id)
    {
        $promotion = promotion::findOrFail($id);
        $promotion->delete();

        return response()->json(['message' => 'Promotion supprimée avec succès']);
    }
}
