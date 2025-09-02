<?php

namespace App\Http\Controllers;

use App\Models\Commande;
use Illuminate\Http\Request;

class CommandeController extends Controller
{
    // Liste des commandes
    public function index()
    {
        try {
            $commandes = Commande::all();
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
        $data = $request->validate([
            'client_id' => 'required|exists:clients,id',
            'total' => 'required|numeric',
        ]);

        return Commande::create($data);
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
}
