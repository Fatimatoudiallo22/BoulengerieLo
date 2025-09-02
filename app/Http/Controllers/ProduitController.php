<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Produit;

class ProduitController extends Controller
{
    // Lister tous les produits
    public function index()
    {
        try {
            $produits = Produit::all();
            return response()->json($produits, 200);
            
        } catch (\Exception $e) {
            return response()->json(['error' => 'Erreur lors de la récupération des produits'], 500);
        }
      
    }

    // Voir un produit spécifique
    public function show($id)
    {
        $produit = Produit::findOrFail($id);
        return $produit;
    }

    // Ajouter un produit (Admin / Employé)
    public function store(Request $request)
    {
        $request->validate([
            'nom' => 'required|string|max:255',
            'description' => 'nullable|string',
            'prix' => 'required|numeric|min:0',
            'stock' => 'required|integer|min:0',
            'photo_url' => 'nullable|string',
            'categorie_id' => 'nullable|integer|exists:categories,id',
            'allergenes' => 'nullable|string'
        ]);

        $produit = Produit::create($request->all());

        return response()->json([
            'message' => 'Produit ajouté avec succès',
            'produit' => $produit
        ], 201);
    }

    // Modifier un produit
    public function update(Request $request, $id)
    {
        $produit = Produit::findOrFail($id);

        $request->validate([
            'nom' => 'sometimes|string|max:255',
            'description' => 'nullable|string',
            'prix' => 'sometimes|numeric|min:0',
            'stock' => 'sometimes|integer|min:0',
            'photo_url' => 'nullable|string',
            'categorie_id' => 'nullable|integer|exists:categories,id',
            'allergenes' => 'nullable|string'
        ]);

        $produit->update($request->all());

        return response()->json([
            'message' => 'Produit modifié avec succès',
            'produit' => $produit
        ]);
    }

    // Supprimer un produit
    public function destroy($id)
    {
        $produit = Produit::findOrFail($id);
        $produit->delete();

        return response()->json(['message' => 'Produit supprimé avec succès']);
    }
}
