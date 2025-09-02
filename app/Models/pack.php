<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class pack extends Model
{
    use HasFactory;
    protected $guarded = [];
    
   // Un pack contient plusieurs produits (relation Many-to-Many avec quantité)
    public function produits()
    {
        return $this->belongsToMany(Produit::class, 'pack_produit')
                    ->withPivot('quantite')
                    ->withTimestamps();
    }
}
