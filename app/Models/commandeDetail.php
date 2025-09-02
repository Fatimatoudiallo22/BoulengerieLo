<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class commandeDetail extends Model
{
    use HasFactory;
    protected $guarded = [];
    
    public function commande()
    {
    return $this->belongsTo(Commande::class);
    }

    public function produit()
    {
    return $this->belongsTo(Produit::class);
    }

}
