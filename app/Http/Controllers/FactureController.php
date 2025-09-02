<?php

namespace App\Http\Controllers;

use App\Models\Facture;
use Illuminate\Http\Request;

class FactureController extends Controller
{
    // Liste de toutes les factures
    public function index()
    {

        try {
            $factures = Facture::all();
            return response()->json($factures, 200);
        } catch (\Exception $e) {
            return response()->json(['error' => 'Erreur lors de la récupération des factures'], 500);
        }
    }

    // Afficher une facture spécifique
    public function show($id)
    {
        return Facture::findOrFail($id);
    }

    // Créer une nouvelle facture
    public function store(Request $request)
    {
        $data = $request->validate([
            'commande_id' => 'required|exists:commandes,id',
            'montant'     => 'required|numeric|min:0',
            'date'        => 'required|date',
        ]);

        return Facture::create($data);
    }

    // Mettre à jour une facture
    public function update(Request $request, $id)
    {
        $facture = Facture::findOrFail($id);

        $data = $request->validate([
            'montant' => 'numeric|min:0',
            'date'    => 'date',
        ]);

        $facture->update($data);

        return $facture;
    }

    // Supprimer une facture
    public function destroy($id)
    {
        return Facture::destroy($id);
    }
}
