<?php

namespace App\Http\Controllers;

use App\Mail\CommandePayeeMail;
use Illuminate\Support\Facades\Mail;    
use App\Models\Facture;
use Barryvdh\DomPDF\Facade\Pdf;
use Illuminate\Support\Facades\Storage;

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
            'montant' => 'numeric|min:0',
            'date_emission'    => 'date',
        ]);

        return Facture::create($data);
    }

    // Mettre à jour une facture
    public function update(Request $request, $id)
    {
        $facture = Facture::findOrFail($id);

        $data = $request->validate([
            'commande_id' => 'required|exists:commandes,id',
            'montant' => 'numeric|min:0',
            'date_emission'    => 'date',
        ]);

        $facture->update($data);

        return $facture;
    }

    // Supprimer une facture
    public function destroy($id)
    {
        return Facture::destroy($id);
    }

    public function download($id)
{
    $facture = Facture::with('commande.utilisateur', 'commande.details.produit')->findOrFail($id);

    if (!$facture->pdf_path || !Storage::disk('public')->exists($facture->pdf_path)) {
        // Générer le PDF si inexistant
        $pdf = Pdf::loadView('factures.facture_pdf', compact('facture'));
        $pdfPath = 'factures/facture_' . $facture->id . '.pdf';
        $pdf->save(storage_path('app/public/' . $pdfPath));
        $facture->pdf_path = $pdfPath;
        $facture->save();
    }

    return response()->download(storage_path('app/public/' . $facture->pdf_path));
}
}
