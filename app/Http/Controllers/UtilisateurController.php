<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Utilisateur;
use Illuminate\Support\Facades\Hash;

class UtilisateurController extends Controller
{
    // Lister tous les utilisateurs (Admin uniquement)
    public function index()
    {
        
      try {
            $utilisateurs = Utilisateur::all();
            return response()->json($utilisateurs, 200);
        } catch (\Exception $e) {
            return response()->json(['error' => 'Erreur lors de la récupération des utilisateurs'], 500);
        }
    }

    // Ajouter un nouvel utilisateur (Admin uniquement)
   public function store(Request $request)
    {
        try {
            $this->validate($request, [
                'nom' => 'required|string|max:255',
                'prenom' => 'required|string|max:255',
                'email' => 'required|email|unique:utilisateurs,email',
                'password' => 'required|string|min:6',
                'role' => 'required|in:admin,employe,client',
                'telephone' => 'nullable|string',
                'adresse' => 'nullable|string'
            ]);

            $utilisateur = Utilisateur::create([
                'nom' => $request->nom,
                'prenom' => $request->prenom,
                'email' => $request->email,
                'password' => Hash::make($request->password),
                'role' => $request->role,
                'telephone' => $request->telephone,
                'adresse' => $request->adresse
            ]);

            return response()->json([
                'message' => 'Utilisateur créé avec succès',
                'utilisateur' => $utilisateur
            ], 201);
        } catch (\Exception $e) {
            return response()->json([
                'error' => 'Erreur lors de la création de l\'utilisateur',
                'message' => $e->getMessage()
            ], 500);
        }
    }
      
    // Modifier un utilisateur
    public function update(Request $request, $id)
    {
        $utilisateur = Utilisateur::findOrFail($id);

        $request->validate([
            'nom' => 'sometimes|string|max:255',
            'prenom' => 'sometimes|string|max:255',
            'email' => 'sometimes|email|unique:utilisateurs,email,' . $id,
            'password' => 'sometimes|string|min:6',
            'role' => 'sometimes|in:admin,employe,client',
            'telephone' => 'nullable|string',
            'adresse' => 'nullable|string'
        ]);

        $utilisateur->update([
            'nom' => $request->nom ?? $utilisateur->nom,
            'prenom' => $request->prenom ?? $utilisateur->prenom,
            'email' => $request->email ?? $utilisateur->email,
            'password' => $request->password ? Hash::make($request->password) : $utilisateur->password,
            'role' => $request->role ?? $utilisateur->role,
            'telephone' => $request->telephone ?? $utilisateur->telephone,
            'adresse' => $request->adresse ?? $utilisateur->adresse
        ]);

        return response()->json([
            'message' => 'Utilisateur mis à jour avec succès',
            'utilisateur' => $utilisateur
        ]);
    }

    // Supprimer un utilisateur
    public function destroy($id)
    {
        $utilisateur = Utilisateur::findOrFail($id);
        $utilisateur->delete();

        return response()->json(['message' => 'Utilisateur supprimé avec succès']);
    }
}
