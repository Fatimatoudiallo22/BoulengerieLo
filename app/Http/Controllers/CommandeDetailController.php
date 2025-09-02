<?php

namespace App\Http\Controllers;

use App\Models\CommandeDetail;
use Illuminate\Http\Request;

class CommandeDetailController extends Controller
{
    // Afficher tous les détails de commandes
    public function index()
    {
        try {
            $details = CommandeDetail::all();
            return response()->json($details, 200);
        } catch (\Exception $e) {
            return response()->json(['error' => 'Erreur lors de la récupération des détails de commande'], 500);
        }
    }

    // Afficher un détail de commande spécifique
    public function show($id)
    {
        return CommandeDetail::findOrFail($id);
    }

    // Ajouter un détail à une commande
    public function store(Request $request)
    {
        $data = $request->validate([
            'commande_id' => 'required|exists:commandes,id',
            'produit_id' => 'required|exists:produits,id',
            'quantite'   => 'required|integer|min:1',
            'prix'       => 'required|numeric|min:0',
        ]);

        return CommandeDetail::create($data);
    }

    // Mettre à jour un détail de commande
    public function update(Request $request, $id)
    {
        $detail = CommandeDetail::findOrFail($id);

        $data = $request->validate([
            'quantite' => 'integer|min:1',
            'prix'     => 'numeric|min:0',
        ]);

        $detail->update($data);

        return $detail;
    }

    // Supprimer un détail de commande
    public function destroy($id)
    {
        return CommandeDetail::destroy($id);
    }
}
