<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Categorie;

class CategorieController extends Controller
{
    // Lister toutes les catégories
    public function index()
    {
        return Categorie::all();
    }

    // Voir une catégorie spécifique
    public function show($id)
    {
        $categorie = Categorie::findOrFail($id);
        return $categorie;
    }

    // Ajouter une catégorie (Admin / Employé)
    public function store(Request $request)
    {
        $request->validate([
            'nom' => 'required|string|max:255',
            'description' => 'nullable|string'
        ]);

        $categorie = Categorie::create($request->all());

        return response()->json([
            'message' => 'Catégorie ajoutée avec succès',
            'categorie' => $categorie
        ], 201);
    }

    // Modifier une catégorie
    public function update(Request $request, $id)
    {
        $categorie = Categorie::findOrFail($id);

        $request->validate([
            'nom' => 'sometimes|string|max:255',
            'description' => 'nullable|string'
        ]);

        $categorie->update($request->all());

        return response()->json([
            'message' => 'Catégorie mise à jour avec succès',
            'categorie' => $categorie
        ]);
    }

    // Supprimer une catégorie
    public function destroy($id)
    {
        $categorie = Categorie::findOrFail($id);
        $categorie->delete();

        return response()->json(['message' => 'Catégorie supprimée avec succès']);
    }
}
