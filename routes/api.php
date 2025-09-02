<?php

use Illuminate\Http\Request;

use App\Http\Controllers\AuthController;
use App\Models\Utilisateur;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\{
    CategorieController,
    CommandeController,
    CommandeDetailController,
    FactureController,
    LivraisonController,
    MessagerieController,
    PackController,
    ProduitController,
    PromotionController,
    UtilisateurController
};
/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider and all of them will
| be assigned to the "api" middleware group. Make something great!
|
*/
Route::middleware(['auth:sanctum', 'role:admin'])->group(function () {
    // Gestion complète : utilisateurs, produits, commandes, promotions, statistiques
    Route::apiResource('utilisateurs', UtilisateurController::class);
    Route::apiResource('produits', ProduitController::class);
    Route::apiResource('promotions', PromotionController::class);
    Route::apiResource('commandes', CommandeController::class);
    Route::apiResource('livraisons', LivraisonController::class);
    Route::apiResource('messageries', MessagerieController::class);
    Route::apiResource('categories', CategorieController::class);
    Route::apiResource('commandedetails', CommandeDetailController::class);
    Route::apiResource('factures', FactureController::class);
    // Autres routes admin...
});
Route::apiResource('utilisateurs', UtilisateurController::class);

Route::middleware(['auth:sanctum', 'role:employe'])->group(function () {
    // Préparation commandes, gestion livraisons, assistance client
    Route::apiResource('commandes', CommandeController::class);
    Route::apiResource('livraisons', LivraisonController::class);
    Route::apiResource('messageries', MessagerieController::class);
    // Autres routes employé...
});

Route::middleware(['auth:sanctum', 'role:client'])->group(function () {
    // Navigation catalogue, commandes, suivi, promotions, support
    Route::apiResource('categories', CategorieController::class);
    Route::apiResource('commandedetails', CommandeDetailController::class);
    Route::apiResource('factures', FactureController::class);
    // Autres routes client...
});


// Routes pour l'authentification
Route::post('/login', [AuthController::class, 'login']);

Route::middleware('auth:sanctum')->group(function () {
    Route::post('/logout', [AuthController::class, 'logout']);

    // Tes autres routes protégées ici
});
// Route pour récupérer l'utilisateur authentifié
Route::middleware('auth:sanctum')->get('/user', function (Request $request) {
    return $request->user();
});
