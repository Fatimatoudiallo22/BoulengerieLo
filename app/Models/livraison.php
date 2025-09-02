<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Livraison extends Model
{
    use HasFactory;
    protected $guarded = [];

    // Relation avec la commande
    public function commande()
    {
        return $this->belongsTo(Commande::class, 'commande_id');
    }

    // Relation avec l'utilisateur (livreur)
    public function livreur()
    {
        // Dans ta migration, la colonne s'appelle employe_id, donc ici aussi !
        return $this->belongsTo(Utilisateur::class, 'employe_id');
    }
}
