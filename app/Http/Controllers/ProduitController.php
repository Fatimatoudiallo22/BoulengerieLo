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
            'prix' => 'required|numeric',
            'stock' => 'required|integer|min:0',
            'image' => 'nullable|file|image|mimes:jpeg,png,jpg,gif|max:2048',
            'categorie_id' => 'nullable|integer|exists:categories,id',
            'allergenes' => 'nullable|string'
        ]);
              $data = $request->all();

    // Upload de l'image si présente
   $data = $request->except('image');

if ($request->hasFile('image')) {
    $imageName = time().'_'.$request->file('image')->getClientOriginalName();
    $request->file('image')->move(public_path('images'), $imageName);
    $data['image'] = url('images/'.$imageName);
}

$produit = Produit::create($data); // store

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
            'prix' => 'sometimes|numeric',
            'stock' => 'sometimes|integer|min:0',
            'image' => 'nullable|file|image|mimes:jpeg,png,jpg,gif|max:2048',
            'categorie_id' => 'nullable|integer|exists:categories,id',
            'allergenes' => 'nullable|string'
        ]);

        $produit->update($request->all());
        $data = $request->except('image');

if ($request->hasFile('image')) {
    $imageName = time().'_'.$request->file('image')->getClientOriginalName();
    $request->file('image')->move(public_path('images'), $imageName);
    $data['image'] = url('images/'.$imageName);
}

$produit->update($data); // update
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
