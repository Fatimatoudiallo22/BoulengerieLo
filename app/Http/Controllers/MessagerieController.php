<?php

namespace App\Http\Controllers;
use App\Models\Message;
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
    /*
    public function conversation($userId)
{
    $authId = auth()->id();

    $messages = Messagerie::where(function ($q) use ($authId, $userId) {
            $q->where('expediteur_id', $authId)->where('destinataire_id', $userId);
        })
        ->orWhere(function ($q) use ($authId, $userId) {
            $q->where('expediteur_id', $userId)->where('destinataire_id', $authId);
        })
        ->with(['expediteur', 'destinataire'])
        ->orderBy('created_at', 'asc')
        ->get();

    return response()->json($messages);
}
*/












    // Afficher la conversation entre l’utilisateur connecté et un autre
    public function show($id)
    {
        $userId = auth()->id(); // id de l’utilisateur connecté

        $messages = messagerie::where(function($q) use ($id, $userId) {
                $q->where('expediteur_id', $userId)
                  ->where('destinataire_id', $id);
            })
            ->orWhere(function($q) use ($id, $userId) {
                $q->where('expediteur_id', $id)
                  ->where('destinataire_id', $userId);
            })
            ->orderBy('created_at', 'asc')
            ->get();

        return response()->json($messages);
    }
    /*
    // Envoyer un message
    public function store(Request $request)
    {
        $msg = Message::create([
            'expediteur_id'   => auth()->id(),
            'destinataire_id' => $request->destinataire_id,
            'message'         => $request->message,
        ]);

        return response()->json($msg, 201);
    }
    */

}

