<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\pack;
use App\Models\produit;

class PackController extends Controller
{
    /**
     * Liste de tous les packs avec leurs produits
     */
    public function index()
    {
        try {
            // Récupérer tous les packs avec leurs produits associés
            $packs = pack::with('produits')->get();
            return response()->json($packs, 200);
        } catch (\Exception $e) {
            return response()->json(['error' => 'Erreur lors de la récupération des packs'], 500);
        }
       
    }

    /**
     * Créer un nouveau pack
     */
    public function store(Request $request)
    {
        $request->validate([
            'nom' => 'required|string|max:255',
            'description' => 'nullable|string',
            'prix' => 'required|numeric|min:0',
            'produits' => 'nullable|array', // liste des produits avec quantités
            'produits.*.id' => 'required|exists:produits,id',
            'produits.*.quantite' => 'required|integer|min:1'
        ]);

        $pack = pack::create($request->only(['nom', 'description', 'prix']));

        if ($request->has('produits')) {
            foreach ($request->produits as $prod) {
                $pack->produits()->attach($prod['id'], ['quantite' => $prod['quantite']]);
            }
        }

        return response()->json([
            'message' => 'Pack créé avec succès',
            'data' => $pack->load('produits')
        ], 201);
    }

    /**
     * Afficher un pack spécifique
     */
    public function show($id)
    {
        $pack = pack::with('produits')->findOrFail($id);
        return response()->json($pack);
    }

    /**
     * Mettre à jour un pack
     */
    public function update(Request $request, $id)
    {
        $pack = pack::findOrFail($id);

        $request->validate([
            'nom' => 'sometimes|required|string|max:255',
            'description' => 'nullable|string',
            'prix' => 'sometimes|required|numeric|min:0',
            'produits' => 'nullable|array',
            'produits.*.id' => 'required|exists:produits,id',
            'produits.*.quantite' => 'required|integer|min:1'
        ]);

        $pack->update($request->only(['nom', 'description', 'prix']));

        if ($request->has('produits')) {
            $syncData = [];
            foreach ($request->produits as $prod) {
                $syncData[$prod['id']] = ['quantite' => $prod['quantite']];
            }
            $pack->produits()->sync($syncData);
        }

        return response()->json([
            'message' => 'Pack mis à jour avec succès',
            'data' => $pack->load('produits')
        ]);
    }

    /**
     * Supprimer un pack
     */
    public function destroy($id)
    {
        $pack = pack::findOrFail($id);
        $pack->produits()->detach();
        $pack->delete();

        return response()->json(['message' => 'Pack supprimé avec succès']);
    }
}
