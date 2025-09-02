<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class facture extends Model
{
    use HasFactory;
    protected $guarded = [];
    
    // Une facture appartient à une commande
    public function commande()
    {
        return $this->belongsTo(commande::class, 'commande_id');
    }

    // Une facture peut avoir plusieurs produits
    public function produits()
    {
        return $this->belongsToMany(produit::class, 'facture_produit')
                    ->withPivot('quantite', 'prix_unitaire')
                    ->withTimestamps();
    }

    // Méthode pour calculer le montant total de la facture
    public function montantTotal()
    {
        return $this->produits->sum(function ($produit) {
            return $produit->pivot->quantite * $produit->pivot->prix_unitaire;
        });
    }

    // Méthode pour générer un numéro de facture unique
    public function generateNumeroFacture()
    {
        return 'FAC-' . strtoupper(uniqid());
    }
}
