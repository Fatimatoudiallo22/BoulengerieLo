<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class commande extends Model
{
    use HasFactory;
    protected $guarded = [];
    public function client()
    {
    return $this->belongsTo(Utilisateur::class, 'utilisateur_id');
    }

    public function details()
    {
    return $this->hasMany(CommandeDetail::class);
    }
    public function facture()
    {
    return $this->hasOne(Facture::class);
    }
}
