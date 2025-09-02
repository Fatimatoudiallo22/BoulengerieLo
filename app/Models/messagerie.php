<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class messagerie extends Model
{
    use HasFactory;
    protected $guarded = [];
    
    // Expéditeur (utilisateur)
    public function expediteur()
    {
        return $this->belongsTo(Utilisateur::class, 'expediteur_id');
    }

    // Destinataire (utilisateur)
    public function destinataire()
    {
        return $this->belongsTo(Utilisateur::class, 'destinataire_id');
    }
    
}
