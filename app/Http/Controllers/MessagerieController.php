<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\messagerie;
use App\Models\Utilisateur;

class MessagerieController extends Controller
{
    /**
     * Liste des messages reçus par l'utilisateur connecté
     */
    public function index()
    {
        $messages = messagerie::where('destinataire_id', auth()->id())
            ->with('expediteur')
            ->latest()
            ->get();

        return response()->json($messages);
    }

    /**
     * Envoyer un message
     */
    public function store(Request $request)
    {
        $request->validate([
            'destinataire_id' => 'required|exists:utilisateurs,id',
            'message' => 'required|string'
        ]);

        $msg = messagerie::create([
            'expediteur_id' => auth()->id(),
            'destinataire_id' => $request->destinataire_id,
            'message' => $request->message
        ]);

        return response()->json([
            'message' => 'Message envoyé avec succès',
            'data' => $msg
        ], 201);
    }

    /**
     * Marquer un message comme lu
     */
    public function markAsRead($id)
    {
        $msg = messagerie::where('id', $id)
            ->where('destinataire_id', auth()->id())
            ->firstOrFail();

        $msg->update(['lu_le' => now()]);

        return response()->json(['message' => 'Message marqué comme lu']);
    }
}
